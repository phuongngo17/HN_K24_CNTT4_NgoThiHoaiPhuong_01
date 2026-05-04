package org.example.hn_k24_cntt4_ngothihoaiphuong_001.controller;
import lombok.RequiredArgsConstructor;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.model.Artwork;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.model.Category;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.service.ArtworkService;
import org.example.hn_k24_cntt4_ngothihoaiphuong_001.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ArtworkController {
    private final ArtworkService artworkService;
    private final CategoryService categoryService;

    @GetMapping
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size,
                       Model model) {

        Page<Artwork> artworkPage = artworkService.getAllArtworks(page, size);

        model.addAttribute("artworks", artworkPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", artworkPage.getTotalPages());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "artwork/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("artwork", new Artwork());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "artwork/form";
    }

    @PostMapping("/add")
    public String saveArtwork(@ModelAttribute Artwork artwork,
                              @RequestParam("file") MultipartFile file,
                              Model model) {
        try {
            if (!artworkService.isValidArtwork(artwork)) {
                model.addAttribute("error", "Dữ liệu không hợp lệ!");
                model.addAttribute("categories", categoryService.getAllCategories());
                return "artwork/form";
            }
            artworkService.saveArtwork(artwork, file);
            return "redirect:/";
        } catch (IOException e) {
            model.addAttribute("error", "Lỗi upload file!");
            return "artwork/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.getArtworkById(id);
        model.addAttribute("artwork", artwork);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "artwork/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return "redirect:/";
    }

    @GetMapping("/category/{id}")
    public String getByCategory(@PathVariable Long id,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {

        Page<Artwork> artworkPage = artworkService.getArtworksByCategory(id, page, size);
        Category category = categoryService.getCategoryById(id);

        model.addAttribute("artworks", artworkPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", artworkPage.getTotalPages());
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "artwork/list";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.getArtworkById(id);
        model.addAttribute("artwork", artwork);
        return "artwork/detail";
    }
}