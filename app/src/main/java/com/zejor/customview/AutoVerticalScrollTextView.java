package com.zejor.customview;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;


import com.zejor.R;

import java.util.List;


/**
 * 轮播的TextView
 */
public class AutoVerticalScrollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private Context mContext;
    private List<String> data;
    private int number;
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;
    public boolean isRunning = true;

    public AutoVerticalScrollTextView(Context context) {
        this(context, null);
    }

    public AutoVerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

    }

    /**
     *
     */
    public List<String> getData(){
        return this.data;
    }

    /**
     * 设置需要展示的数据
     */
    public void setData(List<String> list){
        if(list !=null && list.size() > 0 ){
            this.data = list;
        }

        if(data != null && data.size() >0 ){
            showData(data);
        }

    }

    /**
     * 展示数据
     */
    private void showData(final List<String> data) {
        this.setText(data.get(0));
        new Thread(){
            @Override
            public void run() {
                while(isRunning){
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(199);
                }
            }
        }.start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 199){
                next();
                number++;
                AutoVerticalScrollTextView.this.setText(data.get(number%data.size()));
            }
        }
    };


    private void init() {

        setFactory(this);

        mInUp = createAnim(true, true);
        mOutUp = createAnim(false, true);

        setInAnimation(mInUp);//当View显示时动画资源ID
        setOutAnimation(mOutUp);//当View隐藏是动画资源ID。

    }

    private Rotate3dAnimation createAnim( boolean turnIn, boolean turnUp){

        Rotate3dAnimation rotation = new Rotate3dAnimation(turnIn, turnUp);
        rotation.setDuration(1200);//执行动画的时间
        rotation.setFillAfter(false);//是否保持动画完毕之后的状态
        rotation.setInterpolator(new AccelerateInterpolator());//设置加速模式

        return rotation;
    }


    public View makeView() {

        TextView textView = new TextView(mContext);
        textView.setTextSize(12);
        textView.setSingleLine(true);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(getResources().getColor(R.color.white));
        return textView;

    }

    public void next(){
        //显示动画
        if(getInAnimation() != mInUp){
            setInAnimation(mInUp);
        }
        //隐藏动画
        if(getOutAnimation() != mOutUp){
            setOutAnimation(mOutUp);
        }
    }

    /**
     * 动画效果
     */
    class Rotate3dAnimation extends Animation {
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public Rotate3dAnimation(boolean turnIn, boolean turnUp) {
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight() ;
            mCenterX = getWidth() ;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            final float centerX = mCenterX ;
            final float centerY = mCenterY ;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1: -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection *mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection *mCenterY * (interpolatedTime), 0.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

}
