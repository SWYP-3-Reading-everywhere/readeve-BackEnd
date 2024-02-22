package com.book_everywhere.web.dto.exception;

import java.io.Serial;

public class BookNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BookNotFoundException() {
        super("Book not found");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
