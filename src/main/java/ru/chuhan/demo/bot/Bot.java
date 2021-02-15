package ru.chuhan.demo.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Andrey
//1

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


        //sendingAudio
        sendAudio(update);

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

    public void sendAudio(Update update){
        try {
            execute(SendVoice.builder()
                    .chatId("-1001232767584")
                    .voice(new InputFile().setMedia( new FileInputStream("voice.mp3"), "music"))

                    .caption("caption1")
//                    .captionEntity(MessageEntity.builder()
//                            .type("text")
//                    .text("text1")
//                    .build())

                    .build());

            InlineKeyboardMarkup inlineKeyboardMarkup = createKeyboard();

            execute(SendAudio.builder()
                    .chatId("-1001232767584")
                    .audio(new InputFile().setMedia( new FileInputStream("voice.mp3"), "music"))
                    .caption("caption2")
                    .replyMarkup(inlineKeyboardMarkup)
                    .duration(1)
//                    .captionEntity(MessageEntity.builder()
//                            .type("text")
//                            .text("text2")
//                            .build())
                    .build());
        } catch (TelegramApiException | FileNotFoundException e) {
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


        //
        InlineKeyboardMarkup inlineKeyboardMarkup = createKeyboard();

        SendMessage outMessage = new SendMessage();
        //Указываем в какой чат будем отправлять сообщение
        //(в тот же чат, откуда пришло входящее сообщение)
        outMessage.setChatId(chatId);
//        outMessage.setEntities(Collections.singletonList(MessageEntity.builder().));
        //Указываем текст сообщения
        outMessage.setText( text = "Hello world!    4 " + text.split("@EnglishPhraseBot")[0]);

        outMessage.setReplyMarkup(inlineKeyboardMarkup);
        //Отправляем сообщение
        execute(outMessage);

    }

    public InlineKeyboardMarkup createKeyboard(){
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
        return inlineKeyboardMarkup;
    }
}