package com.luongtx.oes.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity(name = "role")
@Table(name = "[ROLE]")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
