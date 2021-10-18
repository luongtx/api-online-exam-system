package com.luongtx.oes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "[USER]")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "ENABLED", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "USER_EXAM",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXAM_ID")
    )
    private Set<Exam> exams;

    @OneToOne
    @JoinColumn(name = "PROFILE_ID")
    private Profile profile;

}
