package me.bakumon.gank.module.search;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import me.bakumon.gank.R;
import me.bakumon.gank.base.adapter.CommonAdapter4RecyclerView;
import me.bakumon.gank.base.adapter.CommonHolder4RecyclerView;
import me.bakumon.gank.base.adapter.ListenerWithPosition;
import me.bakumon.gank.entity.SearchResult;
import me.bakumon.gank.module.webview.WebViewActivity;
import me.bakumon.gank.utills.DateUtil;

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
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(WebViewActivity.GANK_TITLE, mData.get(position).desc);
        intent.putExtra(WebViewActivity.GANK_URL, mData.get(position).url);
        mContext.startActivity(intent);
    }
}
