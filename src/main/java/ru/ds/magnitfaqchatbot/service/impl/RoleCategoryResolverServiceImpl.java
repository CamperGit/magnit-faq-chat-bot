package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.ds.magnitfaqchatbot.property.RoleCategoryProperties;
import ru.ds.magnitfaqchatbot.service.RoleCategoryResolverService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleCategoryResolverServiceImpl implements RoleCategoryResolverService {

    RoleCategoryProperties roleCategoryProperties;

    @Override
    public List<String> resolveCategories(List<String> roles) {
        Map<String, List<String>> roleMapping = this.roleCategoryProperties.getMapping();
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        List<String> normalizedRoles = roles.stream().map(String::toUpperCase).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleMapping)) {
            return normalizedRoles;
        } else {
            List<String> resultCategories = new ArrayList<>();
            roleMapping.forEach((key, value) -> {
                if (roles.contains(key)) {
                    resultCategories.addAll(value);
                }
            });
            return resultCategories;
        }
    }
}
