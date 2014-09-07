package ru.gamedev.vote.models;

/**
 * Created with IntelliJ IDEA.
 * Date: 07.09.2014
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
public class ColorDTO {
    private String text = "F8F8FF";
    private String back = "C7C7C7";
    private String complete = "239723";

    public ColorDTO(String text, String back, String complete) {
        this.text = text;
        this.back = back;
        this.complete = complete;
    }

    public ColorDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text.length() > 6) {
            text = text.substring(0, 5);
        }
        this.text = text;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        if (back.length() > 6) {
            text = text.substring(0, 5);
        }
        this.back = back;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        if (complete.length() > 6) {
            text = text.substring(0, 5);
        }
        this.complete = complete;
    }
}
