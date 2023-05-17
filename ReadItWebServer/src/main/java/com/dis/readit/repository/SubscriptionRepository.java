package com.dis.readit.repository;

import com.dis.readit.model.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
