package me.bakumon.gank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import me.bakumon.gank.R;
import me.bakumon.gank.utills.GlideUtil;

/**
 * 卖公司列表适配器
 * Created by mafei on 2016/10/13.
 */

public class MyListAdapter extends CommonAdapter4RecyclerView implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public MyListAdapter(Context context) {
        super(context, null, R.layout.item);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, Object sellCompanyVo) {
        final ImageView imageView = holder.getView(R.id.image);
        GlideUtil.showImgIfGIf(mContext, "http://img.gank.io/a0c78014-1cf6-4efc-8e87-0387288d254e?imageView2/0/w/400", imageView);

    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {

    }
}
