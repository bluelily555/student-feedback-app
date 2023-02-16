package com.project.feedback.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePwRequest {
    private String userName;
    private String curPassword;
    private String chPassword;

//    public Object toEntity(String encodedPassword) {
//    }
}
