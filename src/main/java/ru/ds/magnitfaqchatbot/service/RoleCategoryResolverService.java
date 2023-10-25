package ru.ds.magnitfaqchatbot.service;

import java.util.List;

public interface RoleCategoryResolverService {

    List<String> resolveCategories(List<String> roles);
}
