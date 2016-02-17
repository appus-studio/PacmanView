package pro.appus.pacmanview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pro.appus.pacmanview.model.Circle;

/**
 * Created by yuriy.ostapenko on 04.02.16.
 */
public class PacmanView extends View {

    //Pacman
    private Path pacmanPath;
    private Paint pacmanPaint;
    private RectF oval;

    private static final int DEFAULT_PACMAN_COLOR = Color.BLACK;
    private int pacmanColor = DEFAULT_PACMAN_COLOR;
    private float pacmanWidth = 400f;
    private float pacmanHeight = 240f;
    private float pacmanRadius = 0;

    private float pacmanCenterX = 240;
    private int pacmanCenterY = 0;

    private int sweepAngle = 270;
    private float sweepAngleStep = 0;

    private float startAngle = 45;
    private float startAngleStep = 0;

    private static final int MAX_ANGLE = 360;
    private static final int MIN_ANGLE = 260;
    private boolean angleIncreasing = true;


    //Circles
    private Paint circlePaint;

    private List<Circle> circles = new ArrayList<>();

    private static final int DEFAULT_CIRCLE_COLOR = Color.GREEN;
    private int circleColor = DEFAULT_CIRCLE_COLOR;
    private boolean isCirclesWithRandomColor = false;

    private static final int DEFAULT_CIRCLES_COUNT = 3;
    private int circlesCount = DEFAULT_CIRCLES_COUNT;

    private static final float DEFAULT_CIRCLES_SPEED = 2;
    private float circlesStep = DEFAULT_CIRCLES_SPEED;

    private float circlesStartCenterX = 0;
    private float circlesStartCenterY = 0;
    private float circlesMinCenterX = 0;
    private float circlesRadius = 0;
    private float circleCheckPointX = 0; //on which X new circle will appear

    public PacmanView(Context context) {
        super(context);
        init();
    }

    public PacmanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttributes(context, attrs);
        init();
    }

    public PacmanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(context, attrs);
        init();
    }

    public void init(){
        initPacman();
        initCircles();
        createCircleList();
    }

    public void initPacman(){
        pacmanPath = new Path();

        pacmanPaint = new Paint();
        pacmanPaint.setColor(pacmanColor);
        pacmanPaint.setStrokeWidth(5);
        pacmanPaint.setStyle(Paint.Style.FILL);
        pacmanPaint.setAntiAlias(true);

        oval = new RectF();
    }

    public void initCircles(){
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
    }

    public void setCustomAttributes(Context context, AttributeSet attrs){
        TypedArray attributeValuesArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PacmanView, 0, 0);
        try{
            pacmanColor = attributeValuesArray.getColor(R.styleable.PacmanView_pacman_color, DEFAULT_PACMAN_COLOR);
            sweepAngleStep = attributeValuesArray.getFloat(R.styleable.PacmanView_pacman_speed, 0);
            startAngleStep = sweepAngleStep / 2f;
            circleColor = attributeValuesArray.getColor(R.styleable.PacmanView_circles_color, DEFAULT_CIRCLE_COLOR);
            circlesCount = attributeValuesArray.getInteger(R.styleable.PacmanView_circles_count, DEFAULT_CIRCLES_COUNT);
            circlesStep = attributeValuesArray.getFloat(R.styleable.PacmanView_circles_speed, DEFAULT_CIRCLES_SPEED);
            isCirclesWithRandomColor = attributeValuesArray.getBoolean(R.styleable.PacmanView_circles_random_color, false);
        } finally {
            attributeValuesArray.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldheight) {
        super.onSizeChanged(width, height, oldWidth, oldheight);

        //Get center Y for pacman and circles
        pacmanCenterY = height/2;
        pacmanCenterX = height/2;
        pacmanRadius = height/2;

        circlesRadius = pacmanRadius/4;
        circlesStartCenterY = pacmanCenterY;
        circlesStartCenterX = width - circlesRadius;
        circlesMinCenterX = pacmanCenterX - circlesRadius;

        setPacmanDimensions();
        setCirclesDimensions();
    }

    private void setPacmanDimensions(){
        pacmanPath.addCircle(pacmanWidth, pacmanHeight, pacmanRadius, Path.Direction.CW);
        oval.set(pacmanCenterX - pacmanRadius, pacmanCenterY - pacmanRadius, pacmanCenterX + pacmanRadius, pacmanCenterY + pacmanRadius);
    }

    private void createCircleList(){
        for(int i = 0; i < circlesCount; i++){
            if(i == 0){
                circles.add(new Circle(true));
            } else {
                circles.add(new Circle(false));
            }
        }
    }

    private void setCirclesDimensions(){
        circleCheckPointX = ((circlesStartCenterX-circlesMinCenterX) * (circlesCount-1) / circlesCount) + circlesMinCenterX;

        for(Circle circle : circles){
            circle.setStartCenterX(circlesStartCenterX);
            circle.setStartCenterY(circlesStartCenterY);
            circle.setCenterX(circlesStartCenterX);
            circle.setCenterY(circlesStartCenterY);
            circle.setRadius(circlesRadius);
            circle.setMinCenterX(circlesMinCenterX);
            circle.setStep(circlesStep);
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        //CIRCLES
        for(int i = 0; i<circlesCount; i++){
            Circle circle = circles.get(i);

            //if we cross checkpoint we make next circle toDraw=true
            if(circle.getCenterX() <= circleCheckPointX){
                if(i < (circlesCount-1)){ // if we have next element in list
                    circles.get(i+1).setToDraw(true);
                } else {
                    circles.get(0).setToDraw(true);
                }
            }

            if(circle.isToDraw()){
                if(circle.getCenterX() > circle.getMinCenterX()){
                    circle.setCenterX(circle.getCenterX() - circle.getStep());
                    canvas.drawCircle(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circlePaint);
                }
                //if we reached the end point we go to start and disable circle
                else {
                    if(isCirclesWithRandomColor){
                        setCircleToRandomColor();
                    }
                    circle.setCenterX(circle.getStartCenterX());
                    circle.setToDraw(false);
                }
            }
        }

        //PACMAN
        if(sweepAngle > MIN_ANGLE && sweepAngle < MAX_ANGLE){
            if(angleIncreasing){
                sweepAngle += sweepAngleStep;
                startAngle -= startAngleStep;
            } else {
                sweepAngle -= sweepAngleStep;
                startAngle += startAngleStep;
            }
        } else {
            if(sweepAngle >= MAX_ANGLE){
                sweepAngle -= sweepAngleStep;
                startAngle += startAngleStep;
                angleIncreasing = false;
            } else {
                if (sweepAngle <= MIN_ANGLE) {
                    sweepAngle += sweepAngleStep;
                    startAngle -= startAngleStep;
                    angleIncreasing = true;
                }
            }
        }
        canvas.drawArc(oval, startAngle, sweepAngle, true, pacmanPaint);
        invalidate();
    }

    private void setCircleToRandomColor(){
        Random rnd = new Random();
        circleColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        circlePaint.setColor(circleColor);
    }
}