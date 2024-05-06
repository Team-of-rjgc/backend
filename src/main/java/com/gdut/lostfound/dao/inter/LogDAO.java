package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * lost-found
 * com.gdut.backend.dao.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 12:11 Monday
 */
@Repository
public interface LogDAO extends JpaRepository<Log, Long> {
}
