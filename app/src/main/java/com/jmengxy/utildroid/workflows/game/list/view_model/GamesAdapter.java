package com.jmengxy.utildroid.workflows.game.list.view_model;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utillib.adapters.DiffCallback;
import com.jmengxy.utillib.functors.Action1;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;

/**
 * Created by jiemeng on 21/02/2018.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {

    private final Context context;

    private final SchedulerProvider schedulerProvider;

    private List<GameEntity> gameEntityList;

    private Action1<GameEntity> itemClickListener;

    public void setOnItemClickListener(Action1<GameEntity> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public GamesAdapter(Context context) {
        this.context = context;
        schedulerProvider = UtilApplication.get(context).getAppComponent().getSchedulerProvider();
        gameEntityList = new ArrayList<>();
    }

    public void setData(List<GameEntity> gameEntityList) {
        Single.fromCallable(() -> DiffUtil.calculateDiff(new DiffCallback<>(this.gameEntityList, gameEntityList), true))
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe((diffResult, throwable) -> {
                    this.gameEntityList.clear();
                    this.gameEntityList = gameEntityList;
                    diffResult.dispatchUpdatesTo(GamesAdapter.this);
                });
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GameViewHolder(context, LayoutInflater.from(context), parent, itemClickListener);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        holder.bind(gameEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        return gameEntityList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        private Context context;

        private Action1<GameEntity> itemClickListener;

        @BindView(R.id.image)
        ImageView imageView;

        @BindView(R.id.name)
        TextView tvName;

        @BindView(R.id.price)
        TextView tvPrice;

        public GameViewHolder(Context context, LayoutInflater inflater, ViewGroup parent, Action1<GameEntity> itemClickListener) {
            super(inflater.inflate(R.layout.game_item_view, parent, false));
            ButterKnife.bind(this, itemView);
            this.context = context;
            this.itemClickListener = itemClickListener;
        }

        public void bind(GameEntity gameEntity) {
            Glide.with(context)
                    .load(gameEntity.getImageUrl())
                    .into(imageView);

            tvName.setText(gameEntity.getName());
            tvPrice.setText(String.format("$ %.2f", ((float) gameEntity.getPrice()) / 100));
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.apply(gameEntity);
                }
            });
        }
    }
}
