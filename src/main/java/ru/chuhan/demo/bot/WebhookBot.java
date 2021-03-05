//package ru.chuhan.demo.bot;
//
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramWebhookBot;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
//import org.telegram.telegrambots.starter.SpringWebhookBot;
//import org.telegram.telegrambots.util.WebhookUtils;
//
//import java.io.Serializable;
//
//
////englishHookBot
////        1576423108:AAHYosUYEdzy8scqUhe6NtMT0QXsWYPZbkA
//// set url for bot https://xabaras.medium.com/setting-your-telegram-bot-webhook-the-easy-way-c7577b2d6f72
//
//
//
////https://github.com/rubenlagus/TelegramBots/blob/master/telegrambots-spring-boot-starter/src/main/java/org/telegram/telegrambots/starter/SpringWebhookBot.java
//public class WebhookBot extends SpringWebhookBot {
//
////    public static  String BOT_NAME = "englishHookBot";
////    public static String BOT_TOKEN = "1576423108:AAHYosUYEdzy8scqUhe6NtMT0QXsWYPZbkA";
//public static  String BOT_NAME = "EnglishWhBot";
//public static String BOT_TOKEN = "1637222107:AAHQY8VohOQVoQ9mM73psnVxCdtRfnQnA8o";
//
//    public static String URL_WEBHOOK = "https://193.187.173.50:8443/whook";
//
//
////    public static String URL_WEBHOOK = "https://172.17.114.49:8443/whook";
//    public static String PEM_PATH = "/home/django/english/keys/YOURPEM.pem";
////    public static String PEM_PATH = "C:/test/YOURPEM.pem";
//
//    public WebhookBot(SetWebhook setWebhook) {
//        super(setWebhook);
//    }
//
//    public WebhookBot(DefaultBotOptions options, SetWebhook setWebhook) {
//        super(options, setWebhook);
//    }
//
//
//    @Override
//    public String getBotUsername() {
//        return BOT_NAME;
//    }
//
//    @Override
//    public String getBotToken() {
//        return BOT_TOKEN;
//    }
//
//
//    //https://javatalks.ru/topics/55413
//    /**
//     * Метод для приема сообщений.
//     * @param update Содержит сообщение от пользователя.
//     */
//    @Override
//    public BotApiMethod onWebhookUpdateReceived(Update update) {
//        System.out.println("sddddddddddddddddd-------444444444444444444444444444444-----------");
//        System.out.println(update.getMessage());
//        return handleUpdate(update);
//    }
//
//    public SendMessage handleUpdate(Update update) {
//        SendMessage replyMessage = null;
//        if(update.hasMessage()) {
//            if(update.getMessage().getText().toLowerCase().equals("hello")){
//                //TODO
////                    replyMessage = sendInlineKeyBoardMessage(update.getMessage().getChatId(), update);
//            } else {
//                String message = update.getMessage().getText();
//                if (message.equals("Помощь") || message.equals("Привет")) {
//                    replyMessage = sendMsg(update.getMessage().getChatId().toString(), "Нахожусь в стадии тестирования, скоро отвечу.");
//                } else
//
//                    replyMessage = sendMsg(update.getMessage().getChatId().toString(), "Невероятно, со мной разговариваю люди!");
//            }
//        }
////        else  if(update.hasCallbackQuery()) {
////            return new SendMessage().setText(
////                    update.getCallbackQuery().getData())
////                    .setChatId(update.getCallbackQuery().getMessage().getChatId());
////        }
//        return replyMessage;
//    }
//
//    /**
//     * Метод для настройки сообщения и его отправки.
//     * @param chatId id чата
//     * @param s Строка, которую необходимот отправить в качестве сообщения.
//     */
//    public synchronized SendMessage sendMsg(String chatId, String s) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(s);
//
////        setButtons(sendMessage);
//
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//        return sendMessage;
//    }
//
//    @Override
//    public String getBotPath() {
//        return URL_WEBHOOK;
//    }
//
//    @Override
//    public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {
//        WebhookUtils.setWebhook(this, setWebhook);
//    }
//}
