package ru.ds.magnitfaqchatbotauthserver.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Long id;

    String fullName;

    String department;

    String telegramId;

    List<UserRoleDto> roles;
}
