package com.obstacleavoid.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.util.debug.DebugCameraController;

public class DebugCameraSystem extends EntitySystem {

    //==constants==
//    private static final Logger log=new Logger(DebugCameraSystem.class.getName(), Logger.DEBUG);
    private static final DebugCameraController DEBUG_CAMERA_CONTROLLER=new DebugCameraController();

    //==ATTRIBUTES==
    private final OrthographicCamera camera;

    //==constructor ==


    public DebugCameraSystem(OrthographicCamera camera,float startX,float startY) {
        this.camera = camera;
        DEBUG_CAMERA_CONTROLLER.setStartPosition(startX,startY);
    }
    //==update==
    @Override
    public void update(float deltaTime) {

        DEBUG_CAMERA_CONTROLLER.handleDebugInput(deltaTime);
        DEBUG_CAMERA_CONTROLLER.applyTo(camera);
    }




}
