package me.bakumon.ugank.module.favorite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import es.dmoral.toasty.Toasty;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.adapter.CommonAdapter4RecyclerView;
import me.bakumon.ugank.base.adapter.CommonHolder4RecyclerView;
import me.bakumon.ugank.base.adapter.ListenerWithPosition;
import me.bakumon.ugank.entity.Favorite;
import me.bakumon.ugank.module.webview.WebViewActivity;
import me.bakumon.ugank.utills.DateUtil;

/**
 * FavoriteListAdapter
 * Created by bakumon on 2016/12/20.
 */

public class FavoriteListAdapter extends CommonAdapter4RecyclerView<Favorite> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public FavoriteListAdapter(Context context) {
        super(context, null, R.layout.item_favorite);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, Favorite favorite) {
        if (favorite != null) {
            holder.setTextViewText(R.id.tv_item_title_favorite, favorite.getTitle() == null ? "unknown" : favorite.getTitle());
            holder.setTextViewText(R.id.tv_item_type_favorite, favorite.getType() == null ? "unknown" : favorite.getType());
            holder.setTextViewText(R.id.tv_item_publisher_favorite, favorite.getAuthor() == null ? "unknown" : favorite.getAuthor());
            holder.setTextViewText(R.id.tv_item_time_favorite, DateUtil.dateFormat(favorite.getData()));
            holder.setOnClickListener(this, R.id.ll_item_favorite);
        }
    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {
        // 通过 notifyRemoveItem 方法移除 item 后，不能使用这个 position
        position = holder.getAdapterPosition();
        if (mData == null || mData.get(position) == null) {
            Toasty.error(mContext, "数据异常").show();
            return;
        }
        Intent intent = new Intent();
        intent.setClass(mContext, WebViewActivity.class);
        intent.putExtra(WebViewActivity.GANK_TITLE, mData.get(position).getTitle());
        intent.putExtra(WebViewActivity.GANK_URL, mData.get(position).getUrl());
        intent.putExtra(WebViewActivity.FAVORITE_POSITION, position);
        intent.putExtra(WebViewActivity.FAVORITE_DATA, mData.get(position));
        ((Activity) mContext).startActivityForResult(intent, FavoriteActivity.REQUEST_CODE_FAVORITE);
    }
}
