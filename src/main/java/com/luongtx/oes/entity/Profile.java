package com.luongtx.oes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "profile")
@Table(name = "PROFILE")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "BIRTHDAY")
    private LocalDate birthDay;

    @Column(name = "IMAGE")
    private String imageSrc;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
