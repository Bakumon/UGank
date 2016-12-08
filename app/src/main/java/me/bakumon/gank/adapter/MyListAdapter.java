package me.bakumon.gank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.bakumon.gank.R;

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
        ImageView imageView = holder.getView(R.id.image);
        Glide.with(mContext).load("http://img.gank.io/0995f5f4-9b1e-485a-ade2-fd734afad66b").into(imageView);


    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {

    }
}
