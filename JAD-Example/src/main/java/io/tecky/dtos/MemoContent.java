package io.tecky.dtos;


import java.sql.Date;

public interface MemoContent {
    Integer getId();
    String getContent();

    Date getCreatedAt();
}
