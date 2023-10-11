package ru.ds.magnitfaqchatbot.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.magnitfaqchatbot.model.role.RolePermission;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleDto {

    Long id;

    String title;

    RolePermission permissions;
}
