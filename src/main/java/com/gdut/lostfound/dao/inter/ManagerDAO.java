package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * lost-found
 * com.gdut.backend.inter.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/07 19:17 Sunday
 */
@Repository
public interface ManagerDAO extends JpaRepository<Manager, String> {
}
