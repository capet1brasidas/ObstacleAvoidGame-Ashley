package com.obstacleavoid.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.obstacleavoid.common.Mappers;
import com.obstacleavoid.component.BoundsComponent;
import com.obstacleavoid.component.DimensionComponent;
import com.obstacleavoid.component.PositionComponent;

public class BoundsSystem extends IteratingSystem {

    private static final Family FAMILY=Family.all(
            PositionComponent.class,
            BoundsComponent.class,
            DimensionComponent.class
    ).get();

    public BoundsSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bounds= Mappers.BOUNDS.get(entity);
        PositionComponent position=Mappers.POSITION.get(entity);
        DimensionComponent dimension=Mappers.DIMENSION.get(entity);

        bounds.bounds.x=position.x+dimension.width/2f;
        bounds.bounds.y=position.y+dimension.height/2f;


    }
}
