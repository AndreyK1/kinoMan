package ru.chuhan.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chuhan.demo.db.books.SentenceRepository;
import ru.chuhan.demo.entity.book.Sentence;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SentenceService {

    @Autowired
    SentenceRepository sentenceRepository;

    public List<Sentence> getAllByBook(int bookId){
        return sentenceRepository.findByBookId(bookId);
    }

    public void deleteAllByBook(int bookId) {
        sentenceRepository.deleteByBookId(bookId);
    }
}
