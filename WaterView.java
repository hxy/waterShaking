package hy.viewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huangyue on 2017/4/7.
 */

public class WaterView extends View {
    private static final int TO_LEFT = 1001;
    private static final int TO_RIGHT = 1002;
    private static final int TO_IN = 1003;
    private static final int TO_OUT = 1004;
    /*水晃动的方向(左右)*/
    private int mDdirection;
    /*水注入还是排出的方向(上下)*/
    private int mInOut;
    private Path mPath;
    private Paint mPaint;
    /*水的高度*/
    private float mWaterHeight;
    /*控制点X*/
    private float mCtlX;
    /*控制点Y*/
    private float mCtlY;
    /*view的宽度*/
    private int mViewWidth;
    /*view的高度*/
    private int mViewHeight;
    /*模拟水面的贝塞尔曲线的起始点x*/
    private float mStartX;
    /*模拟水面的贝塞尔曲线的终点x*/
    private float mEndX;

    public WaterView(Context context) {
        this(context,null);
    }

    public WaterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewHeight = h;
        mViewWidth = w;

        /*初始水平面高度为400*/
        mWaterHeight = 400;
        /*让起始点起始位置超出view，使动画更逼真*/
        mStartX = -mViewWidth/5;
        /*让终止点位置超出view，使动画更逼真*/
        mEndX = mViewWidth+mViewWidth/5;
    }

    private void init(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPath.moveTo(mStartX,mWaterHeight);
        mCtlY = mWaterHeight - 300;
        mPath.quadTo(mCtlX,mCtlY,mEndX,mWaterHeight);
        mPath.lineTo(mEndX,getHeight());
        mPath.lineTo(mStartX,getHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);

        if(mCtlX >= mEndX){
           mDdirection = TO_LEFT;
        }else if(mCtlX<=mStartX){
            mDdirection = TO_RIGHT;
        }
        if(mDdirection == TO_LEFT){
            mCtlX -=15;
        }else {
            mCtlX +=15;
        }

        if(mWaterHeight>=mViewHeight){
            mInOut = TO_IN;
        }else if(mWaterHeight<=300){
            mInOut = TO_OUT;
        }
        if(mInOut == TO_IN){
            mWaterHeight -= 2;
        }else {
            mWaterHeight += 2;
        }
        mPath.reset();
        invalidate();
    }
}
