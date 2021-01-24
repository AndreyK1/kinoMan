package ru.chuhan.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chuhan.demo.db.ThemeRepository;
import ru.chuhan.demo.db.TreeItemRepository;
import ru.chuhan.demo.entity.AnswerRest;
import ru.chuhan.demo.entity.Theme;
import ru.chuhan.demo.entity.TreeItem;
import ru.chuhan.demo.entitysecur.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    TreeItemRepository treeItemRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Transactional
    public TreeItem addAnswerToDB(AnswerRest answerRest, User user){

        TreeItem treeItem = new TreeItem();
        treeItem.setFirstName(answerRest.text);
        treeItem.setMember(answerRest.member);
//        treeItemRepository.save(treeItem);

        appendThemeToAnswer(answerRest, treeItem);

        TreeItem treeItemParent =  treeItemRepository.findById(Integer.parseInt(answerRest.parent_id)).get();

        treeItem.addParents(treeItemParent);
        treeItem.setUser_id(user.getId());
        treeItemRepository.save(treeItem);

        return treeItem;
    }

    @Transactional
    public TreeItem appendExistingAnswerToQwestion(AnswerRest answerRest, User user) {

        TreeItem treeItemExisting =  treeItemRepository.findById(Integer.parseInt(answerRest.foundid_to_use)).get();

        TreeItem treeItemParent =  treeItemRepository.findById(Integer.parseInt(answerRest.parent_id)).get();

        //раз ответ универсальный, обнуляем тему
        answerRest.for_theme = 0;
        appendThemeToAnswer(answerRest, treeItemExisting);
        //appendThemeToAnswer(answerRest, treeItemExisting);

        treeItemExisting.addParents(treeItemParent);
        treeItemExisting.setUser_id(user.getId());
        treeItemRepository.save(treeItemExisting);

        return treeItemExisting;
    }

    @Transactional
    public TreeItem editAnswer(AnswerRest answerRest, User user) {

        //здесь при изменении на самом деле foundid_to_use - это айди этого же элемента
        TreeItem treeItemExisting = treeItemRepository.findById(Integer.parseInt(answerRest.foundid_to_use)).get();
        appendThemeToAnswer(answerRest, treeItemExisting);
        treeItemExisting.setFirstName(answerRest.text);
        treeItemExisting.setMember(answerRest.member);
        treeItemRepository.save(treeItemExisting);
        return treeItemExisting;

    }


    public void appendThemeToAnswer(AnswerRest answerRest, TreeItem treeItem){
        if(answerRest.for_theme == 0) {
            treeItem.setTheme(null);
        }else{
            Theme theme = themeRepository.findById(answerRest.for_theme).get();
            if(theme!=null){
                treeItem.setTheme(theme);
            }
        }
    }

    public List<TreeItem> findAnswers(AnswerRest answerRest){

        List<TreeItem> treeItemParent =  treeItemRepository.findTop10ByFirstNameContains(answerRest.text);
        return treeItemParent;
    }

    @Transactional
    public TreeItem simpleSaveAnswer(String text){
        TreeItem treeItem = new TreeItem();
        treeItem.setFirstName(text);
        treeItem.setMember(1);
        treeItemRepository.save(treeItem);
        return treeItem;
    }


}
