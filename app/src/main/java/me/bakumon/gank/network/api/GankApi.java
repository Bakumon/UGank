package me.bakumon.gank.network.api;

import me.bakumon.gank.entity.CategoryResult;
import me.bakumon.gank.entity.IOSResult;
import me.bakumon.gank.entity.MeiziResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * gank.io 接口
 * Created by bakumon on 16-12-1.
 */

public interface GankApi {

    @GET("data/{category}/{number}/{page}")
    Observable<CategoryResult> getCategoryDate(@Path("category") String category, @Path("number") int number, @Path("page") int page);

    @GET("data/福利/{number}/{page}")
    Observable<MeiziResult> getBeauties(@Path("number") int number, @Path("page") int page);

    @GET("random/data/福利/{number}")
    Observable<MeiziResult> getRandomBeauties(@Path("number") int number);

}
