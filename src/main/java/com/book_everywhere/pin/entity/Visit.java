package com.book_everywhere.pin.entity;

import com.book_everywhere.auth.entity.User;
import com.book_everywhere.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Visit extends BaseTimeEntity {

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
