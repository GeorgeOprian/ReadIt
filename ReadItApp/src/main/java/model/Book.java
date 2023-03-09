package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "BOOK")
public class Book {

	@Id
	@Column(name = "ISBN")
	private Long isbn;

	@Column(name = "name")
	private String name;


}
