package org.example.hn_k24_cntt4_ngothihoaiphuong_001.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.model.Category;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thư mục"));
    }
    @Transactional
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
