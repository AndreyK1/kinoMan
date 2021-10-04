package ru.chuhan.demo.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chuhan.demo.entity.book.Sentence;
import ru.chuhan.demo.entity.quiz.Question;
import ru.chuhan.demo.service.QuestionService;
import ru.chuhan.demo.service.SentenceService;

import java.util.Date;

import static ru.chuhan.demo.bot.Bot.CHAT_IDS;

@EnableAsync
@Component
public class ScheduledFixedRateMessaging {

    @Autowired
    SentenceService sentenceService;
    @Autowired
    QuestionService questionService;


    @Autowired
    Bot bot;

    Lang last = Lang.EN;

    @Async
    //TODO uncomment
//    @Scheduled(fixedRate = 40000)
    @Scheduled(cron = "1 1 10,12,14,16,18,20 * * ?") //через каждые два часа с 10 до 20

    public void scheduleFixedRateTaskAsync() throws InterruptedException {

        try {
//            bot.sendToTelegram(Bot.CHAT_ID, String.valueOf(System.currentTimeMillis() / 1000));
            Sentence sentence = sentenceService.getRandom();

            if(last.equals(Lang.EN)){
                last = Lang.RU;
            }else{
                last = Lang.EN;
            }

            //TODO
//            System.out.println(new Date());
            CHAT_IDS.forEach(id -> bot.sendSentenceToTelegramVithMedia(id, sentence, last));


        } catch (Exception e) {
            e.printStackTrace();
            bot.sendMessageToAdmin("error - scheduleFixedRateTaskAsync "+ e.getMessage());
        }
//        System.out.println(
//                "Fixed rate task async - " + System.currentTimeMillis() / 1000);
//        Thread.sleep(2000);
    }

    @Async
    //TODO uncomment
//    @Scheduled(fixedRate = 140000)
    @Scheduled(cron = "1 3 10,11,12,13,14,15,16,17,18,19,20 * * ?") //через каждый час с 10 до 20
    public void scheduleTaskAsyncQuestion() throws InterruptedException {

        try {
            Question question = questionService.getRandom();

            //TODO
//            bot.sendQuestionToTelegramVithMedia(Bot.CHAT_ID, question);
            CHAT_IDS.forEach(id -> bot.sendQuestionToTelegramVithMedia(id, question));

        } catch (Exception e) {
            e.printStackTrace();
            bot.sendMessageToAdmin("error - scheduleFixedRateTaskAsync "+ e.getMessage());
        }
    }
}
