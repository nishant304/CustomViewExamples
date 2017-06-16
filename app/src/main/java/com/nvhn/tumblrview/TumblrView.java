package com.nvhn.tumblrview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by nishant on 15.06.17.
 */

public class TumblrView extends View {

    private Paint paint;
    private Bitmap bitmap ;

    private  int r ;

    private int count =6;

    private int maxRadius = 150;

    private int h;
    private int w;

    private ShapeDrawable shapeDrawable;


    public TumblrView(Context context){
        super(context);
        paint = new Paint();
        OvalShape ovalShape = new OvalShape();
        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setIntrinsicHeight(80);
        shapeDrawable.setIntrinsicWidth(80);
        shapeDrawable.getPaint().setColor(Color.RED);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.fb);
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(Color.MAGENTA);
        int cx = getWidth()/2;
        int cy = getHeight()/2;

        int ih = shapeDrawable.getIntrinsicHeight();
        int iw = shapeDrawable.getIntrinsicWidth();
        shapeDrawable.setIntrinsicWidth(80);
        shapeDrawable.setIntrinsicHeight(80);
        shapeDrawable.setBounds(cx,cy,cx+shapeDrawable.getIntrinsicWidth(),cy+shapeDrawable.getIntrinsicHeight());
        shapeDrawable.draw(canvas);
        int tempR = r;
        for(int i= count-1;i >=0 ;i--){
            if(i== count -1){
                r = tempR;
                shapeDrawable.setIntrinsicWidth(w);
                shapeDrawable.setIntrinsicHeight(h);
            }else{
                r = maxRadius;
                shapeDrawable.setIntrinsicWidth(80);
                shapeDrawable.setIntrinsicHeight(80);
            }
            int x = cx + (int)(r *  Math.sin(Math.toRadians( 360*(5-i)/6)));
            int y = cy + (int)(r* Math.cos(Math.toRadians( 360*(5-i)/6)));
            shapeDrawable.setBounds(x,y,x+shapeDrawable.getIntrinsicWidth(),y+shapeDrawable.getIntrinsicHeight());
            shapeDrawable.draw(canvas);
        }
    }

    private void startAnim(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1,100);
        valueAnimator.setDuration(150);
        valueAnimator.reverse();
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                r = (int)(maxRadius*(1.0- valueAnimator.getAnimatedFraction()));
                h = (int)(bitmap.getHeight()*(1.0- valueAnimator.getAnimatedFraction()));
                w = (int)(bitmap.getWidth()*(1.0- valueAnimator.getAnimatedFraction()));
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(count>1) {
                    count--;
                    startAnim();
                }else{
                    count =6;
                    startAnim();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.start();
    }
    
}
