package self.heresay.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="bk_transaction")
@Getter
@Setter
@EqualsAndHashCode
public class Transaction implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "Date")
	private String date;
	@Column(name = "Description")
	private String description;
	@Column(name = "Deposits")
	private String deposits;
	@Column(name = "Withdrawls")
	private String withdrawls;
	@Column(name = "Balance")
	private String balance;
	@Column(name = "created_datetime")
	private Date created_datetime;
}


