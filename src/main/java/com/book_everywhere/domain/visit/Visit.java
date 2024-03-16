package com.book_everywhere.domain.visit;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "pinId")
    private Pin pin;

    private boolean isPinPrivate;
    //최초 방문시각
    @CreationTimestamp
    private Timestamp createAt;

    //==연관 관계 편의 메서드==//
    private void setUser(User user) {
        if(this.user != null) {
            this.user.getVisits().remove(this);
        }
        this.user = user;
        user.getVisits().add(this);
    }
    private void setPin(Pin pin) {
        if(this.pin != null) {
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
