package com.book_everywhere.domain.visit;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "pinId")
    private Pin pin;

    //최초 방문시각
    @CreationTimestamp
    private Timestamp createAt;
}
