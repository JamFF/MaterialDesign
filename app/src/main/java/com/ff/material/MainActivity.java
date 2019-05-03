package com.ff.material;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ff.material.core.MaterialDesignActivity;

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
            case 1:// 自定义RecyclerView
//                fragment = new GooglePercentFragment();
                break;
            case 2:
//                fragment = new MyPercentFragment();
                break;
            default:
                return;

        }
//        getSupportFragmentManager().beginTransaction()
//                .replace(mRoot.getId(), fragment, fragment.getClass().getName())
//                .addToBackStack(null)
//                .commit();
    }
}
