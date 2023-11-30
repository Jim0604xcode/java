package io.tecky.models;
import jakarta.persistence.*;
import java.sql.Date;
@Entity()
@Table(name="memos")
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @Column(name="content")
    private String content;

    @Column(name="created_at", insertable=false, updatable=false)
    private Date createdAt;

    @Column(name="user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", updatable = false, insertable = false)
    private User user;

    public Memo() {
    }


    public void setContent(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
