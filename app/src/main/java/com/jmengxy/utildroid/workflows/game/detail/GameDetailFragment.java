
package com.jmengxy.utildroid.workflows.game.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.models.GameCommentEntity;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utildroid.workflows.game.detail.view_model.GameCommentsAdapter;
import com.jmengxy.utillib.listeners.OnBackPressedListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiemeng on 26/02/2018.
 */

public class GameDetailFragment extends Fragment implements OnBackPressedListener, GameDetailContract.View {

    public static final String ARG_GAME_ENTITY = "arg_game_entity";

    private Unbinder unbinder;

    private GameCommentsAdapter gameCommentsAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.comment_list)
    RecyclerView recyclerView;

    @Inject
    GameDetailContract.Presenter presenter;

    public static GameDetailFragment newInstance(GameEntity gameEntity) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_GAME_ENTITY, gameEntity);
        GameDetailFragment fragment = new GameDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerGameDetailComponent
                .builder()
                .appComponent(UtilApplication.get(getContext()).getAppComponent())
                .gameDetailModule(new GameDetailModule(this, getArguments().getParcelable(ARG_GAME_ENTITY)))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_game_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        init();
        presenter.attach();
        return rootView;
    }

    private void init() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.green));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.refresh();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        gameCommentsAdapter = new GameCommentsAdapter(getContext());
        recyclerView.setAdapter(gameCommentsAdapter);
    }

    @Override
    public void onDestroyView() {
        presenter.detach();
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onBackPressed() {
        return false;
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
    public void showGame(GameEntity gameEntity) {
        toolbar.setTitle(gameEntity.getName());

        Glide.with(getContext())
                .load(gameEntity.getImageUrl())
                .into(imageView);
    }

    @Override
    public void showComments(List<GameCommentEntity> gameCommentEntities) {
        gameCommentsAdapter.setData(gameCommentEntities);
    }

    @Override
    public void showRetry() {
    }
}
