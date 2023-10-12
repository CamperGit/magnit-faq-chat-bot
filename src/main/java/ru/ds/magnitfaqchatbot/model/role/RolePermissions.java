package ru.ds.magnitfaqchatbot.model.role;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePermissions {

    CategoryPermissions categoryPermissions;
}
