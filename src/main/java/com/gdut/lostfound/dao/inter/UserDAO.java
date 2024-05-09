package com.gdut.lostfound.dao.inter;

import com.gdut.lostfound.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserDAO extends JpaRepository<User, String> {

    User findByNickNameEquals(String nickName);

    User findByEmailEquals(String email);

}