package me.bakumon.ugank.module.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.ugank.R;
import me.bakumon.ugank.module.home.HomeActivity;

/**
 * LauncherActivity
 * Created by bakumon on 2016/12/8.
 */
public class LauncherActivity extends AppCompatActivity implements LauncherContract.View {

    @BindView(R.id.img_launcher_welcome)
    AppCompatImageView mImageView;

    private LauncherContract.Presenter mLauncherPresenter = new LauncherPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        mLauncherPresenter.subscribe();
    }

    @Override
    public void loadImg(String url) {
        try {
            Picasso.with(this)
                    .load(url)
                    .into(mImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    goHomeActivity();
                                }
                            }, 1200);
                        }

                        @Override
                        public void onError() {
                            goHomeActivity();
                        }
                    });
        } catch (Exception e) {
            goHomeActivity();
        }
    }

    @Override
    public void goHomeActivity() {
        Intent intent = new Intent(LauncherActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLauncherPresenter.unsubscribe();
    }
}
