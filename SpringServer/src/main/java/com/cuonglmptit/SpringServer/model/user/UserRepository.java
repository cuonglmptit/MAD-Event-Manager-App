package com.cuonglmptit.SpringServer.model.user;

import com.cuonglmptit.SpringServer.model.event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT username FROM tblUser WHERE username = :username", nativeQuery = true)
    public List<String> checkUsername(@Param("username") String username);

    @Query(value = "SELECT password FROM tblUser WHERE username = :username", nativeQuery = true)
    public String checkUserPasswordByUsername(@Param("username") String username);

    public User findByUsername(String username);
}
