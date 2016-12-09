package me.bakumon.gank.module.iOS;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.bakumon.gank.R;
import me.bakumon.gank.base.adapter.CommonAdapter4RecyclerView;
import me.bakumon.gank.base.adapter.CommonHolder4RecyclerView;
import me.bakumon.gank.base.adapter.ListenerWithPosition;
import me.bakumon.gank.entity.IOSResult;
import me.bakumon.gank.utills.DateUtil;
import me.bakumon.gank.utills.GlideUtil;
import me.bakumon.gank.utills.ToastUtil;

/**
 * IOSListAdapter
 * Created by bakumon on 2016/10/13.
 */

public class IOSListAdapter extends CommonAdapter4RecyclerView<IOSResult.ResultsBean> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public IOSListAdapter(Context context) {
        super(context, null, R.layout.item);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, IOSResult.ResultsBean iOSResult) {
        if (iOSResult != null) {
            ImageView imageView = holder.getView(R.id.iv_item_img);
            if (iOSResult.images != null && iOSResult.images.size() > 0) {
                GlideUtil.showImgIfGIf(mContext, iOSResult.images.get(0) + "?imageView2/0/w/400", imageView);
            } else {
                Glide.with(mContext).load(R.mipmap.image_default).into(imageView);
            }
            holder.setTextViewText(R.id.tv_item_title, iOSResult.desc == null ? "unknown" : iOSResult.desc);
            holder.setTextViewText(R.id.tv_item_publisher, iOSResult.who == null ? "unknown" : iOSResult.who);
            holder.setTextViewText(R.id.tv_item_time, DateUtil.dateFormat(iOSResult.createdAt));
            holder.setOnClickListener(this, R.id.ll_item);
        }
    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {
        ToastUtil.showToastDefault(mContext, mData.get(position).url);
    }
}
