package ru.chuhan.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.chuhan.demo.db.ThemeRepository;
import ru.chuhan.demo.db.TreeItemRepository;
import ru.chuhan.demo.db.UserRepository2Old;
import ru.chuhan.demo.entity.*;
import ru.chuhan.demo.entitysecur.Role;
import ru.chuhan.demo.entitysecur.User;
import ru.chuhan.demo.entitysecur.UserInfo;
import ru.chuhan.demo.service.AnswerService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class mainController {

    @Autowired
    UserRepository2Old userRepository2Old;

    @Autowired
    TreeItemRepository treeItemRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    AnswerService answerService;

    //test
    @RequestMapping(path="/create", method= RequestMethod.POST)
    public Content index(@RequestBody Content content) {

//        treeItemDbManipulation();

        return new Content(content.customer_id, content.customer_content);
    }


    @RequestMapping(path="/getuserinfo", method= RequestMethod.POST)
    public UserInfo getTree(Authentication authentication){
        UserInfo userInfo = new UserInfo();
        if(authentication != null){
            userInfo.setName(authentication.getName());
            Set<Role> roleList = (Set<Role>) ((User)authentication.getPrincipal()).getRoles();
            List<String> strings = roleList.stream().map(role -> role.getName()).collect(Collectors.toList());
            userInfo.setRoles(strings);
        }

        return  userInfo;

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(path="/addAnswer", method= RequestMethod.POST)
    public TreeItem addAnswer(Authentication authentication, @RequestBody AnswerRest answerRest) {

        User user = (User)authentication.getPrincipal();

        if(answerRest.foundid_to_use.isEmpty()){
            //создаем новый ответ
            return  answerService.addAnswerToDB(answerRest, user);
        }else{
            //прикрепляем найденный по Id ответ к вопросу
            return answerService.appendExistingAnswerToQwestion(answerRest, user);
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path="/editAnswer", method= RequestMethod.POST)
    public TreeItem editAnswer(Authentication authentication, @RequestBody AnswerRest answerRest) {

        User user = (User)authentication.getPrincipal();


       return answerService.editAnswer(answerRest, user);

    }




    @RequestMapping(path="/findAnswer", method= RequestMethod.POST)
    public List<TreeItem> findAnswer(@RequestBody AnswerRest answerRest) {

        return  answerService.findAnswers(answerRest);
    }




        @RequestMapping(path="/info", method= RequestMethod.GET)
    public TreeItem indexit(){

//        SimpleFilterProvider depthFilters = new SimpleFilterProvider().addFilter("depth_1", new DeepFieldFilter(1))
//                .addFilter("depth_2", new DeepFieldFilter(2))
//                .addFilter("depth_3", new DeepFieldFilter(3));
//
//        ObjectMapper om = new ObjectMapper();
//
//        om.setFilterProvider(depthFilters);
//
//        om.enable(SerializationFeature.INDENT_OUTPUT);


            TreeItem treeItem1 =  treeItemRepository.findById(22).get();

        return treeItem1;
    }


    @Transactional
    public void treeItemDbManipulation(){
//        UserOld userOld = new UserOld();
//        userOld.setFirstName("asdsa1122334445577");
//        userRepository2Old.save(userOld);


        TreeItem treeItem = new TreeItem();
        treeItem.setFirstName("papa8");
        treeItemRepository.save(treeItem);


        TreeItem treeItemChild = new TreeItem();
        treeItemChild.setFirstName("child8");
        treeItemChild.addParents(treeItem);
//        treeItemChild.setParent(treeItem);
        treeItemRepository.save(treeItemChild);

        TreeItem treeItemChild1 = new TreeItem();
        treeItemChild1.setFirstName("child81");
//        treeItemChild1.setParent(treeItem);
        treeItemChild1.addParents(treeItem);
        treeItemRepository.save(treeItemChild1);

        TreeItem treeItemChild2 = new TreeItem();
        treeItemChild2.setFirstName("child82");
//        treeItemChild1.setParent(treeItem);
        treeItemChild2.addParents(treeItem);
        treeItemRepository.save(treeItemChild2);

        treeItemChild1.addParents(treeItemChild2);
        treeItemRepository.save(treeItemChild1);





//        entityManager.refresh(treeItem);
//        treeItem.addChildren(treeItemChild);
//        treeItemRepository.save(treeItem);
//        treeItemChild.setParent(treeItem);

//        TreeItem treeItem1 =  treeItemRepository.findById(2).get();
//
//
//
//        TreeItem treeItem2 =  treeItemRepository.findById(3).get();

//        treeItemChild1.addChilds(treeItem1);
//        treeItemChild1.addChilds(treeItem2);

        TreeItem treeItem1 =  treeItemRepository.findById(treeItem.getId()).get();

        System.out.println("zxcxzc");
    }
}

