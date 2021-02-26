package ru.chuhan.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chuhan.demo.entity.book.Sentence;

import java.util.Arrays;
import java.util.Optional;

@Service
public class BotService {

    @Autowired
    SentenceService sentenceService;



    public AnswerCallbackQuery getAnswerCallbackQuery(Update update){

        String[] split = update.getCallbackQuery().getData().split("-");
        if(split.length < 2)
            throw new RuntimeException("getCallbackQuery has less then 2 args");

        Optional<Sentence> sentenceOpt = sentenceService.getById(Integer.parseInt(split[1]));
        Sentence sentence = sentenceOpt.orElseThrow(() -> new RuntimeException("no such Sentence with id" + split[1]));

        String text = sentence.getEng();

        text = maskWords(text, split);


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

    public String maskWords(String text, String[] split){

        if("100".equals(split[0]))
            return text;

        if("50".equals(split[0])) {
            String[] s = text.split(" ");
            for(int i = 0; i< s.length; i++){
                if(i%2 == 0){
                    int length = s[i].length();
                    s[i] = "*".repeat(length);
                }
            }
            return String.join(" " ,Arrays.asList(s));
        }

        if("33".equals(split[0])) {
            String[] s = text.split(" ");
            for(int i = 0; i< s.length; i++){
                if(i%3 != 0){
                    int length = s[i].length();
                    s[i] = "*".repeat(length);
                }
            }
            return String.join(" " ,Arrays.asList(s));
        }

        if("66".equals(split[0])) {
            String[] s = text.split(" ");
            for(int i = 0; i< s.length; i++){
                if(i%3 == 0){
                    int length = s[i].length();
                    s[i] = "*".repeat(length);
                }
            }
            return String.join(" " ,Arrays.asList(s));
        }
        return "Что-то пошло не так!!!";

    }
}
