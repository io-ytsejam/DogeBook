package com.pzwpj.projekt.model;

import javax.persistence.*;

@Entity
public class Reaction {
    @Column
    private ReactionType type;
    @Id
    @GeneratedValue
    private Long reactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dogePostId")
    private DogePost dogePost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dogeId")
    private Doge doge;

    public void setReactionId(Long id) {
        this.reactionId = id;
    }

    public Long getReactionId() {
        return reactionId;
    }

    public void setId(Long id) {
        this.reactionId = id;
    }

    public Long getId() {
        return reactionId;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }

    public DogePost getDogePost() {
        return dogePost;
    }

    public void setDogePost(DogePost post) {
        this.dogePost = post;
    }

    public Doge getDoge() {
        return doge;
    }

    public void setDoge(Doge doge) {
        this.doge = doge;
    }

    public Reaction(ReactionType type, DogePost dogePost, Doge doge) {
        this.type = type;
        this.dogePost = dogePost;
        this.doge = doge;
    }

    public Reaction() {
    }
}
