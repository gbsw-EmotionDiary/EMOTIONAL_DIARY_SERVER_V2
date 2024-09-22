package com.diary.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="black_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistToken {

    @Id
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
