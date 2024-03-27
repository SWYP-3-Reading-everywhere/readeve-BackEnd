package com.book_everywhere.pin.entity;

import com.book_everywhere.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pin_id")
    private Pin pin;

    private boolean isPinPrivate;
    //최초 방문시각
    @CreationTimestamp
    private Timestamp createAt;

    //==연관 관계 편의 메서드==//
    private void setUser(User user) {
        if (this.user != null) {
            this.user.getVisits().remove(this);
        }
        this.user = user;
        user.getVisits().add(this);
    }

    private void setPin(Pin pin) {
        if (this.pin != null) {
            this.pin.getVisits().remove(this);
        }
        this.pin = pin;
        pin.getVisits().add(this);
    }

    //==수정 메서드==//
    public void changeVisit(User user, Pin pin, boolean isPinPrivate) {
        setUser(user);
        setPin(pin);
        this.isPinPrivate = isPinPrivate;
    }
}
