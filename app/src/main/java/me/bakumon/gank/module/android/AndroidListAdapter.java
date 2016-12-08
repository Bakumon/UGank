package me.bakumon.gank.module.android;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import me.bakumon.gank.R;
import me.bakumon.gank.base.adapter.CommonAdapter4RecyclerView;
import me.bakumon.gank.base.adapter.CommonHolder4RecyclerView;
import me.bakumon.gank.base.adapter.ListenerWithPosition;
import me.bakumon.gank.entity.AndroidResult;
import me.bakumon.gank.utills.DateUtil;
import me.bakumon.gank.utills.GlideUtil;

/**
 * 卖公司列表适配器
 * Created by mafei on 2016/10/13.
 */

public class AndroidListAdapter extends CommonAdapter4RecyclerView<AndroidResult.ResultsBean> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public AndroidListAdapter(Context context) {
        super(context, null, R.layout.item);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, AndroidResult.ResultsBean androidResult) {
        if (androidResult != null) {
            if (androidResult.images != null && androidResult.images.size() > 0) {
                ImageView imageView = holder.getView(R.id.iv_item_img);
                GlideUtil.showImgIfGIf(mContext, androidResult.images.get(0) + "?imageView2/0/w/400", imageView);
            }
            holder.setTextViewText(R.id.tv_item_title, androidResult.desc == null ? "unknown" : androidResult.desc);
            holder.setTextViewText(R.id.tv_item_publisher, androidResult.who == null ? "unknown" : androidResult.who);
            holder.setTextViewText(R.id.tv_item_time, DateUtil.dateFormat(androidResult.createdAt));
        }
    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {

    }
}
