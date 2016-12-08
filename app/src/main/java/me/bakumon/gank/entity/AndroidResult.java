package me.bakumon.gank.entity;

import java.util.List;

/**
 * AndroidResult
 * Created by bakumon on 2016/12/8.
 */
public class AndroidResult {

    /**
     * error : false
     * results : [{"_id":"58480f81421aa963efd90da0","createdAt":"2016-12-07T21:32:49.97Z","desc":"子线程到底能不能更新View?非常详细的解答","publishedAt":"2016-12-08T11:42:08.186Z","source":"web","type":"Android","url":"http://www.cnblogs.com/lao-liang/p/5108745.html","used":true,"who":"Li Wenjing"},{"_id":"58481988421aa963ed5064ea","createdAt":"2016-12-07T22:15:36.986Z","desc":"一款简洁，实用，美观的天气应用，通过源码还能学到更多","images":["http://img.gank.io/d87d24a4-24a3-46c0-aaf7-ae3d657485cf"],"publishedAt":"2016-12-08T11:42:08.186Z","source":"web","type":"Android","url":"https://github.com/SilenceDut/KnowWeather","used":true,"who":null},{"_id":"58484775421aa963f321b02e","createdAt":"2016-12-08T01:31:33.540Z","desc":"主题换色，夜间模式，省流量策略。。。这可能是最友好的Gank.io(干货集中营)客户端了","images":["http://img.gank.io/0995f5f4-9b1e-485a-ade2-fd734afad66b","http://img.gank.io/9f633f51-dee6-4658-a617-8ed9961a2384"],"publishedAt":"2016-12-08T11:42:08.186Z","source":"web","type":"Android","url":"https://github.com/wangdicoder/react-native-Gank","used":true,"who":"Di Wang"},{"_id":"58466bbf421aa939b835363b","createdAt":"2016-12-06T15:41:51.890Z","desc":"基于RxJava打造的多线程下载工具, 支持断点续传下载管理等","publishedAt":"2016-12-07T11:43:57.460Z","source":"web","type":"Android","url":"https://github.com/ssseasonnn/RxDownload","used":true,"who":"Season"},{"_id":"58469c0b421aa939b835363c","createdAt":"2016-12-06T19:07:55.777Z","desc":"又一个Android端动态验证码实现","images":["http://img.gank.io/4366217c-9b21-42f2-a081-240d8be5fbfd"],"publishedAt":"2016-12-07T11:43:57.460Z","source":"web","type":"Android","url":"https://github.com/Freshman111/VerificationCodeView","used":true,"who":"sgffsg"},{"_id":"5846e289421aa963ed5064d8","createdAt":"2016-12-07T00:08:41.105Z","desc":"显示飞行器姿态的Android控件","images":["http://img.gank.io/4c16d55b-1b7b-4a7d-b64d-c414904d7222"],"publishedAt":"2016-12-07T11:43:57.460Z","source":"web","type":"Android","url":"https://github.com/billhsu/AndroidAHRSView","used":true,"who":"Shipeng Xu"},{"_id":"58477a91421aa963eaaee129","createdAt":"2016-12-07T10:57:21.941Z","desc":"Android 矢量图动画在线辅助工具","images":["http://img.gank.io/a0c78014-1cf6-4efc-8e87-0387288d254e"],"publishedAt":"2016-12-07T11:43:57.460Z","source":"chrome","type":"Android","url":"https://github.com/romannurik/AndroidIconAnimator","used":true,"who":"代码家"},{"_id":"584507cb421aa939b8353628","createdAt":"2016-12-05T14:23:07.517Z","desc":"自定义SeekBar，进度变化由可视化气泡样式呈现，定制化程度较高，适合大部分需求","images":["http://img.gank.io/d67113ac-1d4c-4291-b0ab-c0ca32834f58"],"publishedAt":"2016-12-06T11:33:36.433Z","source":"web","type":"Android","url":"https://github.com/woxingxiao/BubbleSeekBar","used":true,"who":"Xiao"},{"_id":"58452aea421aa939befafb09","createdAt":"2016-12-05T16:52:58.22Z","desc":"《你的名字》同款日记APP。作者不是我~","images":["http://img.gank.io/ceb8a9c3-aa52-4e11-8610-44ca7ce5f753"],"publishedAt":"2016-12-06T11:33:36.433Z","source":"web","type":"Android","url":"https://github.com/erttyy8821/MyDiary","used":true,"who":"galois"},{"_id":"58461d71421aa939b58d31e4","createdAt":"2016-12-06T10:07:45.342Z","desc":"Android 翻页效果库","publishedAt":"2016-12-06T11:33:36.433Z","source":"chrome","type":"Android","url":"https://github.com/eschao/android-PageFlip","used":true,"who":"代码家"}]
     */

    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {
        /**
         * _id : 58480f81421aa963efd90da0
         * createdAt : 2016-12-07T21:32:49.97Z
         * desc : 子线程到底能不能更新View?非常详细的解答
         * publishedAt : 2016-12-08T11:42:08.186Z
         * source : web
         * type : Android
         * url : http://www.cnblogs.com/lao-liang/p/5108745.html
         * used : true
         * who : Li Wenjing
         * images : ["http://img.gank.io/d87d24a4-24a3-46c0-aaf7-ae3d657485cf"]
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
