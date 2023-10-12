package ru.ds.magnitfaqchatbot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ds.magnitfaqchatbot.model.converter.UserSettingsJsonConverter;
import ru.ds.magnitfaqchatbot.model.user.UserSettings;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @Column(name = "id")
    Long id;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "department")
    String department;

    @Column(name = "telegram_chat_id")
    String telegramId;

    @Column(name = "settings", columnDefinition = "jsonb", nullable = false)
    @Convert(converter = UserSettingsJsonConverter.class)
    UserSettings settings;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<UserRoleEntity> roles;
}
