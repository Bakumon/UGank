package me.bakumon.ugank.entity;

import java.util.List;

/**
 * SearchResult
 * Created by bakumon on 2016/12/19 17:00.
 */
public class SearchResult {

    public int count;
    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {

        public String desc;
        public String ganhuo_id;
        public String publishedAt;
        public String readability;
        public String type;
        public String url;
        public String who;
    }
}
