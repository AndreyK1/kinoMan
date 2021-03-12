package ru.chuhan.demo.bot;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chuhan.demo.entity.book.Sentence;
import ru.chuhan.demo.service.BotService;
import ru.chuhan.demo.service.VoiceService;


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

@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final BotService botService;
    private final VoiceService voiceService;

//    public static final String CHAT_ID = "-1001232767584";

    public static final String CHAT_ID = "-1001339562665";

    public static final String ADMIN_ID = "462180992";

    public final String POLLING_BOT_ID = "1689312176:AAG6Gg7NhwA0k455g5ycPlmCBkzoju3A_PE";

//    public final String WHOOK_BOT_ID = "1637222107:AAHQY8VohOQVoQ9mM73psnVxCdtRfnQnA8o";
public final String WHOOK_BOT_ID = "1606769980:AAEGnUdy82fMJ0TsFB9XPG__baEJO2G2TSc";


    /**
     * Метод, который возвращает токен, выданный нам ботом @BotFather.
     * @return токен
     */
    @Override
    public String getBotToken() {
        return WHOOK_BOT_ID;
    }

    /**
     * Метод-обработчик поступающих сообщений.
     * @param update объект, содержащий информацию о входящем сообщении
     */
    @Override
    public void onUpdateReceived(Update update) {

        System.out.println("1+");

        //клик по клавиатуре
        if(update.getCallbackQuery() != null){
            try {
                execute(botService.getAnswerCallbackQuery(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
                sendMessageToAdmin("error - onUpdateReceived - клик по клавиатуре - update.getCallbackQuery().getId() - " + update.getCallbackQuery().getId() +" error " + e.getMessage());
            }
            update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(0).setText("опана");
        }


        //сообщение в канале
        //TODO use когда надо будет обрабатывать любые сообщения в канале
//        if(update.getChannelPost() != null && update.getChannelPost().getText() != null ){
//            try {
//                if(update.getChannelPost().getText().equals("audio")){
//                    //sendingAudio
//                    sendAudio(update);
//                }
//                sendToTelegram(String.valueOf(update.getChannelPost().getSenderChat().getId()), update.getChannelPost().getText());
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//                sendMessageToAdmin("error - onUpdateReceived - сообщение в канале - update.getChannelPost().getSenderChat().getId() - " + update.getChannelPost().getSenderChat().getId() +" error " + e.getMessage());
//            }
//        }


        //личное  сообщение боту
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
            sendMessageToAdmin("error - onUpdateReceived - личное  сообщение боту - inMessage.getChatId() - " + update.getMessage().getChatId() +" " + e.getMessage());
        }
    }

    //test
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

            InlineKeyboardMarkup inlineKeyboardMarkup = createKeyboard("test", Lang.EN);

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
            sendMessageToAdmin("error - sendAudio "+ e.getMessage());
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

    public void sendToTelegramVithMedia(String chatId, Sentence sentence, Lang lang)  {

        String textRu = sentence.getRus().replaceAll("\"","");
        String textEn = sentence.getEng().replaceAll("\"","");

        String textShow = textRu;
        String textTranslate = textEn;

        if(lang.equals(Lang.EN)){
            textShow = textEn;
            textTranslate = textRu;
        }

        byte[] voice = voiceService.getVoice(textEn);


        InlineKeyboardMarkup inlineKeyboardMarkup = createKeyboard(String.valueOf(sentence.getId()), lang);
//        System.out.println(sentence.getRus());
//        System.out.println(textRu);
//        System.out.println(textEn);

        try {
            execute(SendAudio.builder()
                    .chatId(chatId)
                    .audio(new InputFile().setMedia(new ByteArrayInputStream(voice) , "voice"))
                    .caption(textShow)
                    .replyMarkup(inlineKeyboardMarkup)
                    .duration(1)
    //                    .captionEntity(MessageEntity.builder()
    //                            .type("text")
    //                            .text("text2")
    //                            .build())
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendMessageToAdmin("error - sendToTelegramVithMedia "+ e.getMessage());
        }
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
        InlineKeyboardMarkup inlineKeyboardMarkup = createKeyboard("test2", Lang.EN);

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

    public InlineKeyboardMarkup createKeyboard(String sentenceId, Lang lang){


        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton0 = new InlineKeyboardButton();
        inlineKeyboardButton0.setText("33%");
        inlineKeyboardButton0.setCallbackData("33-"+sentenceId+"-"+lang.name());

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("50%");
        inlineKeyboardButton.setCallbackData("50-"+sentenceId+"-"+lang.name());

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton0);
        keyboardButtonsRow1.add(inlineKeyboardButton);
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("66%");
        inlineKeyboardButton1.setCallbackData("66-"+sentenceId+"-"+lang.name());

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("100%");
        inlineKeyboardButton2.setCallbackData("100-"+sentenceId+"-"+lang.name());


        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public void sendMessageToAdmin(String text) {
        SendMessage outMessage = new SendMessage();
        //Указываем в какой чат будем отправлять сообщение
        outMessage.setChatId(ADMIN_ID);
        //Указываем текст сообщения
        outMessage.setText(text);
        //Отправляем сообщение

        try {
            execute(outMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}