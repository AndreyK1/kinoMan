package ru.chuhan.demo.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chuhan.demo.entity.book.Sentence;
import ru.chuhan.demo.service.SentenceService;

import java.util.Date;

@EnableAsync
@Component
public class ScheduledFixedRateMessaging {

    @Autowired
    SentenceService sentenceService;

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
            bot.sendToTelegramVithMedia(Bot.CHAT_ID, sentence, last);

        } catch (Exception e) {
            e.printStackTrace();
            bot.sendMessageToAdmin("error - scheduleFixedRateTaskAsync "+ e.getMessage());
        }
//        System.out.println(
//                "Fixed rate task async - " + System.currentTimeMillis() / 1000);
//        Thread.sleep(2000);
    }
}
