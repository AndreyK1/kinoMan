package ru.chuhan.demo.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.chuhan.demo.entity.TreeItem;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;


@Repository
    public interface TreeItemRepository extends CrudRepository<TreeItem, Integer> {
        List<TreeItem> findTop10ByFirstNameContains(String text);


//    @Query("SELECT m FROM Master m WHERE m.userId = :userId")
//    Optional<TreeItem> findAllByIdAAndThemeId(Integer id, Integer theme_id);
}
