package com.ff.material.core.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ff.material.R;
import com.ff.material.core.adapter.NormalAdapter;
import com.ff.material.core.bean.Movie;
import com.ff.material.core.net.HttpMethods;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MovieFragment extends Fragment {
    @BindView(R.id.rv_fr_list)
    RecyclerView rvFrList;
    @BindView(R.id.srl_fr_refresh)
    SwipeRefreshLayout srlFrRefresh;
    private Unbinder unbinder;
    private boolean firstShow = true;
    private NormalAdapter normalAdapter;


    private ArrayList<Movie.SubjectsBean> mMovieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initData();
    }

    private void initData() {
        srlFrRefresh.setRefreshing(true);
        rvFrList.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (firstShow) {
            firstShow = false;
            HttpMethods.getInstance().getTopMovie(new Observer<Movie>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Movie movie) {
                    Log.i("ToolTAG", "onNext: " + movie.getSubjects().size());


                    mMovieList.addAll(movie.getSubjects());
                    normalAdapter = new NormalAdapter(mMovieList, getActivity());
                    rvFrList.setAdapter(normalAdapter);
                }

                @Override
                public void onError(Throwable e) {
                    Log.i("ToolTAG", "onError: " + e.getMessage());
                    srlFrRefresh.setRefreshing(false);
                }

                @Override
                public void onComplete() {
                    srlFrRefresh.setRefreshing(false);
                    Log.i("ToolTAG", "onCompleted: ");
                }
            }, 0, 10);
        }
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
