package ru.ds.magnitfaqchatbot.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ds.magnitfaqchatbot.entity.CategoryEntity;
import ru.ds.magnitfaqchatbot.repository.CategoryRepository;
import ru.ds.magnitfaqchatbot.service.CategoryService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public CategoryEntity getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found category for id = %d", id)));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
