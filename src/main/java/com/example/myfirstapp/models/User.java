package com.example.myfirstapp.models;

import com.example.myfirstapp.enums.Gender;
import com.example.myfirstapp.validators.ValidDob;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender is mandatory")
    private Gender gender;

    @NotNull(message = "Date of birth is mandatory")
    @ValidDob
    private LocalDate dob;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id",
            referencedColumnName = "user_id",
            insertable = false,
            updatable = false
    )
    private UserInfo userInfo;
}
