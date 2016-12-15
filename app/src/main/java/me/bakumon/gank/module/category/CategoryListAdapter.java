package me.bakumon.gank.module.category;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.bakumon.gank.App;
import me.bakumon.gank.R;
import me.bakumon.gank.base.adapter.CommonAdapter4RecyclerView;
import me.bakumon.gank.base.adapter.CommonHolder4RecyclerView;
import me.bakumon.gank.base.adapter.ListenerWithPosition;
import me.bakumon.gank.entity.CategoryResult;
import me.bakumon.gank.module.webview.WebViewActivity;
import me.bakumon.gank.utills.DateUtil;

/**
 * CategoryListAdapter
 * Created by bakumon on 2016/10/13.
 */

public class CategoryListAdapter extends CommonAdapter4RecyclerView<CategoryResult.ResultsBean> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public CategoryListAdapter(Context context) {
        super(context, null, R.layout.item);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, CategoryResult.ResultsBean androidResult) {
        if (androidResult != null) {
            ImageView imageView = holder.getView(R.id.iv_item_img);
            if (App.isListShowImg) { // 列表显示图片
                imageView.setVisibility(View.VISIBLE);
                if (androidResult.images != null && androidResult.images.size() > 0) {
                    Glide.with(mContext).load(androidResult.images.get(0) + "?imageView2/0/w/200").into(imageView);
                } else { // 列表不显示图片
                    Glide.with(mContext).load(R.mipmap.image_default).into(imageView);
                }
            } else {
                imageView.setVisibility(View.GONE);
            }
            holder.setTextViewText(R.id.tv_item_title, androidResult.desc == null ? "unknown" : androidResult.desc);
            holder.setTextViewText(R.id.tv_item_publisher, androidResult.who == null ? "unknown" : androidResult.who);
            holder.setTextViewText(R.id.tv_item_time, DateUtil.dateFormat(androidResult.publishedAt));
            holder.setOnClickListener(this, R.id.ll_item);
        }
    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(WebViewActivity.GANK_TITLE, mData.get(position).desc);
        intent.putExtra(WebViewActivity.GANK_URL, mData.get(position).url);
        mContext.startActivity(intent);
    }
}
