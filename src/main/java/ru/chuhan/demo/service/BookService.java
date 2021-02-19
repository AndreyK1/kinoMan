package ru.chuhan.demo.service;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.chuhan.demo.db.books.BooksRepository;
import ru.chuhan.demo.db.books.SentenceRepository;
import ru.chuhan.demo.entity.book.Book;
import ru.chuhan.demo.entity.book.Sentence;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    SentenceRepository sentenceRepository;

    @Autowired
    SentenceService sentenceService;

    @Transactional
    public void parseBook(MultipartFile multipartFile, String author, String bookName){
//        File file = new File(bookPath);
        try {
//            FileInputStream fs = new FileInputStream(file);
//            OPCPackage d = OPCPackage.open(fs);
//        XWPFWordExtractor xw = new XWPFWordExtractor(d);
//        System.out.println(xw.getText());



            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(multipartFile.getInputStream()));
            List<XWPFParagraph> paragraphList = xdoc.getParagraphs();

            Book book = new Book().setAuthor(author).setName(bookName);
            Book save = booksRepository.save(book);

            int par=0;
            XWPFParagraph paragraph = paragraphList.get(0);
//            for (XWPFParagraph paragraph : paragraphList) {
            for (var table : paragraph.getBody().getTables()) {
//                oldParagraphHandler(paragraph);
//                List<XWPFTableRow> rows = paragraph.getBody().getTables().get(0).getRows();
                List<XWPFTableRow> rows = table.getRows();
                for(int row=0; row < rows.size(); row++){
                    String rus = rows.get(row).getCell(0).getText();
                    String eng = rows.get(row).getCell(1).getText();
                    if(rus.length() > 10 && eng.length() > 10){
                        Sentence sentence = new Sentence()
                                .setBookId(book.getId())
                                .setRowNum(row)
                                .setParagraphNum(par)
                                .setRus(rus)
                                .setEng(eng);
                        sentenceRepository.save(sentence);
                    }
                }
                par++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        List<Sentence> all = sentenceRepository.findAll();
        List<Book> all1 = booksRepository.findAll();
        System.out.println("dfsdfsdf");
    }

    public List<Book> getAllBooks(){
        return booksRepository.findAll();
    }

    @Transactional
    public void deleteBook(int bookId) {
        booksRepository.deleteById(bookId);
        sentenceService.deleteAllByBook(bookId);
    }


//    public void oldParagraphHandler(XWPFParagraph paragraph){
//        paragraph.getBody().getTables().get(0).getRows().get(10).getCell(0).getText();
//
//        System.out.println(paragraph.getText());
//        System.out.println(paragraph.getAlignment());
//        System.out.print(paragraph.getRuns().size());
//        System.out.println(paragraph.getStyle());
//
//        // Returns numbering format for this paragraph, eg bullet or lowerLetter.
//        System.out.println(paragraph.getNumFmt());
//        System.out.println(paragraph.getAlignment());
//
//        System.out.println(paragraph.isWordWrapped());
//        System.out.println("********************************************************************");
//    }


}
