package self.heresay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadException extends RuntimeException{
   private String message;
   
}
