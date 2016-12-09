package me.bakumon.gank.entity;

import java.util.List;

/**
 * IOSResult
 * Created by bakumon on 16-12-9.
 */

public class IOSResult {

    /**
     * error : false
     * results : [{"_id":"5847c9d5421aa963f321b026","createdAt":"2016-12-07T16:35:33.610Z","desc":"对绝大多数开发者来说，尽管我们每天都要与编译器打交道，然而实际上编译器对我们来说仍然像一个神秘的黑盒。在本次 try! Swift 的讲演中，Samuel Giddins 从头搭建了一个全新的微型编译器，用来编译他自制的一门编程语言，从而借此去学习编译器的基本工作机制。他还讲述了 Swift 是如何为复杂问题（例如语义解析、词法分析和代码生成）提供优雅的解决方案的。最后，我们将实现一门全新的编程语言，并完成对它的编译工作！","images":["http://img.gank.io/8b94548f-0804-4683-bee7-8d7828f90abb","http://img.gank.io/8b3cf104-4b27-4dbd-8407-769d622ca077"],"publishedAt":"2016-12-09T11:33:12.481Z","source":"chrome","type":"iOS","url":"https://realm.io/cn/news/tryswift-samuel-giddins-building-tiny-compiler-swift-ios/","used":true,"who":"beeender"},{"_id":"584a1fc7421aa963ed5064f9","createdAt":"2016-12-09T11:06:47.769Z","desc":"iOS 展示复杂数学公式的裤子","images":["http://img.gank.io/d814ddd5-a735-4ba2-b456-6fd6771411b1","http://img.gank.io/93ee438f-e87b-4375-9e8e-fe4ecaf5aac0","http://img.gank.io/eb74b42a-6695-4448-9696-086d288633b0"],"publishedAt":"2016-12-09T11:33:12.481Z","source":"chrome","type":"iOS","url":"https://github.com/kostub/iosMath","used":true,"who":"代码家"}]
     */

    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {
        /**
         * _id : 5847c9d5421aa963f321b026
         * createdAt : 2016-12-07T16:35:33.610Z
         * desc : 对绝大多数开发者来说，尽管我们每天都要与编译器打交道，然而实际上编译器对我们来说仍然像一个神秘的黑盒。在本次 try! Swift 的讲演中，Samuel Giddins 从头搭建了一个全新的微型编译器，用来编译他自制的一门编程语言，从而借此去学习编译器的基本工作机制。他还讲述了 Swift 是如何为复杂问题（例如语义解析、词法分析和代码生成）提供优雅的解决方案的。最后，我们将实现一门全新的编程语言，并完成对它的编译工作！
         * images : ["http://img.gank.io/8b94548f-0804-4683-bee7-8d7828f90abb","http://img.gank.io/8b3cf104-4b27-4dbd-8407-769d622ca077"]
         * publishedAt : 2016-12-09T11:33:12.481Z
         * source : chrome
         * type : iOS
         * url : https://realm.io/cn/news/tryswift-samuel-giddins-building-tiny-compiler-swift-ios/
         * used : true
         * who : beeender
         */

        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;
    }
}
