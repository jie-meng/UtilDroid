package com.jmengxy.utildroid.data.source.local;

import com.jmengxy.utildroid.models.MonsterEntity;

/**
 * Created by jiemeng on 25/01/2018.
 */

public class LocalDataSource {

    private MonsterEntity monsterEntity = new MonsterEntity();

    public MonsterEntity getMonsterEntity() {
        //Pretend to get data from db, actually you may retrieve data with retrofit or okhttp here
        return monsterEntity;
    }

    public void updateUser(MonsterEntity monsterEntity) {
        this.monsterEntity = monsterEntity;
    }
}
