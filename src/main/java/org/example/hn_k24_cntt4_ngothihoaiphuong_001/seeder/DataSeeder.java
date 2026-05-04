package org.example.hn_k24_cntt4_ngothihoaiphuong_001.seeder;

import lombok.RequiredArgsConstructor;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.model.Artwork;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.model.Category;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.repository.ArtworkRepository;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ArtworkRepository artworkRepo;
    private final CategoryRepository categoryRepo;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepo.count() == 0) {
            Category cat1 = new Category(null, "Painting", "Tranh sơn dầu, acrylic", new ArrayList<>());
            Category cat2 = new Category(null, "Photography", "Nhiếp ảnh nghệ thuật", new ArrayList<>());
            Category cat3 = new Category(null, "Digital Art", "Nghệ thuật số", new ArrayList<>());

            categoryRepo.saveAll(List.of(cat1, cat2, cat3));

            createArtworksForCategory(cat1, 5);
            createArtworksForCategory(cat2, 4);
            createArtworksForCategory(cat3, 4);
        }
    }

    private void createArtworksForCategory(Category category, int count) {
        for (int i = 1; i <= count; i++) {
            Artwork art = new Artwork();
            art.setTitle("Tác phẩm " + i + " - " + category.getName());
            art.setArtist("Artist " + i);
            art.setPrice(500.0 + i * 100);
            art.setReleaseDate(LocalDate.now().minusMonths(i));
            art.setCoverImage("/images/default.jpg");
            art.setStatus(true);
            art.setCategory(category);
            artworkRepo.save(art);
        }
    }
}