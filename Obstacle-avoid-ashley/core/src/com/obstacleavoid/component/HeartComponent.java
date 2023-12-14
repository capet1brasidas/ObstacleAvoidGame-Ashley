package com.obstacleavoid.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HeartComponent implements Component , Pool.Poolable {
    public boolean hit;


    @Override
    public void reset() {
        hit=false;
    }
}
