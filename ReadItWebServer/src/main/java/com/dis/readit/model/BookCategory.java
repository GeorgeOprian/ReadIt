package com.dis.readit.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "BookCategory")
@Table(name = "book_category")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BookCategory {

	@EmbeddedId
	private BookCategoryId id;

	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("bookId")
	@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_BOOK_CATEGORY"))
	@JsonManagedReference
	private Book book;

	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("categoryId")
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_BOOK"))
	private Category category;

	public BookCategory(Book book, Category category) {
		this.book = book;
		this.category = category;

		id = new BookCategoryId(book.getBookId(), category.getCategoryId());
	}


	@Embeddable
	@AllArgsConstructor @NoArgsConstructor
	public static class BookCategoryId implements Serializable {

		@Column(name = "book_id")
		private Integer bookId;

		@Column(name = "category_id")
		private Integer categoryId;

		public Integer getBookId() {
			return bookId;
		}

		public void setBookId(Integer bookId) {
			this.bookId = bookId;
		}

		public Integer getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;

			if (o == null || getClass() != o.getClass())
				return false;

			BookCategoryId that = (BookCategoryId) o;
			return Objects.equals(bookId, that.bookId) &&
					Objects.equals(categoryId, that.categoryId);
		}

		@Override
		public int hashCode() {
			return Objects.hash(bookId, categoryId);
		}
	}

}
