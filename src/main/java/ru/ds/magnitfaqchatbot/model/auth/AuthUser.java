package ru.ds.magnitfaqchatbot.model.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthUser {

    Long id;

    String fullName;

    String department;

    String telegramId;

    List<AuthUserRole> roles;
}
