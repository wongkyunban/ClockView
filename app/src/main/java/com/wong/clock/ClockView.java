package com.wong.clock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class ClockView extends View {

    /*画笔*/
    private Paint mPaintCircle = new Paint();
    private Paint mPaintDot = new Paint();
    private Paint mPaintScale = new Paint();
    private Paint mPaintHour = new Paint();
    private Paint mPaintMin = new Paint();
    private Paint mPaintSec = new Paint();
    private int width = 0;
    private int height = 0;
    private int circleColor = Color.WHITE;
    private int scaleColor = Color.GRAY;
    private int dotColor = Color.BLACK;
    private int hourPointColor = Color.BLACK;
    private int minPointColor = Color.BLACK;
    private int secPointColor = Color.RED;
    private float hourPointWidth = 12f;
    private float minPointWidth = 8f;
    private float secPointWidth = 2f;
    private float dotWidth = 0;

    public void setCircleColor(int circleColor){
        this.circleColor = circleColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
    }

    public void setHourPointColor(int hourPointColor) {
        this.hourPointColor = hourPointColor;
    }

    public void setMinPointColor(int minPointColor) {
        this.minPointColor = minPointColor;
    }

    public void setSecPointColor(int secPointColor) {
        this.secPointColor = secPointColor;
    }

    public void setHourPointWidth(float hourPointWidth) {
        this.hourPointWidth = hourPointWidth;
    }

    public void setMinPointWidth(float minPointWidth) {
        this.minPointWidth = minPointWidth;
    }

    public void setSecPointWidth(float secPointWidth) {
        this.secPointWidth = secPointWidth;
    }

    public ClockView(Context context) {
        super(context);
        init(context,null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    private void init(@NonNull Context context,AttributeSet attrs){


        /*初始化xml属性的值*/
        if(attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ClockView);
            circleColor = typedArray.getColor(R.styleable.ClockView_circle_color,circleColor);
            scaleColor = typedArray.getColor(R.styleable.ClockView_circle_scale_color,scaleColor);
            dotColor = typedArray.getColor(R.styleable.ClockView_dot_color,dotColor);
            hourPointColor = typedArray.getColor(R.styleable.ClockView_hour_point_color,hourPointColor);
            minPointColor = typedArray.getColor(R.styleable.ClockView_min_point_color,minPointColor);
            secPointColor = typedArray.getColor(R.styleable.ClockView_sec_point_color,secPointColor);
            hourPointWidth = typedArray.getDimension(R.styleable.ClockView_hour_point_width,hourPointWidth);
            minPointWidth = typedArray.getDimension(R.styleable.ClockView_min_point_width,minPointWidth);
            secPointWidth = typedArray.getDimension(R.styleable.ClockView_sec_point_width,secPointWidth);
            dotWidth = typedArray.getDimension(R.styleable.ClockView_dot_width,dotWidth);
            typedArray.recycle();
        }

        /*表盘*/
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(circleColor);
        /*刻度*/
        mPaintScale.setStyle(Paint.Style.FILL);
        mPaintScale.setAntiAlias(true);
        mPaintScale.setColor(scaleColor);
        /*圆心的圆*/
        mPaintDot.setStyle(Paint.Style.FILL);
        mPaintDot.setAntiAlias(true);
        mPaintDot.setColor(dotColor);
        /*hour*/
        mPaintHour.setAntiAlias(true);
        mPaintHour.setStrokeWidth(hourPointWidth);
        mPaintHour.setStyle(Paint.Style.FILL);
        mPaintHour.setColor(hourPointColor);
        /*min*/
        mPaintMin.setAntiAlias(true);
        mPaintMin.setStrokeWidth(minPointWidth);
        mPaintMin.setStyle(Paint.Style.FILL);
        mPaintMin.setColor(minPointColor);
        /*sec*/
        mPaintSec.setAntiAlias(true);
        mPaintSec.setStrokeWidth(secPointWidth);
        mPaintSec.setStyle(Paint.Style.FILL);
        mPaintSec.setColor(secPointColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

         int widthW = widthSize - getPaddingLeft()-getPaddingRight();
         int heightH = heightSize - getPaddingTop() - getPaddingBottom();


        switch (widthMode){
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                if(getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    width = widthW;
                }else if(getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT){
                    width  = (int)getResources().getDimension(R.dimen.clock_view_width);
                }else{
                    width = widthW;
                }
                break;
                case MeasureSpec.UNSPECIFIED:
                    width = widthW;
                    break;
        }
        switch (heightMode){
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                if(getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    height = heightH;
                }else if(getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT){
                    height = (int)getResources().getDimension(R.dimen.clock_view_height);
                }else{
                    height = heightH;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                height = heightH;
                break;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*设置画布的颜色*/
        canvas.drawColor(Color.WHITE);
        int radius = Math.min(width,height)/2;
        /*画刻盘*/
        canvas.drawCircle(width/2,height/2,radius,mPaintCircle);

        /*绘制12个点，代表１２小时*/
        float scaleRadius = radius / 24;
        float dotY = (float) height/2-radius+5*scaleRadius/4;
        for(int i = 0; i < 12; i++){
            canvas.drawCircle(width/2, dotY, scaleRadius, mPaintScale);
            canvas.rotate(30f,width/2,height/2);
        }
        /*绘制时针、分针、秒针*/
        Calendar calendar = Calendar.getInstance();
        float hour = calendar.get(Calendar.HOUR);
        float min = calendar.get(Calendar.MINUTE);
        float sec = calendar.get(Calendar.SECOND);
        /*时针转过的角度*/
        float angleHour = hour * (float) (360 / 12) + min/60*30;
        /*分针转过的角度*/
        float angleMin = min * 6;
        /*秒针转过的角度*/
        float angleSec = sec * 6;

        /*绘制时针*/
        float hourY = (float) height/2-radius+(float) radius / 2;
        canvas.save();
        canvas.rotate(angleHour,width/2,height/2);
        canvas.drawLine(width/2,height/2,width/2,hourY,mPaintHour);
        canvas.restore();

        /*绘制分针*/
        float minY = (float) height/2-radius+(float) radius / 3;
        canvas.save();
        canvas.rotate(angleMin,width/2,height/2);
        canvas.drawLine(width/2,height/2,width/2,minY,mPaintMin);
        canvas.restore();

        /*绘制分针*/
        float secY = (float) height/2-radius+(float) radius / 5;
        canvas.save();
        canvas.rotate(angleSec,width/2,height/2);
        canvas.drawLine(width/2,height/2,width/2,secY,mPaintSec);
        canvas.restore();

        /*在圆心画个圆盖住三条指针的连接处*/
        float dotRadius = Math.max(dotWidth,(float) 0.05*radius);
        canvas.drawCircle(width/2,height/2,dotRadius,mPaintDot);

        /*每隔１秒就刷新*/
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },1000);

    }
}
