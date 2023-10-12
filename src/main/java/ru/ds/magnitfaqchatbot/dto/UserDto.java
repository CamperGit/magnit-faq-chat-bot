package ru.ds.magnitfaqchatbot.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.magnitfaqchatbot.model.user.UserSettings;

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

    UserSettings settings;

    List<UserRoleDto> roles;
}
