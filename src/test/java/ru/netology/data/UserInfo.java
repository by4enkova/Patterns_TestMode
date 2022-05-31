package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class UserInfo {
    String login;
    String password;
    String status;

}
