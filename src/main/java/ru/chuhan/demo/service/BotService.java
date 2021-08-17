package ru.chuhan.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chuhan.demo.bot.Lang;
import ru.chuhan.demo.entity.book.Sentence;
import ru.chuhan.demo.entity.quiz.Question;

import java.util.Arrays;
import java.util.Optional;

@Service
public class BotService {

    @Autowired
    SentenceService sentenceService;
    @Autowired
    QuestionService questionService;

    public AnswerCallbackQuery getAnswerCallbackQuery(Update update){
        String[] split = update.getCallbackQuery().getData().split("-");
        if(split.length < 3)
            throw new RuntimeException("getCallbackQuery has less then 3 args");

        if(split[0].equals("S")){
            return getAnswerCallbackQuerySentence(update, split);
        }else if(split[0].equals("Q")){
            return getAnswerCallbackQueryQuestion(update, split);
        }else{
            throw new RuntimeException("it is not known type of callback " + split[0]);
        }

    }

    public AnswerCallbackQuery getAnswerCallbackQueryQuestion(Update update, String[] split) {
        Optional<Question> questionOpt = questionService.getById(Integer.parseInt(split[2]));
        Question question = questionOpt.orElseThrow(() -> new RuntimeException("no such Question with id" + split[2]));
        String text = question.getEng();
        text = maskWords(text, split, question.getAnswer());
        int end = 197;
        if(text.length() > end){
            text = text.substring(0,end) + "...";
        }
        return AnswerCallbackQuery
            .builder()
            .showAlert(true)
            .text(text)
            .callbackQueryId(update.getCallbackQuery().getId())
            .build();
    }

    public AnswerCallbackQuery getAnswerCallbackQuerySentence(Update update, String[] split){



        Optional<Sentence> sentenceOpt = sentenceService.getById(Integer.parseInt(split[2]));
        Sentence sentence = sentenceOpt.orElseThrow(() -> new RuntimeException("no such Sentence with id" + split[2]));

        String text = sentence.getRus();
        if(split[3].equals(Lang.RU.name())){
            text = sentence.getEng(); //надо наоборот
        }

        text = maskWords(text, split, "");

        int end = 197;
        if(text.length() > end){
            text = text.substring(0,end) + "...";
        }

        return AnswerCallbackQuery
                .builder()
                .showAlert(true)
                .text(text)
                .callbackQueryId(update.getCallbackQuery().getId())
//                        .chatId(CHAT_ID)
//                        .inlineMessageId(update.getCallbackQuery().getId())
//
//                        .messageId(update.getCallbackQuery().getMessage().getMessageId())

//                        .inlineMessageId()
//                        .text("ffffffffffff")
                .build();

        //                execute(EditMessageText
//                        .builder()
//                        .chatId(CHAT_ID)
//                        .messageId(update.getCallbackQuery().getMessage().getMessageId())
//                        .text("ffffffffffff")
//                        .build());
        //answerCallbackQuery

    }

    public String maskWords(String text, String[] split, String answer){

        if("100".equals(split[1]))
            return text;

        if("50".equals(split[1])) {
            String[] s = text.split(" ");
            for(int i = 0; i< s.length; i++){
                if(i%2 == 0){
                    int length = s[i].length();
                    s[i] = "*".repeat(length);
                }
            }
            return String.join(" " ,Arrays.asList(s));
        }

        if("33".equals(split[1])) {
            String[] s = text.split(" ");
            for(int i = 0; i< s.length; i++){
                if(i%3 != 0){
                    int length = s[i].length();
                    s[i] = "*".repeat(length);
                }
            }
            return String.join(" " ,Arrays.asList(s));
        }

        if("66".equals(split[1])) {
            String[] s = text.split(" ");
            for(int i = 0; i< s.length; i++){
                if(i%3 == 0){
                    int length = s[i].length();
                    s[i] = "*".repeat(length);
                }
            }
            return String.join(" " ,Arrays.asList(s));
        }

        if("??".equals(split[1])) {
            return answer;
        }
        return "Что-то пошло не так!!!";

    }
}
