package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.Category;
import com.gdut.lostfound.dao.entity.CategoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryDAO extends JpaRepository<Category, CategoryKey> {
    /**
     * 通过类型名查询类别
     */
    Category findByNameEquals(String name);

    /**
     * 通过类型名称和目标id（校区id）查找类别
     */
    Category findByNameEqualsAndTargetIdEquals(String name, String targetId);
}
