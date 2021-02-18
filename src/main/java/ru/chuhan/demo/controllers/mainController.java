package ru.chuhan.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
//import ru.chuhan.demo.db.ThemeRepository;
//import ru.chuhan.demo.db.TreeItemRepository;
//import ru.chuhan.demo.db.UserRepository2Old;
import ru.chuhan.demo.entity.*;
import ru.chuhan.demo.entitysecur.Role;
import ru.chuhan.demo.entitysecur.User;
import ru.chuhan.demo.entitysecur.UserInfo;
//import ru.chuhan.demo.service.AnswerService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class mainController {

    @Autowired
    EntityManager entityManager;



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

}

