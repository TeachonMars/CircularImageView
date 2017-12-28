package com.teachonmars.modules.widget.circularImageView;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class CircularImageView extends AppCompatImageView {
    private Path     circlePath;
    private Path     borderPath;
    private Paint    borderPaint;
    private int      canvasBeforeDrawState;
    private int      gapSize;
    private int      gapColor;
    private int      borderSize;
    private int      borderColor;
    private Drawable baseDrawable;
    private boolean  needInsetDrawable;

    public CircularImageView(Context context) {
        this(context, null);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.CircularImageViewDefault);
    }


    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        initFromAttrs(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void initFromAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyleAttr, defStyleRes);
        extractGapAndBorderShader(a);
        a.recycle();
    }

    public void setGapSize(int gapSize) {
        this.gapSize = gapSize;
        this.updateDrawable();
        this.updateBounds();
    }

    public void setGapColor(int gapColor) {
        this.gapColor = gapColor;
        this.updateBounds();
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
        this.updateDrawable();
        this.updateBounds();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        this.updateBounds();
    }

    public void setNeedInsetDrawable(boolean needInsetDrawable) {
        this.needInsetDrawable = needInsetDrawable;
        this.updateDrawable();
    }

    private void extractGapAndBorderShader(TypedArray a) {
        gapSize = a.getDimensionPixelSize(R.styleable.CircularImageView_gapSize, 0);
        borderSize = a.getDimensionPixelSize(R.styleable.CircularImageView_borderSize, 0);

        gapColor = a.getColor(R.styleable.CircularImageView_gapColor, 0);
        borderColor = a.getColor(R.styleable.CircularImageView_borderColor, 0);

        needInsetDrawable = a.getBoolean(R.styleable.CircularImageView_needInsetDrawable, false);
        updateDrawable();
    }

    private void init() {
        circlePath = new Path();
        borderPath = new Path();
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        updateBounds();
    }

    private void updateBounds() {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth > 0 && measuredHeight > 0) {
            setDrawBounds(measuredWidth - ViewCompat.getPaddingStart(this) - ViewCompat.getPaddingEnd(this),
                    measuredHeight - getPaddingTop() - getPaddingBottom());
        }
    }

    private void setDrawBounds(int width, int height) {
        float w = width / 2f;
        float h = height / 2f;
        float radius = Math.min(w, h);
        circlePath.reset();
        circlePath.addCircle(w, h, radius - (borderSize + gapSize), Path.Direction.CW);

        borderPath.reset();
        borderPath.addCircle(w, h, radius, Path.Direction.CW);

        borderPaint.setDither(true);
        borderPaint.setShader(buildBorderShader(w, h, radius));
    }

    private RadialGradient buildBorderShader(float xCenter, float yCenter, float radius) {
        float edgeAdjuster = 1f / radius;
        float centerEnd = (radius - (gapSize + borderSize)) / radius;
        float gapStart = centerEnd + edgeAdjuster;
        float gapEnd = centerEnd + gapSize / radius;
        float borderStart = gapEnd + edgeAdjuster;
        int[] colors = new int[]{noAlpha(gapColor), noAlpha(gapColor),
                gapColor, gapColor,
                borderColor, borderColor
        };
        float[] positions = new float[]{0, centerEnd - edgeAdjuster,
                gapStart, gapEnd,
                borderStart, 1};

        return new RadialGradient(xCenter, yCenter, radius, colors, positions, Shader.TileMode.CLAMP);
    }

    private int noAlpha(int color) {
        return Color.argb(0, Color.red(color), Color.green(color), Color.blue(color));
    }

    @Override
    public void draw(Canvas canvas) {
        canvasBeforeDrawState = canvas.save();
        canvas.clipPath(circlePath);
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.restoreToCount(canvasBeforeDrawState);
        canvas.drawPath(borderPath, borderPaint);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable baseDrawable) {
        this.baseDrawable = baseDrawable;
        updateDrawable();
    }


    private void updateDrawable() {
        if (baseDrawable != null && needInsetDrawable && (gapSize != 0 || borderSize != 0)) {
            int inset = (gapSize + borderSize);
            super.setImageDrawable(new CustomDrawable(baseDrawable, inset));
        } else {
            super.setImageDrawable(baseDrawable);
        }
    }
}
