package me.bakumon.ugank.module.search;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import es.dmoral.toasty.Toasty;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.adapter.CommonAdapter4RecyclerView;
import me.bakumon.ugank.base.adapter.CommonHolder4RecyclerView;
import me.bakumon.ugank.base.adapter.ListenerWithPosition;
import me.bakumon.ugank.entity.Favorite;
import me.bakumon.ugank.entity.SearchResult;
import me.bakumon.ugank.module.bigimg.BigimgActivity;
import me.bakumon.ugank.module.webview.WebViewActivity;
import me.bakumon.ugank.utills.DateUtil;

/**
 * SearchListAdapter
 * Created by bakumon on 2016/12/20.
 */

public class SearchListAdapter extends CommonAdapter4RecyclerView<SearchResult.ResultsBean> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public SearchListAdapter(Context context) {
        super(context, null, R.layout.item_search);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, SearchResult.ResultsBean searchResult) {
        if (searchResult != null) {
            holder.setTextViewText(R.id.tv_item_title_search, searchResult.desc == null ? "unknown" : searchResult.desc);
            holder.setTextViewText(R.id.tv_item_type_search, searchResult.type == null ? "unknown" : searchResult.type);
            holder.setTextViewText(R.id.tv_item_publisher_search, searchResult.who == null ? "unknown" : searchResult.who);
            holder.setTextViewText(R.id.tv_item_time_search, DateUtil.dateFormat(searchResult.publishedAt));
            holder.setOnClickListener(this, R.id.ll_item_search);
        }
    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {
        if (mData == null || mData.get(position) == null) {
            Toasty.error(mContext, "数据异常").show();
            return;
        }
        Intent intent = new Intent();
        if ("福利".equals(mData.get(position).type)) {
            intent.setClass(mContext, BigimgActivity.class);
            intent.putExtra(BigimgActivity.MEIZI_TITLE, mData.get(position).desc);
            intent.putExtra(BigimgActivity.MEIZI_URL, mData.get(position).url);
        } else {
            intent.setClass(mContext, WebViewActivity.class);
            intent.putExtra(WebViewActivity.GANK_TITLE, mData.get(position).desc);
            intent.putExtra(WebViewActivity.GANK_URL, mData.get(position).url);
            Favorite favorite = new Favorite();
            favorite.setAuthor(mData.get(position).who);
            favorite.setData(mData.get(position).publishedAt);
            favorite.setTitle(mData.get(position).desc);
            favorite.setType(mData.get(position).type);
            favorite.setUrl(mData.get(position).url);
            favorite.setGankID(mData.get(position).ganhuo_id);
            intent.putExtra(WebViewActivity.FAVORITE_DATA, favorite);
        }
        mContext.startActivity(intent);
    }
}
