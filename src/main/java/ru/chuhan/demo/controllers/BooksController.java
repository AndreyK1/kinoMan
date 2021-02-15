package ru.chuhan.demo.controllers;

import com.voicerss.tts.*;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.chuhan.demo.service.BookService;

//import java.io.FileOutputStream;
//import com.voicerss.tts.AudioCodec;
//import com.voicerss.tts.AudioFormat;
//import com.voicerss.tts.Languages;
//import com.voicerss.tts.SpeechDataEvent;
//import com.voicerss.tts.SpeechDataEventListener;
//import com.voicerss.tts.SpeechErrorEvent;
//import com.voicerss.tts.SpeechErrorEventListener;
//import com.voicerss.tts.VoiceParameters;
//import com.voicerss.tts.VoiceProvider;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    BookService bookService;

    @RequestMapping(path = "/parse", method = RequestMethod.GET)
    public void parse() {
        bookService.parseBook("c:\\bo\\ff.docx");
    }


    //http://www.voicerss.org/personel/
    //http://www.voicerss.org/sdk/java.aspx
    @RequestMapping(path = "/voice", method = RequestMethod.GET)
    public void voice() throws Exception {
        VoiceProvider tts = new VoiceProvider("b6c7619ce9a942f7a639a74c882553c9");

        VoiceParameters params = new VoiceParameters(
                "I ask the indulgence of the children who may read this book for dedicating it to a grown-up. I have a serious reason: he is the best friend I have in the world."
                , Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        //8khz_8bit_mono.
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        //скорость
        params.setRate(0);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream("voice.mp3");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();



    }

//    public static void main (String args[]) throws Exception {
//        VoiceProvider tts = new VoiceProvider("<API key>");
//
//        VoiceParameters params = new VoiceParameters("Hello, world!", Languages.English_UnitedStates);
//        params.setCodec(AudioCodec.WAV);
//        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
//        params.setBase64(false);
//        params.setSSML(false);
//        params.setRate(0);
//
//        byte[] voice = tts.speech(params);
//
//        FileOutputStream fos = new FileOutputStream("voice.mp3");
//        fos.write(voice, 0, voice.length);
//        fos.flush();
//        fos.close();
//    }
}
