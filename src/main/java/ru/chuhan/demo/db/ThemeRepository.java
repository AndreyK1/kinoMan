package ru.chuhan.demo.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.chuhan.demo.entity.Theme;
import ru.chuhan.demo.entity.TreeItem;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Integer> {
}
