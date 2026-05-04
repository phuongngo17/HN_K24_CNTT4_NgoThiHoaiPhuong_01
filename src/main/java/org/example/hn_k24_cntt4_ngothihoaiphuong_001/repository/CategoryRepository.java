package org.example.hn_k24_cntt4_ngothihoaiphuong_001.repository;

import org.example.hn_k24_cntt4_ngothihoaiphuong_001.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
