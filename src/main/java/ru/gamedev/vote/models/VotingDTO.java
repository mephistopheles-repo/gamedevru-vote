package ru.gamedev.vote.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
public class VotingDTO {
    private Long voteId;
    private Map<VoteChoice, VoteChoice> choices = new HashMap<VoteChoice, VoteChoice>();
    private Date lastUpdated;

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public Map<VoteChoice, VoteChoice> getChoices() {
        return choices;
    }

    public void setChoices(Map<VoteChoice, VoteChoice> choices) {
        this.choices = choices;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public int hashCode() {
        return voteId.hashCode();
    }
}
