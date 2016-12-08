package me.bakumon.gank.utills;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by bakumon on 2016/12/8 15:51.
 *
 * @author bakumon
 * @version 1.0.0
 * @class GlideUtil
 * @describe Glide 图片加载工具类
 */
public class GlideUtil {
    /**
     * 判断是否是 gif
     *
     * @param context   上下文
     * @param imgUrl    图片 URL
     * @param imageView ImageView
     */
    public static void showImgIfGIf(final Context context, final String imgUrl, final ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .asGif()
                .listener(new RequestListener<String, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                        Glide.with(context).load(imgUrl).into(imageView);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }
}
