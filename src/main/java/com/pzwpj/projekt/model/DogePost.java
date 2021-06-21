package com.pzwpj.projekt.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DogePost {
    @Column
    private String content;
    @Column
    private LocalDateTime creationTime;
    @OneToMany(mappedBy = "dogePost", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reaction> reactions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dogeId")
    private Doge doge;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Id
    @GeneratedValue
    private Long dogePostId;

    @Column
    private boolean edited;

    public void setId(Long id) {
        this.dogePostId = id;
    }

    public Long getId() {
        return dogePostId;
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

    public Doge getDoge() {
        return doge;
    }

    public void setDoge(Doge doge) {
        this.doge = doge;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getDogePostId() {
        return dogePostId;
    }

    public void setDogePostId(Long dogePostId) {
        this.dogePostId = dogePostId;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public DogePost(String content, LocalDateTime creationTime, List<Reaction> reactions, Doge doge, List<Comment> comments, Long dogePostId) {
        this.content = content;
        this.creationTime = creationTime;
        this.reactions = reactions;
        this.doge = doge;
        this.comments = comments;
        this.dogePostId = dogePostId;
    }

    public DogePost() {
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
