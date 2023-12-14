package com.obstacleavoid.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewPortUtils {

    private ViewPortUtils(){}

    private static final Logger log=new Logger(ViewPortUtils.class.getName(), Logger.DEBUG);
    private static final int DEFAULT_CELL_SIZE=1;



    public static void drawGrid(Viewport viewport, ShapeRenderer renderer){
        drawGrid(viewport,renderer,DEFAULT_CELL_SIZE);
    }

    public static void drawGrid(Viewport viewport,ShapeRenderer renderer,int cellSize){
        // validate parameters/arguments
        if(viewport==null){
            throw new IllegalArgumentException("viewport param is required");
        }

        if(renderer==null){
            throw new IllegalArgumentException("render param is required");
        }

        if(cellSize<DEFAULT_CELL_SIZE){
            cellSize=DEFAULT_CELL_SIZE;
        }

        //copy old color from renderer
        Color oldColor=new Color(renderer.getColor());
        int worldWidth=(int) viewport.getWorldWidth();
        int worldHeight=(int) viewport.getWorldHeight();
        int doubleWorldWidth=worldWidth*2;
        int doubleWorldHeight=worldHeight*2;
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        //draw vertical lines
        for (int i = -doubleWorldWidth; i <doubleWorldWidth ; i+=cellSize) {
            renderer.line(i,-doubleWorldHeight,i,doubleWorldHeight);
        }


        //draw horizontal lines
        for (int i = -doubleWorldHeight; i <doubleWorldHeight ; i++) {
            renderer.line(-doubleWorldWidth,i,doubleWorldWidth,i);
        }
        //draw x-y axis lines
        renderer.setColor(Color.RED);
        renderer.line(0,-doubleWorldHeight,0,doubleWorldHeight);
        renderer.line(-doubleWorldWidth,0,doubleWorldWidth,0);

        //draw world bounds
        renderer.setColor(Color.GREEN);

        renderer.line(0,worldHeight,worldWidth,worldHeight);
        renderer.line(worldWidth,0,worldWidth,worldHeight);

        renderer.end();
        renderer.setColor(oldColor);


    }

    public static void debugPixelPerUnit(Viewport viewport){
        if(viewport==null){
            throw new IllegalArgumentException("viewport param is required");

        }

        float screenWidth=viewport.getScreenWidth();
        float screenHeight=viewport.getScreenHeight();

        float worldWidth=viewport.getWorldWidth();
        float woldHeight=viewport.getWorldHeight();

        //ppu=>pixels per world unit
        float xPPU=screenWidth/worldWidth;
        float yPPU=screenHeight/woldHeight;

        log.debug("x PPU="+xPPU+" yPPU="+yPPU);


    }
}
