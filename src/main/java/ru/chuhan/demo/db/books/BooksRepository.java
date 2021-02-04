package ru.chuhan.demo.db.books;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chuhan.demo.entity.book.Books;
import ru.chuhan.demo.entitysecur.Role;

public interface BooksRepository  extends JpaRepository<Books, Long> {
}
