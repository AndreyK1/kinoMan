package ru.chuhan.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.chuhan.demo.bot.Bot;
import ru.chuhan.demo.entity.book.Sentence;
import ru.chuhan.demo.service.SentenceService;
import ru.chuhan.demo.service.VoiceService;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    SentenceService sentenceService;

    @Autowired
    Bot bot;

    @Autowired
    VoiceService voiceService;

    @RequestMapping(path = "/sendToGroup", method = RequestMethod.GET)
    public void sendMsgToChat() throws Exception {
        Sentence sentence = sentenceService.getRandom();
        System.out.println(sentence);

        byte[] voice = voiceService.getVoice(sentence.getEng());

        bot.sendToTelegramVithMedia(Bot.CHAT_ID, sentence, voice);
    }
}
