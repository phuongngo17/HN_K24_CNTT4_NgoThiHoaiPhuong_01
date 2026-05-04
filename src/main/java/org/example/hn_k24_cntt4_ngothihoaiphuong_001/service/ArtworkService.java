package org.example.hn_k24_cntt4_ngothihoaiphuong_001.service;

import lombok.RequiredArgsConstructor;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.model.Artwork;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.repository.ArtworkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final CategoryService categoryService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public Page<Artwork> getAllArtworks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return artworkRepository.findAll(pageable);
    }

    public Page<Artwork> getArtworksByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return artworkRepository.findByCategoryId(categoryId, pageable);
    }

    public Artwork getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác phẩm"));
    }

    @Transactional
    public Artwork saveArtwork(Artwork artwork, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = saveImageFile(file);
            artwork.setCoverImage("/images/" + fileName);
        }

        return artworkRepository.save(artwork);
    }

    @Transactional
    public void deleteArtwork(Long id) {
        artworkRepository.deleteById(id);
    }

    private String saveImageFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        return uniqueFileName;
    }

    public boolean isValidArtwork(Artwork artwork) {
        if (artwork.getTitle() == null || artwork.getTitle().length() < 5 || artwork.getTitle().length() > 150) {
            return false;
        }
        if (artwork.getArtist() == null || artwork.getArtist().trim().isEmpty()) {
            return false;
        }
        if (artwork.getReleaseDate() != null && artwork.getReleaseDate().isAfter(LocalDate.now())) {
            return false;
        }
        return true;
    }
}