package com.group12.cookiesrising.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group12.cookiesrising.util.Assets;

/**
 * Created by nattapat on 5/6/2016 AD.
 */
public class BG implements IGameObjectDrawable {

    @Override
    public void draw(SpriteBatch batch) {batch.draw(Assets.bg,0,0);}
}
