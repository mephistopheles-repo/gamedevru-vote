package ru.gamedev.vote.models;

/**
 * Created with IntelliJ IDEA.
 * Date: 20.09.2014
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class ConfigDTO {
    private MessageFilter messageFilter;

    public MessageFilter getMessageFilter() {
        return messageFilter;
    }

    public void setMessageFilter(MessageFilter messageFilter) {
        this.messageFilter = messageFilter;
    }
}
