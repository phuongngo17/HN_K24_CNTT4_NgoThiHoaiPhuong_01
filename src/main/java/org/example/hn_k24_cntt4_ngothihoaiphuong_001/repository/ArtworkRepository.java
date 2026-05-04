package org.example.hn_k24_cntt4_ngothihoaiphuong_001.repository;

import org.example.hn_k24_cntt4_ngothihoaiphuong_001.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    Page<Artwork> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Artwork> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
