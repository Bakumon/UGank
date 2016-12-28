package me.bakumon.ugank.module.search;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import me.bakumon.ugank.R;
import me.bakumon.ugank.base.adapter.CommonAdapter4RecyclerView;
import me.bakumon.ugank.base.adapter.CommonHolder4RecyclerView;
import me.bakumon.ugank.base.adapter.ListenerWithPosition;
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
        if (mData.get(position) == null) {
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
        }
        mContext.startActivity(intent);
    }
}
