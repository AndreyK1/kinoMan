package ru.chuhan.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.chuhan.demo.bot.Bot;

@Configuration
@EnableScheduling
public class TelegrtamBotConfiguration {

    @Bean
    public Bot createBot() {
       return new Bot();
    }

    @Bean
    public TelegramBotsApi createTelegramBotsApi() {
        TelegramBotsApi telegramBotsApi;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(createBot());
            return telegramBotsApi;
        } catch (
                TelegramApiRequestException e) {
            e.printStackTrace();
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
        return null;
    }
}
