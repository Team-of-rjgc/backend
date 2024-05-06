package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, String> {
    /**
     * 查找反馈列表
     * 未读的在前，已读的在后
     */
    @Query("select f from Feedback f where f.schoolId = :schoolId " +
            "and f.recordStatus = 1 order by f.status asc")
    List<Feedback> listFeedback(@Param("schoolId") String schoolId);
}
