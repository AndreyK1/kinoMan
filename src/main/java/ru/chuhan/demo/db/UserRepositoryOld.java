package ru.chuhan.demo.db;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.chuhan.demo.entity.WebsiteUser;

import java.util.List;

//not working
//https://www.baeldung.com/spring-data-rest-intro
@RepositoryRestResource(collectionResourceRel = "website_users", path = "users")
public interface UserRepositoryOld extends PagingAndSortingRepository<WebsiteUser, Long> {
    List<WebsiteUser> findByName(@Param("name") String name);
}
