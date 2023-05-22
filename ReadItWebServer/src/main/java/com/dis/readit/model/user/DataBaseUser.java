package com.dis.readit.model.user;

import com.dis.readit.model.address.Adresa;
import com.dis.readit.model.address.Localitate;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.book.BookRental;
import com.dis.readit.model.subscription.Subscription;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name = "User")
@Table(name = "read_it_user")
public class DataBaseUser {
	public static final String ADMIN_USER_EMAIL = "readitapp.adm@gmail.com";

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@Column(name = "email")
	private String email;

	@Column(name = "user_name")
	private String userName;
	@Column(name = "is_admin")
	private boolean isAdmin;

	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private List<BookRental> bookRentals = new ArrayList<>();

	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private List<Subscription> subscriptions = new ArrayList<>();


	@OneToOne
	@JoinColumn(name = "id_adresa", foreignKey = @ForeignKey(name = "FK_USER_ADDRESS"))
	private Adresa adresa;


	public void addRental(Book book, LocalDate returnDate) {
		BookRental bookRental = new BookRental(book, this, returnDate);
		bookRentals.add(bookRental);
	}

}
