package com.obstacleavoid.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public  class ViewportUtils {

    private static final Logger log = new Logger(ViewportUtils.class.getName(),Logger.DEBUG);

    private static final int DEFAULT_CELL_SIZE=1;
    public static void drawGrid(Viewport viewport, ShapeRenderer renderer){
        drawGrid(viewport,renderer,DEFAULT_CELL_SIZE);
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer,int cellSize){

    // Argument validation for non nullable objects ;
        if(viewport == null || renderer == null  )
            throw new IllegalArgumentException("Required parameter is null");

     // control of cellsize
        if(cellSize <DEFAULT_CELL_SIZE) {
            cellSize = DEFAULT_CELL_SIZE;
        }
    // retrieving old color of rendrer object ;

        Color oldColor = new Color(renderer.getColor());
        int worldWidth = (int) viewport.getWorldWidth();

        int worldHeight = (int) viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth *2;
        int doubleWorldHeight = worldHeight *2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);


        //<editor-fold desc="drawing grid lines ">
        // drawing vertical lines
        for (int i = -doubleWorldWidth; i <doubleWorldWidth ; i+=cellSize) {
            renderer.line(i,-doubleWorldHeight,i,doubleWorldHeight);
        }


        // drawing horizental lines
        for (int i = -doubleWorldHeight; i <doubleWorldHeight ; i+=cellSize) {
            renderer.line(-doubleWorldWidth,i,doubleWorldWidth,i);
        }
        // drawing X and Y axes ;

        renderer.setColor(Color.RED);

        renderer.line(0,-doubleWorldHeight,0,doubleWorldHeight); // Y axes
        renderer.line(-doubleWorldWidth,0,doubleWorldWidth,0); // X axes

        // drawing world bounds
        renderer.setColor(Color.GREEN);
        renderer.line(0,worldHeight,worldWidth,worldHeight);
        renderer.line(worldWidth,0,worldWidth,worldHeight);
        //</editor-fold>

        renderer.end();

        renderer.setColor(oldColor);

    }

    public static void debugPixel(Viewport viewport){
        if(viewport == null)
            throw new IllegalArgumentException("required viewport");
        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float xPPU = screenWidth /worldWidth;

        float yPPU = screenHeight /worldHeight;

        log.debug("xPPU == " +xPPU + "  yPPU == " + yPPU);
    }

    private ViewportUtils(){

    }


}
