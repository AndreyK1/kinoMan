package ru.chuhan.demo.db;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.chuhan.demo.entity.UserOld;

@Repository
public interface UserRepository2Old extends CrudRepository<UserOld, Integer> {
}

