package com.ff.material.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ff.material.R;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * description: 沉浸式顶部悬浮条的RecyclerView
 * author: FF
 * time: 2019-05-15 10:40
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private RelativeLayout mSuspensionBar;// 顶部悬浮条
    private TextView mSuspensionTv;// 顶部悬浮条的TextView
    private ImageView mSuspensionIv;// 顶部悬浮条的ImageView

    private RecyclerViewActivity.OnScrollListener mOnScrollListener;// RecyclerView滑动监听

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        immersive();// FIXME: 2019-05-15 这里使用沉浸式，第一次不能滑动
        // 如果使用Toolbar，隐藏状态栏，Toolbar的位置会上移，所以需要增加该逻辑
        setHeightAndPadding(this, findViewById(R.id.toolbar));

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new FeedAdapter());
        mRecyclerView.setHasFixedSize(true);

        mSuspensionBar = findViewById(R.id.suspension_bar);
        mSuspensionTv = findViewById(R.id.tv_nickname);
        mSuspensionIv = findViewById(R.id.iv_avatar);

        updateSuspensionBar(0);// 更新初始UI

        initEvent();
    }

    private void initEvent() {
        mOnScrollListener = new RecyclerViewActivity.OnScrollListener(this);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void onDestroy() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        super.onDestroy();
    }

    /**
     * 更新顶部悬浮条的内容
     *
     * @param position 位置
     */
    private void updateSuspensionBar(int position) {
        Picasso.get()
                .load(getAvatarResId(position))
                .centerInside()
                .fit()
                .into(mSuspensionIv);
        mSuspensionTv.setText("NetEase " + position);
    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }

    private void immersive() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置状态栏颜色透明
            window.setStatusBarColor(Color.TRANSPARENT);

            int visibility = window.getDecorView().getSystemUiVisibility();
            // 布局内容全屏展示
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            // 隐藏虚拟导航栏
            visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            // 防止内容区域大小发生变化
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            window.getDecorView().setSystemUiVisibility(visibility);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 将Toolbar的高度和PaddingTop增大一个状态栏高度，进行适配
     */
    public void setHeightAndPadding(Context context, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height += getStatusBarHeight(context);
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop() + getStatusBarHeight(context),
                view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 获取状态栏高度
     */
    private int getStatusBarHeight(Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return context.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }

    private static class OnScrollListener extends RecyclerView.OnScrollListener {

        private WeakReference<RecyclerViewActivity> mReference;

        private int mSuspensionHeight;// 顶部悬浮条的高度
        private int mCurrentPosition;// 当前RecyclerView第一个展示的position

        private OnScrollListener(RecyclerViewActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (mSuspensionHeight == 0 && mReference != null && mReference.get() != null) {
                mSuspensionHeight = mReference.get().mSuspensionBar.getHeight();
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

            if (mReference == null || mReference.get() == null) {
                return;
            }

            RecyclerViewActivity activity = mReference.get();

            // 对悬浮条的位置进行调整
            // 找到下一个itemView
            View view = activity.mLayoutManager.findViewByPosition(mCurrentPosition + 1);
            if (view != null) {
                if (view.getTop() < mSuspensionHeight) {
                    // 需要对顶部悬浮条进行向上移动
                    activity.mSuspensionBar.setY(view.getTop() - mSuspensionHeight);
                } else {
                    // 保持在原来的位置
                    activity.mSuspensionBar.setY(0);
                }
            }

            if (mCurrentPosition != activity.mLayoutManager.findFirstVisibleItemPosition()) {
                mCurrentPosition = activity.mLayoutManager.findFirstVisibleItemPosition();
                activity.updateSuspensionBar(mCurrentPosition);// 更新内容
            }
        }
    }
}
