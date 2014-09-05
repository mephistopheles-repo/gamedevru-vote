package ru.gamedev.vote.models;

/**
 * Created with IntelliJ IDEA.
 * Date: 05.09.2014
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public class VoteChoice {
    private String choice;
    private Long count;

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

    @Override
    public int hashCode() {
        return choice.hashCode();
    }
}
