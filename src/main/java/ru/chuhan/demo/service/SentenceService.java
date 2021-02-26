package ru.chuhan.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chuhan.demo.db.books.SentenceRepository;
import ru.chuhan.demo.entity.book.Sentence;

import java.util.List;
import java.util.Optional;

@Service
public class SentenceService {

    @Autowired
    SentenceRepository sentenceRepository;

    public List<Sentence> getAllByBook(int bookId){
        return sentenceRepository.findByBookId(bookId);
    }

    public Sentence getRandom(){
        return sentenceRepository.getRandomRow();
    }

    public Optional<Sentence> getById(Integer id){
        return sentenceRepository.findById(id);
    }

    public void deleteAllByBook(int bookId) {
        sentenceRepository.deleteByBookId(bookId);
    }
}
