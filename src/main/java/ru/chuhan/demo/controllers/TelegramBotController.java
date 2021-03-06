package ru.chuhan.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chuhan.demo.bot.Bot;
import ru.chuhan.demo.dto.CommonMsgDto;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/telbot")
public class TelegramBotController {

    @Autowired
    Bot bot;

    @RequestMapping(path = "/sendMessage", method = RequestMethod.POST)
    public void sendMessage(@RequestBody CommonMsgDto commonMsgDto) throws TelegramApiException {
        bot.sendToTelegram(Bot.CHAT_ID, commonMsgDto.getMessage());
    }


//ЭТО WEEBHOOK РУЧКА, КОТОРУЮ МЫ ИСПОЛЬЗУЕМ ВМЕСТО КРИВОГО ДИДЛИОТЕЧНОГО WEEBHOOK БОТА, КОТОРЫЙ ПОДНИМАЕТ ЕЩЕ СЕРВЕР
    //НО ТАК КАК МЫ НЕ ЮЗАЕМ БИБЛИОТЕЧНОГО, НАДО СВМОСТОЯТЕЛЬНО ЗАРЕГАТЬ РУЧКУ И САМОПОДПИСАННЫЙ СЕРТ В ТЕЛЕГЕ (СМОТРИ ЕВЕРНОУТС)
    @RequestMapping(path = "/whook", method = RequestMethod.POST)
    public Response updateReceived(@RequestBody Update update) {
        System.out.println("sadsaaaaaasad----------------------");
        System.out.println(update.getMessage());

        //!!!!!TODO
        bot.onUpdateReceived(update);

//    public Response updateReceived(@PathParam("botPath") String botPath, Update update) {
        return Response.ok(null).build();
    }

//    @RequestMapping(path = "/test1", method = RequestMethod.GET)
//    public Response updateReceiv() {
//        System.out.println("1111111111111111sadsaaaaaasad----------------------");
////        System.out.println(update.getMessage());
////    public Response updateReceived(@PathParam("botPath") String botPath, Update update) {
//        return Response.ok(null).build();
//    }
//
//    @RequestMapping(path = "/test2", method = RequestMethod.POST)
//    public Response updateReceiv1() {
//        System.out.println("222222222221111111111111111sadsaaaaaasad----------------------");
////        System.out.println(update.getMessage());
////    public Response updateReceived(@PathParam("botPath") String botPath, Update update) {
//        return null;
//    }
}
