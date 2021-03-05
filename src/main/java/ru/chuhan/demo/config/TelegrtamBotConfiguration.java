package ru.chuhan.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.chuhan.demo.bot.Bot;
import ru.chuhan.demo.service.BotService;
import ru.chuhan.demo.service.VoiceService;

@Configuration
@EnableScheduling
public class TelegrtamBotConfiguration {


    //ЭТО НЕЗАРЕГАННЫЙ ЛОНГПОЛИНГ БУТ, КОТОРЫЙ МЫ ИСПОЛЬЗУЕМ КАК WEBHOOK БОТ(ДЕРГАЕМ ЕГО ЧЕРЕЗ КОНТРОЛЕР)
    //ЕСЛИ ЮЗАТЬ ЕГО КАК ЛОНГПОЛИНГ, ТО НАДО РАЗКОМЕНТИТЬ (3), или телеграм-бут-стартер в ПОМ разкоментить, тиогда он зарегается и начнет сам тянуть с телеги данные, а не ждать сигнала на ручку whook, которую мы зарегали в обычном спринг-бут контроолере
    @Bean
    public Bot createBot(BotService botService, VoiceService voiceService) {
       return new Bot(botService, voiceService);
    }


    // (3) ЭТО ЕСЛИ ПОДНИМАТЬ LONGPOLLING BOOT БЕЗ СПРИНГ БУТА
//    @Bean
//    public TelegramBotsApi createTelegramBotsApi() {
//        TelegramBotsApi telegramBotsApi;
//        try {
//            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(createBot(null, null));
//            return telegramBotsApi;
//        } catch (
//                TelegramApiRequestException e) {
//            e.printStackTrace();
//        } catch (TelegramApiException telegramApiException) {
//            telegramApiException.printStackTrace();
//        }
//        return null;
//    }



    //-------ЭТО ЕСЛИ НАСТРАИВАТЬ WEBHOOK БОТА ЧЕРЕЗ СПРИНГ БУТ (ПОДНИМАЕТСЯ ЕЩЕ ОДИН СЕРВЕР)
//    @Bean
//    public WebhookBot createBot1(SetWebhook setWebhook) {
//
//        return new WebhookBot(setWebhook);
//    }
//    @Bean
//    public SetWebhook setWebhookInstance() {
//        SetWebhook setWebhook = new SetWebhook();
//        setWebhook.setUrl(URL_WEBHOOK);
//        try {
//            setWebhook.setCertificate(
//                    new InputFile().setMedia( new FileInputStream(PEM_PATH), "certificate"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return setWebhook;
//    }
//    @Bean
//    public TelegramBotsApi createTelegramBotsApi(SetWebhook setWebhook) {
//        TelegramBotsApi telegramBotsApi = null;
//        DefaultWebhook defaultWebhook = new DefaultWebhook();
//        defaultWebhook.setInternalUrl(URL_WEBHOOK);
//
//        try {
//            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, defaultWebhook);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//        return telegramBotsApi;
//    }



}
