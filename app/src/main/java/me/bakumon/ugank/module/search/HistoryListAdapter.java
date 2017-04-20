package me.bakumon.ugank.module.search;

import android.content.Context;
import android.view.View;

import me.bakumon.ugank.R;
import me.bakumon.ugank.base.adapter.CommonAdapter4RecyclerView;
import me.bakumon.ugank.base.adapter.CommonHolder4RecyclerView;
import me.bakumon.ugank.base.adapter.ListenerWithPosition;
import me.bakumon.ugank.entity.History;

/**
 * HistoryListAdapter
 * Created by bakumon on 2017/2/18.
 */

public class HistoryListAdapter extends CommonAdapter4RecyclerView<History> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public HistoryListAdapter(Context context) {
        super(context, null, R.layout.item_history);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, History history) {
        if (history != null) {
            holder.setTextViewText(R.id.tv_item_content_history, history.getContent() == null ? "unknown" : history.getContent());
            holder.setOnClickListener(this, R.id.tv_item_content_history);
        }
    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {
        if (mData.get(position) == null) {
            return;
        }
        if (mOnItemClickListener != null) {
            mOnItemClickListener.OnItemClick(mData.get(position));
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(History history);
    }

}
