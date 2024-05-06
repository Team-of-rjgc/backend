package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDAO extends JpaRepository<Log, Long> {
}
