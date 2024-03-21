package com.book_everywhere.tag.entity;

import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Tagged {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pinId")
    private Pin pin;

    @ManyToOne
    @JoinColumn(name = "tagId")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createAt;

    //==연관 관계 편의 메서드==//
    private void setPin(Pin pin) {
        if(this.pin != null) {
            this.pin.getTags().remove(this);
        }
        this.pin = pin;
        pin.getTags().add(this);
    }
    private void setTag(Tag tag) {
        if(this.tag != null) {
            this.tag.getTags().remove(this);
        }
        this.tag = tag;
        tag.getTags().add(this);
    }

    private void setUser(User user) {
        this.user = user;
    }


    //==수정 메서드==//
    public void changeTagged(Pin pin, Tag tag, User user) {
        setPin(pin);
        setTag(tag);
        setUser(user);
    }
}
