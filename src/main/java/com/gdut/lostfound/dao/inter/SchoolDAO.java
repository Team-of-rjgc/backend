package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolDAO extends JpaRepository<School, String> {
    /**
     * 获取学校列表
     */
    @Query("select s from School s where s.campusId = s.schoolId " +
            "and s.status = 1 and s.recordStatus = 1 order by s.createTime")
    List<School> getSchools();
}