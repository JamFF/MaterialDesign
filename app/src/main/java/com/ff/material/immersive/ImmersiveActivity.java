package com.ff.material.immersive;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ff.material.R;

public class ImmersiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersive);
        immersive();
        // 如果使用Toolbar，隐藏状态栏，Toolbar的位置会上移，所以需要增加该逻辑
        setHeightAndPadding(this, findViewById(R.id.toolbar));
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

}
