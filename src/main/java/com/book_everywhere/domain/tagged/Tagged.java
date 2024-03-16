package com.book_everywhere.domain.tagged;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.tag.Tag;
import com.book_everywhere.web.dto.review.ReviewRespDto;
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

    private int count;

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

    //==수정 메서드==//
    public void changeTagged(Pin pin, Tag tag, int count) {
        setPin(pin);
        setTag(tag);
        this.count = count;
    }
}
