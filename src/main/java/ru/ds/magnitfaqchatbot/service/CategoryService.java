package ru.ds.magnitfaqchatbot.service;

import ru.ds.magnitfaqchatbot.entity.CategoryEntity;


public interface CategoryService {

    CategoryEntity save(CategoryEntity category);

    CategoryEntity getById(Long id);

    void deleteById(Long id);
}
