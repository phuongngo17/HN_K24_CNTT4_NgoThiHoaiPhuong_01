package org.example.hn_k24_cntt4_ngothihoaiphuong_001.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "artworks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 150, message = "Độ dài phải từ 5 đến 150 ký tự")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Không được để trống")
    @Column(name = "artist")
    private String artist;

    @Column(name = "price")
    private Double price;

    @PastOrPresent(message = "Không được là ngày trong tương lai")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "cover_image")
    private String coverImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "status",nullable = false)
    private Boolean status = true;
}
