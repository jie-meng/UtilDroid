package com.jmengxy.utildroid.workflows.game.detail.view_model;

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
import com.jmengxy.utildroid.models.GameCommentEntity;
import com.jmengxy.utillib.adapters.DiffCallback;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by jiemeng on 26/02/2018.
 */

public class GameCommentsAdapter extends RecyclerView.Adapter<GameCommentsAdapter.GameCommentViewHolder> {

    private final Context context;

    private final SchedulerProvider schedulerProvider;

    private List<GameCommentEntity> gameCommentEntityList;

    public GameCommentsAdapter(Context context) {
        this.context = context;
        schedulerProvider = UtilApplication.get(context).getAppComponent().getSchedulerProvider();
        gameCommentEntityList = new ArrayList<>();
    }

    public void setData(List<GameCommentEntity> gameCommentEntityList) {
        Single.fromCallable(() -> DiffUtil.calculateDiff(new DiffCallback<>(this.gameCommentEntityList, gameCommentEntityList), true))
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe((diffResult, throwable) -> {
                    this.gameCommentEntityList.clear();
                    this.gameCommentEntityList = gameCommentEntityList;
                    diffResult.dispatchUpdatesTo(GameCommentsAdapter.this);
                });
    }

    @Override
    public GameCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GameCommentsAdapter.GameCommentViewHolder(context, LayoutInflater.from(context), parent);
    }

    @Override
    public void onBindViewHolder(GameCommentViewHolder holder, int position) {
        holder.bind(gameCommentEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        return gameCommentEntityList.size();
    }

    public static class GameCommentViewHolder extends RecyclerView.ViewHolder {
        private Context context;

        @BindView(R.id.icon)
        ImageView imageView;

        @BindView(R.id.name)
        TextView tvName;

        @BindView(R.id.comment)
        TextView tvComment;

        public GameCommentViewHolder(Context context, LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.game_comment_item_view, parent, false));
            ButterKnife.bind(this, itemView);
            this.context = context;
        }

        public void bind(GameCommentEntity gameCommentEntity) {
            Glide.with(context)
                    .load(gameCommentEntity.getAvatar())
                    .apply(bitmapTransform(new RoundedCornersTransformation(256, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(imageView);

            tvName.setText(gameCommentEntity.getName());
            tvComment.setText(gameCommentEntity.getComment());
        }
    }
}
