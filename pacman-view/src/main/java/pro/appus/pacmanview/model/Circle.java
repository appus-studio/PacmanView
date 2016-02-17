package pro.appus.pacmanview.model;

/**
 * Created by yuriy.ostapenko on 04.02.16.
 */
public class Circle {

    private float startCenterX = -1;
    private float startCenterY = -1;

    private float centerX = startCenterX;
    private float centerY = startCenterY;
    private float radius = -1;

    private float minCenterX = -1;

    private float step = -1;

    private boolean toDraw = false;

    public Circle(boolean toDraw) {
        this.toDraw = toDraw;
    }

    public Circle(float startCenterX, float startCenterY, float radius, float minCenterX, float step) {
        this.startCenterX = startCenterX;
        this.startCenterY = startCenterY;
        this.centerX = startCenterX;
        this.centerY = startCenterY;
        this.radius = radius;
        this.minCenterX = minCenterX;
        this.step = step;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getMinCenterX() {
        return minCenterX;
    }

    public void setMinCenterX(float minCenterX) {
        this.minCenterX = minCenterX;
    }

    public float getStartCenterX() {
        return startCenterX;
    }

    public void setStartCenterX(float startCenterX) {
        this.startCenterX = startCenterX;
    }

    public float getStartCenterY() {
        return startCenterY;
    }

    public void setStartCenterY(float startCenterY) {
        this.startCenterY = startCenterY;
    }

    public boolean isToDraw() {
        return toDraw;
    }

    public void setToDraw(boolean toDraw) {
        this.toDraw = toDraw;
    }
}