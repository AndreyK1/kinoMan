package ru.chuhan.demo.bot;

import org.apache.commons.codec.binary.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

//t.me/EnglishPhraseBot.
//https://habr.com/ru/post/418905/    клавиатура
//send msg
// https://dds861.medium.com/sending-message-to-telegram-group-channel-using-bot-from-android-or-java-apps-3c68ffe04a46


//Here is the sequence that worked for me after struggling for several hours:
//
//        Assume the bot name is my_bot.
//
//        1- Add the bot to the group.
//        Go to the group, click on group name, click on Add members, in the searchbox search for your bot like this: @my_bot, select your bot and click add.
//
//        2- Send a dummy message to the bot.
//        You can use this example: /my_id @my_bot
//        (I tried a few messages, not all the messages work. The example above works fine. Maybe the message should start with /)
//
//3- Go to following url: https://api.telegram.org/botXXX:YYYY/getUpdates
//        replace XXX:YYYY with your bot token
//
//        4- Look for "chat":{"id":-zzzzzzzzzz,
//        -zzzzzzzzzz is your chat id (with the negative sign).
//
//        5- Testing: You can test sending a message to the group with a curl:
//
//        curl -X POST "https://api.telegram.org/botXXX:YYYY/sendMessage" -d "chat_id=-zzzzzzzzzz&text=my sample text"

//channelPost
//Chat(id=-1001232767584, type=channel, title=Alliexpress,


public class Bot extends TelegramLongPollingBot {
    /**
     * Метод, который возвращает токен, выданный нам ботом @BotFather.
     * @return токен
     */
    @Override
    public String getBotToken() {
        return "1689312176:AAG6Gg7NhwA0k455g5ycPlmCBkzoju3A_PE";
    }

    /**
     * Метод-обработчик поступающих сообщений.
     * @param update объект, содержащий информацию о входящем сообщении
     */
    @Override
    public void onUpdateReceived(Update update) {


        if(update.getChannelPost() != null && update.getChannelPost().getText() != null ){
            try {
                sendToTelegram(String.valueOf(update.getChannelPost().getSenderChat().getId()), update.getChannelPost().getText());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        try {
            //проверяем есть ли сообщение и текстовое ли оно
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем объект входящего сообщения
                Message inMessage = update.getMessage();
                //Создаем исходящее сообщение
                SendMessage outMessage = new SendMessage();
                //Указываем в какой чат будем отправлять сообщение
                //(в тот же чат, откуда пришло входящее сообщение)
                outMessage.setChatId(String.valueOf(inMessage.getChatId()));
                //Указываем текст сообщения
                outMessage.setText(inMessage.getText());
                //Отправляем сообщение
                execute(outMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, который возвращает имя пользователя бота.
     * @return имя пользователя
     */
    @Override
    public String getBotUsername() {
        return "EnglishPhraseBot";
    }


    public void sendToTelegram(String chatId, String text) throws TelegramApiException {
//        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
//
//        //Add Telegram token (given Token is fake)
//        String apiToken = "1689312176:AAG6Gg7NhwA0k455g5ycPlmCBkzoju3A_PE";
//
//        //Add chatId (given chatId is fake)
////        String chatId = "-1001232767584";
//        text = "Hello world!" + text.split("@EnglishPhraseBot")[0];
//
//        urlString = String.format(urlString, apiToken, chatId, text);
//
//        try {
//            URL url = new URL(urlString);
//            URLConnection conn = url.openConnection();
//            InputStream is = new BufferedInputStream(conn.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //
        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Тык");
        inlineKeyboardButton.setCallbackData("Button \"Тык\" has been pressed");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);

        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Fi4a");
        inlineKeyboardButton1.setCallbackData("CallFi4a");
        keyboardButtonsRow1.add(inlineKeyboardButton1);

        inlineKeyboardMarkup.setKeyboard(rowList);
        //


        SendMessage outMessage = new SendMessage();
        //Указываем в какой чат будем отправлять сообщение
        //(в тот же чат, откуда пришло входящее сообщение)
        outMessage.setChatId(chatId);
        //Указываем текст сообщения
        outMessage.setText( text = "Hello world!    4 " + text.split("@EnglishPhraseBot")[0]);

        outMessage.setReplyMarkup(inlineKeyboardMarkup);
        //Отправляем сообщение
        execute(outMessage);

    }
}