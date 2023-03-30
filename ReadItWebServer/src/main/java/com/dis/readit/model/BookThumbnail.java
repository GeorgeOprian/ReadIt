package com.dis.readit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity(name = "BookThumbnail")
@Table(name = "book_thumbnail")
public class BookThumbnail {

	@Id
	@Column(name ="thumbnail_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer thumbnailId;

	@Column(name ="small_thumbnail")
	private String smallThumbnail;

	@Column(name ="thumbnail")
	private String thumbnail;

	public BookThumbnail(String smallThumbnail, String thumbnail) {
		this.smallThumbnail = smallThumbnail;
		this.thumbnail = thumbnail;
	}
}
