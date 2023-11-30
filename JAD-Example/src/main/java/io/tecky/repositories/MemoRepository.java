package io.tecky.repositories;
import io.tecky.dtos.MemoContent;
import io.tecky.models.Memo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemoRepository extends JpaRepository<Memo,Integer> {

    List<Memo> findByContent(String content);

    @Query(value="""
        SELECT memos.content, memos.id from memos inner join users on memos.user_id = users.id where users.id = :id
    """, nativeQuery=true)
    List<MemoContent> findMemoContentsForUser(@Param("id") Integer id);

    @Modifying
    @Query(value= """
            Update Memo m set content = 'testing'
            """)
    void updateMemoContentToTesting();
}