package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.book.BookService;
import com.codegym.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/books")
    public String bookList(Model model, @PageableDefault(size = 10,sort = "name") Pageable pageable) {
        Page<Book> books = bookService.findAll(pageable);
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/create-book")
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        return "book/create";
    }

    @PostMapping("/create-book")
    public String saveNewBook(Book book, Model model) {
        bookService.save(book);
        model.addAttribute("message", "Added new book");
        model.addAttribute("book", new Book());
        return "book/create";
    }

    @GetMapping("/edit-book/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book/edit";
    }

    @PostMapping("/edit-book")
    public String saveEditBook(Model model, Book book) {
        bookService.save(book);
        model.addAttribute("message", "Saved");
        return "book/edit";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book/delete";
    }

    @PostMapping("/delete-book")
    public String saveDeleteBook(Book book) {
        bookService.remove(book.getId());
        return "book/delete";
    }

    @ModelAttribute("categories")
    public Page<Category> suppliers(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/list-books/{id}")
    public String listBooksMaterials(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        List<Book> books = bookService.findAllByCategory(category);
        model.addAttribute("books", books);
        return "category/listbooks-categories";
    }
    @GetMapping("/search-book")
    public String search(@RequestParam String search, Model model, @PageableDefault(size = 10,sort = "price") Pageable pageable){
        Page<Book> books = bookService.findAll(pageable);
        List<Book> results = new ArrayList<>();
        for (Book book: books) {
            if (book.getName().toUpperCase().contains(search.toUpperCase())) {
                results.add(book);
            }
        }
        model.addAttribute("books",results);
        return "book/list";
    }
}
