package ru.gamedev.vote.models;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Date: 21.09.2014
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class FilterCollection implements MessageFilter {

    private MessageFilter userFilter;
    private MessageFilter wordFilter;
    private MessageFilter levelFilter;

    public FilterCollection() {
        this.userFilter = new UserFilter(true, new ArrayList<Long>());
        this.wordFilter = new WordFilter(true, new ArrayList<String>());
        this.levelFilter = new LevelFilter(true, new ArrayList<AuthorLevel>());
    }

    public void setUserFilter(MessageFilter userFilter) {
        this.userFilter = userFilter;
    }

    public void setWordFilter(MessageFilter wordFilter) {
        this.wordFilter = wordFilter;
    }

    public void setLevelFilter(MessageFilter levelFilter) {
        this.levelFilter = levelFilter;
    }

    @Override
    public boolean filter(VoteDTO voteDTO) {
        if (userFilter.filter(voteDTO)) {
            if (levelFilter.filter(voteDTO)) {
                if (wordFilter.filter(voteDTO)) {
                    return true;
                }
            }
        }

        return false;
    }
}
