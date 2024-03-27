package com.book_everywhere.tag.entity;

import com.book_everywhere.common.entity.BaseTimeEntity;
import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Tagged extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagged_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pin_id")
    private Pin pin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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
