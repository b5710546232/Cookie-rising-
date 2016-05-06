package com.group12.cookiesrising.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group12.cookiesrising.composite.CompositeGameObjectDrawable;

/**
 * Created by nattapat on 5/6/2016 AD.
 * WorldRenderer
 *
 * render all the sprite in game world.
 */
public class GameWorldRenderer  implements Disposable{

    private CompositeGameObjectDrawable gameObjectContainer;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;


    public GameWorldRenderer(CompositeGameObjectDrawable gameObjectContainer) {
        this.gameObjectContainer = gameObjectContainer;

    }

    /**
     * draw all texture in world.
     * */
    public void render(){

        //Draw a black bg.prevents flickering.
        Gdx.gl.glClearColor(1, 0, 0, 1);
        //// Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //Apply viewport
        viewport.apply();


        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();

        batch.begin();

        gameObjectContainer.draw(batch);

        batch.end();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
    /**
     * initial all
     * */
    public void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(640
                , 360,camera);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    /**
     * resize by update the viewport.
     * */
    public void resize(int width, int height) {
        // update the viewport
        viewport.update(width,height,true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
