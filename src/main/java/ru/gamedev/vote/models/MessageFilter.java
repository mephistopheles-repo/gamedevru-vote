package ru.gamedev.vote.models;

/**
 * Created with IntelliJ IDEA.
 * Date: 20.09.2014
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public interface MessageFilter {
    boolean filter(VoteDTO voteDTO);
}
