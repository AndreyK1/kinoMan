package ru.chuhan.demo.controllers;

import com.voicerss.tts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.chuhan.demo.entity.book.Book;
import ru.chuhan.demo.entity.book.Sentence;
import ru.chuhan.demo.service.BookService;
import ru.chuhan.demo.service.SentenceService;

import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    BookService bookService;

    @Autowired
    SentenceService sentenceService;

    @RequestMapping(path = "/parse", method = RequestMethod.GET)
    public void parse() {
//        bookService.parseBook("c:\\bo\\ff.docx", "sads", "sdfsdf");
    }


    //http://www.voicerss.org/personel/
    //http://www.voicerss.org/sdk/java.aspx
    //test
    @RequestMapping(path = "/voice", method = RequestMethod.GET)
    public void voice() throws Exception {
        VoiceProvider tts = new VoiceProvider("b6c7619ce9a942f7a639a74c882553c9");

        VoiceParameters params = new VoiceParameters(
                "I may ask the indulgence of the children who may read this book for dedicating it to a grown-up. I have a serious reason: he is the best friend I have in the world."
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



    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @RequestMapping(path = "/allSentences/{idBook}", method = RequestMethod.GET)
    public List<Sentence> getAllSentences(@PathVariable(name = "idBook") int bookId) {
        return sentenceService.getAllByBook(bookId);
    }

    @RequestMapping(path = "/deleteBook/{idBook}", method = RequestMethod.GET)
    public void deleteBook(@PathVariable(name = "idBook") int bookId) {
        bookService.deleteBook(bookId);
//        sentenceService.deleteAllByBook(bookId);
    }

    //pdf to word
//https://www.pdf2go.com/ru/result#j=1ff8d1c8-c3f4-4a8e-a8e1-464b78e89890
    //books from here
    //https://fenglish.ru/the-oval-portrait-by-edgar-allan-poe-edgar-po-ovalnyj-portret/
    @RequestMapping(path = "/parsebook", method = RequestMethod.POST)
    public void parseBook(@RequestParam(name = "file") MultipartFile multipartFile,
                          @RequestParam(name = "author") String author, @RequestParam(name = "bookName") String bookName) {
//        bookService.deleteBook(bookId);
//        sentenceService.deleteAllByBook(bookId);
//            InputStream inputStream = multipartFile.getInputStream();
            bookService.parseBook(multipartFile,  author, bookName);
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
