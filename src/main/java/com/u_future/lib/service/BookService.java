package com.u_future.lib.service;

import com.u_future.lib.model.Book;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final List<Book> bookList = new ArrayList<>();
    private Long nextId = 1L;

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Optional<Book> getBookById(Long id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book addBook(Book book) {
        validatePrice(book.getPrice());
        book.setId(nextId++);
        bookList.add(book);
        return book;
    }

    public Optional<Book> updateBook(Long id, Book newBook) {
        validatePrice(newBook.getPrice());
        return getBookById(id).map(book -> {
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            book.setDescription(newBook.getDescription());
            book.setPrice(newBook.getPrice());
            return book;
        });
    }

    public boolean deleteBook(Long id) {
        return bookList.removeIf(book -> book.getId().equals(id));
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
    }
}
