package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.LostFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface LostFoundDAO extends JpaRepository<LostFound, String> {
    /**
     * 根据类别id查出失物招领表中类别的id（用户统计各个类别有几条相关的失物招领记录）
     */
    @Query("select l.categoryId from LostFound  l where l.categoryId in(:idSet) and l.recordStatus = :recordStatus")
    List<String> findAllByCategoryIdInAAndRecordStatusEquals(@Param("idSet") Set<String> idSet,
                                                             @Param("recordStatus") Integer status);

}
