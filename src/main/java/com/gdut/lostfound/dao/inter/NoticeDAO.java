package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeDAO extends JpaRepository<Notice, String> {
    /**
     * 查询公告列表
     */
    @Query("select n from Notice  n where n.status=1 " +
            "and n.recordStatus = 1 and n.targetId = :targetId order by n.fixTop desc")
    List<Notice> listNotice(@Param("targetId") String targetId);
}
