package com.obstacleavoid.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT = new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT,BitmapFont.class);


    public static final AssetDescriptor<TextureAtlas> GAMEPLAY = new AssetDescriptor<TextureAtlas>(AssetPaths.GAMEPLAY_ATLAS,TextureAtlas.class);




    public static final AssetDescriptor<Skin> UI_SKIN =new AssetDescriptor<Skin>(AssetPaths.UI_SKIN,Skin.class);

    public static final AssetDescriptor<Sound> HIT_TRACK = new AssetDescriptor<Sound>(AssetPaths.HIT,Sound.class);
    public static final AssetDescriptor<Music> GAMEPLAY_TRACK = new AssetDescriptor<Music>(AssetPaths.GAMEPLAY_MUSIC,Music.class);
    private AssetDescriptors(){

    }
}
