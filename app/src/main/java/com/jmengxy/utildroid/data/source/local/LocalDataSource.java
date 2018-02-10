package com.jmengxy.utildroid.data.source.local;

import com.jmengxy.utildroid.models.Monster;

/**
 * Created by jiemeng on 25/01/2018.
 */

public class LocalDataSource {

    private Monster monster = new Monster();

    public Monster getMonster() {
        //Pretend to get data from db, actually you may retrieve data with retrofit or okhttp here
        return monster;
    }

    public void updateUser(Monster monster) {
        this.monster = monster;
    }
}
