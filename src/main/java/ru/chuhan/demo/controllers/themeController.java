package ru.chuhan.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.chuhan.demo.db.ThemeRepository;
import ru.chuhan.demo.db.TreeItemRepository;
import ru.chuhan.demo.entity.Theme;
import ru.chuhan.demo.entity.ThemeFromFront;
import ru.chuhan.demo.entity.TreeItem;
import ru.chuhan.demo.entitysecur.User;
import ru.chuhan.demo.service.AnswerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("theme")
public class themeController {

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    TreeItemRepository treeItemRepository;

    @Autowired
    AnswerService answerService;

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public Theme getTheme(Authentication authentication, @PathVariable int id){
        Theme theme = themeRepository.findById(id).get();
        return  theme;
    }

    @RequestMapping(path="/all", method= RequestMethod.GET)
    public Iterable<Theme>  getAllThemes(Authentication authentication){
        Iterable<Theme> themes = themeRepository.findAll();
        return themes;
    }

//    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PreAuthorize ("hasRole('ROLE_USER')")
    @RequestMapping(path="/add", method= RequestMethod.POST)
    public Theme addTheme(Authentication authentication, @RequestBody ThemeFromFront themeFromFront){


        User user = (User)authentication.getPrincipal();

//        Iterable<Theme> themes = themeRepository.findAll();
        TreeItem treeItem = answerService.simpleSaveAnswer(themeFromFront.getStart_qwest());

        Theme theme = new Theme();
        theme.setName(themeFromFront.getName());
        theme.setStart_id(treeItem.getId());
        theme.setUser_id(user.getId());
        theme = themeRepository.save(theme);

        treeItem.setTheme(theme);
        treeItemRepository.save(treeItem);

        return theme;



    }

}
