package com.codegym.service.book;

import com.codegym.model.Book;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Page<Book> findAll(Pageable pageable);
    List<Book> findAllByCategory(Category category);
    Book findById(Long id);
    void save(Book book);

    void remove(Long id);
}
