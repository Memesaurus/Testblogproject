package com.BlogSite.TestBlogProject.models;

import lombok.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private static final PasswordEncoder PASSWORD_ENCODER =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Column(name = "isactive")
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "roleid",
           foreignKey = @ForeignKey(name = "fk_roleid"))
    private Role role;

    public void encryptPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
        this.setIsActive(true);
    }

}
