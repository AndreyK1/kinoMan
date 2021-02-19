package ru.chuhan.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chuhan.demo.bot.Bot;

@RestController
@RequestMapping("/telbot")
public class TelegramBotController {

    @Autowired
    Bot bot;

    @RequestMapping(path = "/sendMessage", method = RequestMethod.POST)
    public void sendMessage(@RequestParam(name = "message") String message) throws TelegramApiException {
        bot.sendToTelegram(Bot.CHAT_ID, message);
    }
}
