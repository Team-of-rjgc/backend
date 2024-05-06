package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ManagerDAO extends JpaRepository<Manager, String> {
}
