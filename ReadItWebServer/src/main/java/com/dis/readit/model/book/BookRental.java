package com.dis.readit.model.book;

import com.dis.readit.model.user.DataBaseUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "BookRental")
@Table(name = "book_rental")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BookRental {

	@Id
	@Column(name = "rent_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rentId;

	@ManyToOne
	@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_BOOK_USER"))
	@JsonManagedReference
	private Book book;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_BOOK"))
	private DataBaseUser user;

	@Column(name = "return_date")
	private LocalDate returnDate;

	public BookRental(Book book, DataBaseUser user, LocalDate returnDate) {
		this.book = book;
		this.user = user;
		this.returnDate = returnDate;
	}


}
