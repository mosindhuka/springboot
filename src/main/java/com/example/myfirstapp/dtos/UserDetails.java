package com.example.myfirstapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserDetails {

    private Integer userId;

    private String name;

    private String city;
}
