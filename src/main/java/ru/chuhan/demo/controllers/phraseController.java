package ru.chuhan.demo.controllers;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.chuhan.demo.db.TreeItemRepository;
import ru.chuhan.demo.entity.TreeItem;

@RestController
@RequestMapping("tree")
public class phraseController {

    @Autowired
    TreeItemRepository treeItemRepository;

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public TreeItem getTree(Authentication authentication, @PathVariable int id){

//        22
//        SimpleFilterProvider depthFilters = new SimpleFilterProvider().addFilter("depth_1", new DeepFieldFilter(1))
//                .addFilter("depth_2", new DeepFieldFilter(2))
//                .addFilter("depth_3", new DeepFieldFilter(3));
//
//        ObjectMapper om = new ObjectMapper();
//
//        om.setFilterProvider(depthFilters);
//
//        om.enable(SerializationFeature.INDENT_OUTPUT);


//        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication1.getName();


        TreeItem treeItem1 =  treeItemRepository.findById(id).get();

        return treeItem1;
    }
}
