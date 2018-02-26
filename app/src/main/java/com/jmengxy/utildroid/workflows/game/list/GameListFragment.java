package com.jmengxy.utildroid.workflows.game.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utildroid.workflows.game.detail.GameDetailActivity;
import com.jmengxy.utildroid.workflows.game.list.view_model.GamesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiemeng on 21/02/2018.
 */

public class GameListFragment extends Fragment implements GameListContract.View {

    private Unbinder unbinder;

    @Inject
    GameListContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private GamesAdapter gamesAdapter;

    public static GameListFragment newInstance() {
        GameListFragment fragment = new GameListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerGameListComponent
                .builder()
                .appComponent(UtilApplication.get(getContext()).getAppComponent())
                .gameListModule(new GameListModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_games, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        init();
        presenter.attach();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        presenter.detach();
        super.onDestroyView();
        unbinder.unbind();
    }

    private void init() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.green));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.refresh();
        });

        gamesAdapter = new GamesAdapter(getContext());
        gamesAdapter.setOnItemClickListener(gameEntity -> {
            startActivity(GameDetailActivity.getIntent(getContext(), gameEntity));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(gamesAdapter);
    }

    @Override
    public void showProgress(boolean showOrHide) {
        swipeRefreshLayout.setRefreshing(showOrHide);
    }

    @Override
    public void showError(int code, String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGames(List<GameEntity> gameEntities) {
        gamesAdapter.setData(gameEntities);
    }
}
