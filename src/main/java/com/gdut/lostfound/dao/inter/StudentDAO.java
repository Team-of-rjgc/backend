package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentDAO extends JpaRepository<Student, String> {
    Optional<Student> findByUserIdEquals(String userId);

    /**
     * 关键字查询学生列表
     */
    @Query("select s from Student s where (" +
            "s.studentNum like concat('%', :keyword, '%') or s.name like concat('%', :keyword, '%') or " +
            "s.academy like concat('%', :keyword, '%') or s.major like concat('%', :keyword, '%') or " +
            "s.classNum like concat('%', :keyword, '%') or s.campusName like concat('%', :keyword, '%') " +
            ") " +
            "and s.schoolId = :schoolId order by s.createTime asc ")
    Page<Student> findStudentByKeyword(@Param("keyword") String keyword, @Param("schoolId") String schoolId,
                                    Pageable pageable);

}