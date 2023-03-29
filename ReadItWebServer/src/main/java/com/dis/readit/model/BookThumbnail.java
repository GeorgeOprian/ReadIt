package com.dis.readit.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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

}
