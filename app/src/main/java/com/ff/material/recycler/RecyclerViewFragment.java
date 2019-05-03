package com.ff.material.recycler;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ff.material.R;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * description: 顶部悬浮条的RecyclerView
 * author: FF
 * time: 2019-05-03 13:04
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private RelativeLayout mSuspensionBar;// 顶部悬浮条
    private TextView mSuspensionTv;// 顶部悬浮条的TextView
    private ImageView mSuspensionIv;// 顶部悬浮条的ImageView

    private OnScrollListener mOnScrollListener;// RecyclerView滑动监听

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new FeedAdapter());
        mRecyclerView.setHasFixedSize(true);

        mSuspensionBar = view.findViewById(R.id.suspension_bar);
        mSuspensionTv = view.findViewById(R.id.tv_nickname);
        mSuspensionIv = view.findViewById(R.id.iv_avatar);

        updateSuspensionBar(0);// 更新初始UI

        initEvent();

        return view;
    }

    private void initEvent() {
        mOnScrollListener = new OnScrollListener(this);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onDestroyView() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        super.onDestroyView();
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

    private static class OnScrollListener extends RecyclerView.OnScrollListener {

        private WeakReference<RecyclerViewFragment> mReference;

        private int mSuspensionHeight;// 顶部悬浮条的高度
        private int mCurrentPosition;// 当前RecyclerView第一个展示的position

        private OnScrollListener(RecyclerViewFragment fragment) {
            mReference = new WeakReference<>(fragment);
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

            RecyclerViewFragment fragment = mReference.get();

            // 对悬浮条的位置进行调整
            // 找到下一个itemView
            View view = fragment.mLayoutManager.findViewByPosition(mCurrentPosition + 1);
            if (view != null) {
                if (view.getTop() < mSuspensionHeight) {
                    // 需要对顶部悬浮条进行向上移动
                    fragment.mSuspensionBar.setY(view.getTop() - mSuspensionHeight);
                } else {
                    // 保持在原来的位置
                    fragment.mSuspensionBar.setY(0);
                }
            }

            if (mCurrentPosition != fragment.mLayoutManager.findFirstVisibleItemPosition()) {
                mCurrentPosition = fragment.mLayoutManager.findFirstVisibleItemPosition();
                fragment.updateSuspensionBar(mCurrentPosition);// 更新内容
            }
        }
    }
}
