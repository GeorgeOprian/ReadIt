package com.dis.readit.model.subscription;

import com.dis.readit.model.user.DataBaseUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Entity(name = "Subscription")
@Table(name = "Subscription")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Subscription {

	@Id
	@Column(name = "subscription_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subscriptionId;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_SUBSCRIPTION"))
	private DataBaseUser user;

}
