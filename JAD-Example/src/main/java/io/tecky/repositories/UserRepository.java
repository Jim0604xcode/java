package io.tecky.repositories;

import io.tecky.dtos.MemoContent;
import io.tecky.dtos.UserContent;
import io.tecky.models.Memo;
import io.tecky.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRepository extends CustomRepository<User,Integer> {
    List<User> findUsersByUsername(String username);
    @Query(value="""
        SELECT users.id, users.username, users.role from users where users.id = :id
    """, nativeQuery=true)
    List<UserContent> findUserContentsForUserWithNativeQuery(@Param("id") Integer id);


    @Query(value="""
        Select u.id as id, u.username as username, u.role as role from User u WHERE u.id = :id
    """)
    UserContent findUserContentsForUserWithoutNativeQuery(@Param("id") Integer id);

}
