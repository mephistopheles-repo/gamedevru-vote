package ru.gamedev.vote.models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 21.09.2014
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */
public class LevelFilter implements MessageFilter {

    private boolean isExcludeFilter;
    private List<AuthorLevel> levels;

    public LevelFilter(boolean isExcludeFilter, List<AuthorLevel> levels) {
        this.isExcludeFilter = isExcludeFilter;
        this.levels = levels;
    }

    @Override
    public boolean filter(VoteDTO voteDTO) {
        boolean isMatches = false;
        for (AuthorLevel level : levels) {
            if (level == voteDTO.getAuthorLevel()) {
                isMatches = true;
                break;
            }
        }
        return isExcludeFilter ? !isMatches : isMatches;
    }
}
