package com.ff.material;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ff.material.behavior.BehaviorFragment;
import com.ff.material.cardview.CardViewFragment;
import com.ff.material.core.MaterialDesignActivity;
import com.ff.material.immersive.ImmersiveActivity;
import com.ff.material.recyclerview.RecyclerViewFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListItemClickListener {

    private FrameLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRoot = new FrameLayout(this);
        mRoot.setId(View.generateViewId());
        mRoot.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mRoot);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(mRoot.getId(), new MainFragment(), MainFragment.class.getName())
                    .commit();
        }
    }

    @Override
    public void onListItemClick(int position) {
        Fragment fragment;
        switch (position) {
            case 0:// MaterialDesign
                startActivity(new Intent(this, MaterialDesignActivity.class));
                return;
            case 1:// 顶部悬浮条的RecyclerView
                fragment = new RecyclerViewFragment();
                break;
            case 2:// 自定义Behavior
                fragment = new BehaviorFragment();
                break;
            case 3:// 沉浸式
                startActivity(new Intent(this, ImmersiveActivity.class));
                return;
            case 4:// CardView
                fragment = new CardViewFragment();
                break;
            default:
                return;

        }
        getSupportFragmentManager().beginTransaction()
                .replace(mRoot.getId(), fragment, fragment.getClass().getName())
                .addToBackStack(null)
                .commit();
    }
}
