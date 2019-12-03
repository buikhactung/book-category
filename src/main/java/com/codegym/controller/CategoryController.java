package com.codegym.controller;


import com.codegym.model.Category;
import com.codegym.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public String categoryList(Model model, @PageableDefault(size = 5) Pageable pageable){
        Page<Category> categories = categoryService.findAll(pageable);
        model.addAttribute("categories",categories);
        return "category/list";
    }
    //    @GetMapping("/")
//    public String homePage(){
//        return "home";
//    }
    @GetMapping("/create-category")
    public String createCategory(Model model) {
        model.addAttribute("category", new Category());
        return "category/create";
    }
    @PostMapping("/create-category")
    public String saveNewCategory(Category category, Model model) {
        categoryService.save(category);
        model.addAttribute("message", "Added new category");
        model.addAttribute("category", new Category());
        return "category/create";
    }
    @GetMapping("/edit-category/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "category/edit";
    }
    @PostMapping("/edit-category")
    public String saveEditCategory(Model model, Category category) {
        categoryService.save(category);
        model.addAttribute("message","Saved");
        return "category/edit";
    }
    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "category/delete";
    }
    @PostMapping("/delete-category")
    public String saveDeleteCategory(Category category) {
        categoryService.remove(category.getId());
        return "category/delete";
    }
}
