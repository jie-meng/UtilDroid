package com.jmengxy.utildroid.data.source.remote;


import com.jmengxy.utildroid.models.GameCommentEntity;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utildroid.models.LoginRequest;
import com.jmengxy.utildroid.models.UserEntity;
import com.jmengxy.utillib.architecture.eventbus.EventBus;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.jmengxy.utildroid.utils.eventbus.RemoteEvent.authTokenInvalidEvent;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Singleton
public class RemoteDataSource {

    private interface UserApi {
        @POST("user/login")
        Single<Response<UserEntity>> login(@Body LoginRequest loginRequest);

        @POST("user/logout")
        Single<Response<Object>> logout();
    }

    private interface GameApi {
        @GET("game/list")
        Single<Response<List<GameEntity>>> getGames();

        @GET("game/detail/{id}")
        Single<Response<GameEntity>> getGame(@Path("id") String gameId);

        @GET("game/comments/{id}")
        Single<Response<List<GameCommentEntity>>> getGameComments(@Path("id") String gameId);
    }

    private final EventBus eventBus;
    private final UserApi userApi;
    private final GameApi gameApi;

    public RemoteDataSource(Retrofit retrofit, EventBus eventBus) {
        this.eventBus = eventBus;
        this.userApi = retrofit.create(UserApi.class);
        this.gameApi = retrofit.create(GameApi.class);
    }

    public Single<UserEntity> login(LoginRequest loginRequest) {
        return userApi
                .login(loginRequest)
                .compose(unwrap(UserEntity.class));
    }

    public Single<Object> logout() {
        return userApi
                .logout()
                .compose(unwrap(Object.class));
    }

    public Single<List<GameEntity>> getGames() {
        Object t1 = checkAuthFailure(List.class);
        Object t2 = unwrap(List.class);
        return gameApi
                .getGames()
                .compose((SingleTransformer<? super Response<List<GameEntity>>, ? extends Response<List<GameEntity>>>) t1)
                .compose((SingleTransformer<? super Response<List<GameEntity>>, ? extends List<GameEntity>>) t2);
    }

    public Single<GameEntity> getGame(String gameId) {
        return gameApi
                .getGame(gameId)
                .compose(checkAuthFailure(GameEntity.class))
                .compose(unwrap(GameEntity.class));
    }

    public Single<List<GameCommentEntity>> getGameComments(String gameId) {
        Object t1 = checkAuthFailure(List.class);
        Object t2 = unwrap(List.class);
        return gameApi
                .getGameComments(gameId)
                .compose((SingleTransformer<? super Response<List<GameCommentEntity>>, ? extends Response<List<GameCommentEntity>>>) t1)
                .compose((SingleTransformer<? super Response<List<GameCommentEntity>>, ? extends List<GameCommentEntity>>) t2);
    }

    private <T> SingleTransformer<Response<T>, Response<T>> checkAuthFailure(Class<T> clazz) {
        return upstream -> upstream.doOnError((Consumer<Throwable>) throwable -> {
            if (throwable instanceof RemoteAuthException) {
                RemoteAuthException authException = (RemoteAuthException) throwable;
                eventBus.post(authTokenInvalidEvent(authException.getErrorMessage()));
            }
        });
    }

    private <T> SingleTransformer<Response<T>, T> unwrap(final Class<T> clazz) {
        return upstream -> upstream
                .map((Function<Response<T>, T>) response -> {
                    T data = response.getData();
                    if (data != null) {
                        return data;
                    } else {
                        return clazz.newInstance();
                    }
                });
    }
}
