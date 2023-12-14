package com.obstacleavoid.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.common.GameManager;
import com.obstacleavoid.config.DifficultyLevel;

public class OptionsScreen extends MenuScreenBase {

    private static final Logger log=new Logger(OptionsScreen.class.getName(), Logger.DEBUG);

    private Image checkMark;

    public OptionsScreen(ObstacleAvoidGame game) {
        super(game);
    }

    private ButtonGroup<CheckBox> checkBoxGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    @Override
    protected Actor createUi(){
        Table table=new Table();
        table.defaults().pad(15);

        TextureAtlas gamePlayAtlas=assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiskin=assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion=gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        //label
        Label label=new Label("DIFFICULTY",uiskin);
        easy=checkBox(DifficultyLevel.EASY.name(),uiskin);

//        easy.setDebug(true);


        medium=checkBox(DifficultyLevel.MEDIUM.name(), uiskin);
        hard=checkBox(DifficultyLevel.HARD.name(), uiskin);

        checkBoxGroup=new ButtonGroup<>(easy,medium,hard);

        DifficultyLevel difficultyLevel=GameManager.INSTANCE.getDifficultyLevel();
        checkBoxGroup.setChecked(difficultyLevel.name());

        TextButton backButton=new TextButton("BACK",uiskin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        ChangeListener listener=new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        };

        easy.addListener(listener);
        medium.addListener(listener);
        hard.addListener(listener);

        //setup table
        Table contentTable=new Table(uiskin);
        contentTable.defaults().pad(10);
        contentTable.setBackground(RegionNames.PANEL);

        contentTable.add(label).row();
        contentTable.add(easy).row();
        contentTable.add(medium).row();
        contentTable.add(hard).row();
        contentTable.add(backButton).row();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;

    }


    private void back(){
        log.debug("back");
        game.setScreen(new MenuScreen(game));

    }

    private void difficultyChanged(){
        log.debug("difficultyChanged()");
        CheckBox checked=checkBoxGroup.getChecked();
        if (checked==easy){
            log.debug("easy");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
        } else if (checked==medium) {
            log.debug("medium");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
        }else if (checked==hard){
            log.debug("hard");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
        }

    }

    private static CheckBox checkBox(String text,Skin skin){
        CheckBox checkBox=new CheckBox(text,skin);
        checkBox.left().pad(8);
        checkBox.getLabelCell().pad(8);

        return checkBox;
    }


}
