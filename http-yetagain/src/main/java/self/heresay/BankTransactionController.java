package self.heresay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType;
import lombok.extern.slf4j.Slf4j;
import self.heresay.model.Transaction;

@RestController
@RequestMapping("bank-tx")
@Slf4j
public class BankTransactionController {
	@Autowired
	private BankTxFacadeRemote bankTxFacade;
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> bulkUploadtransactions(@RequestParam("file") MultipartFile file) {
		return new ResponseEntity<>( "Successfully uploaded file",HttpStatus.OK);
	}
	private UploadFileResponse uploadFile(MultipartFile processFile){
		CsvSchema txSchema = CsvSchema.builder()
	             .addColumn("date", ColumnType.STRING)
	             .addColumn("description", ColumnType.STRING)
	             .addColumn("deposits", ColumnType.STRING)
	             .addColumn("withdrawls", ColumnType.STRING)
	             .addColumn("balance", ColumnType.STRING).build();
		try(InputStream data = processFile.getInputStream()){
			CsvMapper csvMapper = new CsvMapper();
	        MappingIterator<Transaction> itr = csvMapper.readerFor(Transaction.class).with(txSchema).readValues(data);
	        while (itr.hasNext()){
	        	Transaction tx = itr.next();
	        	bankTxFacade.addTransaction(tx);
	        }
		} catch (IOException ex) {
			log.error("error uploading file",ex);
		}
		return new UploadFileResponse("Successfully uploaded file");
	}
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("null")
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = null;
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
