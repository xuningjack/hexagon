package com.example.widget;

import java.util.ArrayList;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


/**
 * 正六边形的布局
 * @author Jack
 */
public class SpecailView extends ViewGroup {
	
    private static final String TAG = "SpecailView";
    private static final int RADIU_COUNT = 8;
    private static final int PADDING = 10;

    private int childRadius;
    private int childWidth;
    private int childHeight;
    private int mChildCount;
    private int centerX ,centerY;

    public SpecailView(Context context) {
        super(context);
        Log.i(TAG, "SpecailView()");
    }

    public SpecailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.i(TAG, "SpecailView( , , )");
    }

    public SpecailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "SpecailView( , )");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(wSize, hSize);

        centerX = wSize / 2;
        centerY = hSize / 2;
        childRadius = (wSize - PADDING * 2) / RADIU_COUNT;
        childWidth = childRadius * 2;
        childHeight = (int)(childRadius * Math.sqrt(3) / 2)*2;
        final int count = getChildCount();
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            // measure
            child.measure(childWidth, childHeight);
        }
        if (mChildCount != count) {
            mChildCount = count;
        }
//        if (mChildCount > centers.size()) {
            computerPoint(centerX, centerY, childHeight);
//        }
        Log.i(TAG, "onMeasure()--childWidth="+childWidth+",childHeight="+childHeight);
        Log.i(TAG, "onMeasure()--wMode=" + wMode + ",wSize=" + wSize + ",hMode=" + hMode+ ",hSize=" + hSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        int childLeft, childTop;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            childLeft = (int)(centers.get(i).x-childRadius);
            childTop =  (int)(centers.get(i).y-childHeight/2);
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }
        Log.i(TAG, "onLayout()--changed=" + changed + ",left=" + left + ",top=" + top + ",right="
                + right + ",bottom=" + bottom + ",count=" + count);
    }

    private int getCircleIndex(int i) {
        int index = 0;
        while (i > (3*index*index + 3*index)) {
            index ++;
        }
        return index;
    }

    /**
     *  index start from 0
     */
    private int getCircleCount(int index) {
        if (index == 0) {
            return 1;
        }
        return index*6;
    }

    private void computerPoint(double a, double b, double h) {
        double sqrt3 = Math.sqrt(3);

        CircleCenteter c01 = new CircleCenteter(a, b);

        CircleCenteter c11 = new CircleCenteter(a, b-h);
        CircleCenteter c12 = new CircleCenteter(a + sqrt3*h/2, b - h/2);
        CircleCenteter c13 = new CircleCenteter(a + sqrt3*h/2, b + h/2);
        CircleCenteter c14 = new CircleCenteter(a, b + h);
        CircleCenteter c15 = new CircleCenteter(a - sqrt3*h/2, b + h/2);
        CircleCenteter c16 = new CircleCenteter(a - sqrt3*h/2, b - h/2);

        CircleCenteter c21 = new CircleCenteter(a, b-2*h);
        CircleCenteter c22 = new CircleCenteter(a + sqrt3*h/2, b-3*h/2);
        CircleCenteter c23 = new CircleCenteter(a + sqrt3*h, b - h);
        CircleCenteter c24 = new CircleCenteter(a + sqrt3*h, b);
        CircleCenteter c25 = new CircleCenteter(a + sqrt3*h, b + h);
        CircleCenteter c26 = new CircleCenteter(a + sqrt3*h/2, b + 3*h/2);
        CircleCenteter c27 = new CircleCenteter(a, b + 2*h);
        CircleCenteter c28 = new CircleCenteter(a - sqrt3*h/2, b + 3*h/2);
        CircleCenteter c29 = new CircleCenteter(a - sqrt3*h, b + h);
        CircleCenteter c210 = new CircleCenteter(a - sqrt3*h, b);
        CircleCenteter c211 = new CircleCenteter(a - sqrt3*h, b - h);
        CircleCenteter c212 = new CircleCenteter(a - sqrt3*h/2, b-3*h/2);

        centers.clear();
        centers.add(c01);

        centers.add(c11);
        centers.add(c12);
        centers.add(c13);
        centers.add(c14);
        centers.add(c15);
        centers.add(c16);

        centers.add(c21);
        centers.add(c22);
        centers.add(c23);
        centers.add(c24);
        centers.add(c25);
        centers.add(c26);
        centers.add(c27);
        centers.add(c28);
        centers.add(c29);
        centers.add(c210);
        centers.add(c211);
        centers.add(c212);
    }

    public void setOnItemClick(SpecailButton.OnClickListener l) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            ((SpecailButton)getChildAt(i)).setOnClickListener(l);
        }
    }

    private ArrayList<CircleCenteter> centers = new ArrayList<SpecailView.CircleCenteter>(7);
    class CircleCenteter {
        double x ,y;
        public CircleCenteter(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
}


