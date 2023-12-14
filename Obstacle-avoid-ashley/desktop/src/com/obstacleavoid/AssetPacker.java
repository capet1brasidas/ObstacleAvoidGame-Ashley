package com.obstacleavoid;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE=true;
    private static final String RAW_ASSETS_PATH="desktop/assets-raw";
    private static final String ASSETS_PATH="assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings=new TexturePacker.Settings();
//        settings.debug=DRAW_DEBUG_OUTLINE;
//        settings.pot=false;
//        settings.maxWidth=404;
//        settings.maxHeight=804;

        TexturePacker.process(settings,
                RAW_ASSETS_PATH+"/gameplay",
                ASSETS_PATH+"/gameplay",
                "gameplay");
//        TexturePacker.process(settings,
//                RAW_ASSETS_PATH+"/ui",
//                ASSETS_PATH+"/ui",
//                "ui" );

//        TexturePacker.process(settings,
//                RAW_ASSETS_PATH+"/skin",
//                ASSETS_PATH+"/ui",
//                "uiskin");

    }


}
