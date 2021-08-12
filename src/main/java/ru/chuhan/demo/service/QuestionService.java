package ru.chuhan.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.chuhan.demo.db.books.SentenceRepository;
import ru.chuhan.demo.db.quiz.QuestionRepository;
import ru.chuhan.demo.entity.book.Sentence;
import ru.chuhan.demo.entity.quiz.Question;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

  @Autowired
  QuestionRepository questionRepository;

  @Transactional
  public void parseQuiz(MultipartFile multipartFile, String quizNum,
                        String partNum, String lang, String delimeter) {
    if(!(lang.equals("ru") || lang.equals("en"))){
      throw new RuntimeException("NO such lang like : " + lang);
    }

    if(delimeter == null || delimeter.isEmpty()){
      throw new RuntimeException("NO delimeter : " + delimeter);
    }

    //

    // Open the file
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
      String strLine;
      int row = 0;
      while ((strLine = br.readLine()) != null) {
        // Print the content on the console
        String[] strings = strLine.split("\\"+delimeter);
        if(lang.equals("ru") && strings.length < 2){
          throw new RuntimeException("Some trouble with parsing string  : " + strLine);
        }

        Question question;

        if(lang.equals("ru")){
          question = new Question();
          question.setRowNum(row);
          question.setPartNum(Integer.parseInt(partNum));
          question.setQuizNum(Integer.parseInt(quizNum));
          question.setRus(strings[0]);
          question.setAnswer(strings[1]);
        }else{
          question = questionRepository.findByQuizNumAndPartNumAndRowNum(Integer.parseInt(quizNum), Integer.parseInt(partNum), row);
          question.setEng(strings[0]);
        }

        questionRepository.save(question);


        System.out.println(strings[0] + "-------");
        row++;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      //Close the input stream
      try {
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public List<Question> getAllByQuiz(int quizNum, int partNum) {
    return questionRepository.findByQuizNumAndPartNum(quizNum, partNum);
  }

  public Question getRandom() {
    return questionRepository.getRandomRow();
  }

  public Optional<Question> getById(Integer id) {
    return questionRepository.findById(id);
  }

  public void deleteAllByQuiz(int quizNum) {
    questionRepository.deleteByQuizNum(quizNum);
  }
}
