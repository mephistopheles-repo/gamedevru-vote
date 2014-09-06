package ru.gamedev.vote.models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 06.09.2014
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class PageDTO {
    List<VoteDTO> messageDTOList;
    private Boolean isFirstPage;
    private Boolean isLastPage;

    public Boolean getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(Boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public Boolean getIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(Boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public List<VoteDTO> getMessageDTOList() {
        return messageDTOList;
    }

    public void setMessageDTOList(List<VoteDTO> messageDTOList) {
        this.messageDTOList = messageDTOList;
    }
}
