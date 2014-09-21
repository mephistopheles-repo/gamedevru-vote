package ru.gamedev.vote.models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 21.09.2014
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class UserFilter implements MessageFilter {

    private boolean isExcludeFilter;
    private List<Long> ids;

    public UserFilter(boolean isExcludeFilter, List<Long> ids) {
        this.isExcludeFilter = isExcludeFilter;
        this.ids = ids;
    }

    @Override
    public boolean filter(VoteDTO voteDTO) {
        boolean isMatches = false;
        for (Long id : ids) {
            if (id.equals(voteDTO.getAuthorId())) {
                isMatches = true;
                break;
            }
        }
        return isExcludeFilter ? !isMatches : isMatches;
    }
}
