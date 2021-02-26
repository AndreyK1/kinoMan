package ru.chuhan.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.chuhan.demo.bot.Bot;
import ru.chuhan.demo.service.BotService;

@Configuration
@EnableScheduling
public class TelegrtamBotConfiguration {

    @Bean
    public Bot createBot(BotService botService) {
       return new Bot(botService);
    }

    @Bean
    public TelegramBotsApi createTelegramBotsApi() {
        TelegramBotsApi telegramBotsApi;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(createBot(null));
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
