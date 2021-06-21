package com.pzwpj.projekt.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Column
    private String content;
    @Column
    private LocalDateTime creationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dogePostId")
    private DogePost post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dogeId")
    private Doge doge;
    @Id
    @GeneratedValue
    private Long commentId;

    public void setCommentId(Long id) {
        this.commentId = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public DogePost getPost() {
        return post;
    }

    public void setPost(DogePost post) {
        this.post = post;
    }

    public Doge getDoge() {
        return doge;
    }

    public void setDoge(Doge doge) {
        this.doge = doge;
    }

    public Comment(String content, LocalDateTime creationTime, DogePost post, Doge doge) {
        this.content = content;
        this.creationTime = creationTime;
        this.post = post;
        this.doge = doge;
    }

    public Comment() {
    }
}
