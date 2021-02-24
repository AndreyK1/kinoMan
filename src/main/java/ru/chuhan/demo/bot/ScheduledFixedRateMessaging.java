package ru.chuhan.demo.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@EnableAsync
@Component
public class ScheduledFixedRateMessaging {

    @Autowired
    Bot bot;

    @Async
    @Scheduled(fixedRate = 15000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {

        try {
            bot.sendToTelegram(Bot.CHAT_ID, String.valueOf(System.currentTimeMillis() / 1000));
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
//        System.out.println(
//                "Fixed rate task async - " + System.currentTimeMillis() / 1000);
//        Thread.sleep(2000);
    }
}
