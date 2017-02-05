package com.example.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android_hexagon.R;


/**
 * 正六边形的按钮
 * @author Administrator
 */
public class SpecailButton extends TextView implements View.OnClickListener {
	
    private static final String TAG = "SpecailButton";
    public static final int TEXT_ALIGN_LEFT              = 0x00000001;
    public static final int TEXT_ALIGN_RIGHT             = 0x00000010;
    public static final int TEXT_ALIGN_CENTER_VERTICAL   = 0x00000100;
    public static final int TEXT_ALIGN_CENTER_HORIZONTAL = 0x00001000;
    public static final int TEXT_ALIGN_TOP               = 0x00010000;
    public static final int TEXT_ALIGN_BOTTOM            = 0x00100000;


    /** 控件画笔 */
    private Paint paint;
    /** 文字的方位 */
    private int textAlign;
    /** 文字的颜色 */
    private int textColor;
    /** 控件的宽度 */
    private int viewWidth;
    /** 控件的高度 */
    private int viewHeight;
    /** 文本中轴线X坐标 */
    private float textCenterX;
    /** 文本baseline线Y坐标 */
    private float textBaselineY;
    private String text;
    private FontMetrics fm;
    private Context mContext;
    private boolean checked = false;

    public SpecailButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SpecailButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public SpecailButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * 变量初始化
     */
    private void init() {
        setOnClickListener(this);
        text = getText().toString();
        setText("");
        paint = new Paint();
        paint.setTextSize(22);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.CENTER);
        //默认情况下文字居中显示
        textAlign = TEXT_ALIGN_CENTER_HORIZONTAL | TEXT_ALIGN_CENTER_VERTICAL;
        //默认的文本颜色是黑色
        textColor = Color.BLACK;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(wSize, hSize);
        Log.i(TAG, "onMeasure()--wMode=" + wMode + ",wSize=" + wSize + ",hMode=" + hMode+ ",hSize=" + hSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        Log.i(TAG, "onLayout");
        viewWidth = right - left; 
        viewHeight = bottom - top;
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制控件内容
        setTextLocation(text);
        canvas.drawText(text, textCenterX, textBaselineY, paint);
    }

    /**
     * 定位文本绘制的位置
     */
    private void setTextLocation(String text) {
//        paint.setTextSize(textSize);
        paint.setColor(textColor);
        fm = paint.getFontMetrics();
        //文本的宽度
        float textWidth = paint.measureText(text);
        float textCenterVerticalBaselineY = viewHeight / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        switch (textAlign) {
        case TEXT_ALIGN_CENTER_HORIZONTAL | TEXT_ALIGN_CENTER_VERTICAL:
            textCenterX = (float)viewWidth / 2;
            textBaselineY = textCenterVerticalBaselineY;
            break;
        case TEXT_ALIGN_LEFT | TEXT_ALIGN_CENTER_VERTICAL:
            textCenterX = textWidth / 2;
            textBaselineY = textCenterVerticalBaselineY;
            break;
        case TEXT_ALIGN_RIGHT | TEXT_ALIGN_CENTER_VERTICAL:
            textCenterX = viewWidth - textWidth / 2;
            textBaselineY = textCenterVerticalBaselineY;
            break;
        case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_CENTER_HORIZONTAL:
            textCenterX = viewWidth / 2;
            textBaselineY = viewHeight - fm.bottom; 
            break;
        case TEXT_ALIGN_TOP | TEXT_ALIGN_CENTER_HORIZONTAL:
            textCenterX = viewWidth / 2;
            textBaselineY = -fm.ascent;
            break;
        case TEXT_ALIGN_TOP | TEXT_ALIGN_LEFT:
            textCenterX = textWidth / 2;
            textBaselineY = -fm.ascent;
            break;
        case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_LEFT:
            textCenterX = textWidth / 2;
            textBaselineY = viewHeight - fm.bottom; 
            break;
        case TEXT_ALIGN_TOP | TEXT_ALIGN_RIGHT:
            textCenterX = viewWidth - textWidth / 2;
            textBaselineY = -fm.ascent;
            break;
        case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_RIGHT:
            textCenterX = viewWidth - textWidth / 2;
            textBaselineY = viewHeight - fm.bottom; 
            break;
        }
    }

    public interface OnClickListener {
        void onClick(View v, boolean checked);
    }
    private OnClickListener mListener;
    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        checked = !checked;
        setBackgroundResource(checked ? 0 : R.drawable.ic_hexagon);
        if (mListener != null) {
            mListener.onClick(v, checked);
        }
    }

    public String getTextString() {
        return text;
    }

}