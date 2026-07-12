package divar.service.impl;

import divar.dto.response.CategoryResponse;
import divar.repository.CategoryRepository;
import divar.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getParentCategory() == null
                                ? null
                                : category.getParentCategory().getId()
                ))
                .toList();
    }
}