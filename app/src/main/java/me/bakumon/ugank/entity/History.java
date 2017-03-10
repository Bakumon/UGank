package me.bakumon.ugank.entity;

import org.litepal.crud.DataSupport;

/**
 * History
 * Created by bakumon on 2017/2/18
 */
public class History extends DataSupport {
    private long createTimeMill;
    private String content;

    public long getCreateTimeMill() {
        return createTimeMill;
    }

    public void setCreateTimeMill(long createTimeMill) {
        this.createTimeMill = createTimeMill;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
