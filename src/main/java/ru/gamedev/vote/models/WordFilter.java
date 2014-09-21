package ru.gamedev.vote.models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 21.09.2014
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
public class WordFilter implements MessageFilter {

    private boolean isExcludeFilter;
    private List<String> words;

    public WordFilter(boolean isExcludeFilter, List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            words.set(i, words.get(i).toLowerCase());
        }
        this.isExcludeFilter = isExcludeFilter;
        this.words = words;
    }

    @Override
    public boolean filter(VoteDTO voteDTO) {
        boolean isMatches = false;
        for (String word : words) {
            String vote = voteDTO.getVote().toLowerCase();
            if (vote.contains(word)) {
                isMatches = true;
                break;
            }
        }
        return isExcludeFilter ? !isMatches : isMatches;
    }
}
