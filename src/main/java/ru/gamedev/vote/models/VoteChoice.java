package ru.gamedev.vote.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public class VoteChoice {
    private String choice;
    private Long count = 0l;
    private List<VoteDTO> voters = new ArrayList<VoteDTO>();

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<VoteDTO> getVoters() {
        return voters;
    }

    public void setVoters(List<VoteDTO> voters) {
        this.voters = voters;
    }

    public void addCount(Long val) {
        count += val;
    }

    @Override
    public int hashCode() {
        return choice.hashCode();
    }
}
