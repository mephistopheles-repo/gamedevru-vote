package ru.gamedev.vote.models;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Date: 07.09.2014
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
public class ImageDTO {
    private ByteArrayOutputStream imageData;
    private Date lastUpdated;

    public ByteArrayOutputStream getImageData() {
        return imageData;
    }

    public void setImageData(ByteArrayOutputStream imageData) {
        this.imageData = imageData;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
