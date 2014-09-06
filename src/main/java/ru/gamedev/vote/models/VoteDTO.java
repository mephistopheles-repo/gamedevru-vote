package ru.gamedev.vote.models;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class VoteDTO {
    private String authorName;
    private Long authorId;
    private AuthorLevel authorLevel;
    private String vote;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public AuthorLevel getAuthorLevel() {
        return authorLevel;
    }

    public void setAuthorLevel(AuthorLevel authorLevel) {
        this.authorLevel = authorLevel;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public int hashCode() {
        return authorId.hashCode();
    }
}
