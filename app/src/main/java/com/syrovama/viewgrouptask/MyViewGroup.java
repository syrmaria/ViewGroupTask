package com.syrovama.viewgrouptask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        int leftPosition = left + getPaddingLeft();
        int rightPosition = right - getPaddingRight();
        int topPosition = top + getPaddingTop();
        int bottomPosition = bottom + getPaddingBottom();
        int maxChildRightPosition = leftPosition;

        for (int i=0; i<childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                if (topPosition + height > bottomPosition) {
                    //начинаю новый столбец
                    topPosition = top + getPaddingTop();
                    leftPosition = maxChildRightPosition;
                }
                int childBottomPosition = topPosition + height;
                int childRightPosition = leftPosition + width;
                if (childRightPosition <= rightPosition) {
                    //добавляю только если влезает целиком
                    child.layout(leftPosition, topPosition, childRightPosition, childBottomPosition);
                    if (childRightPosition > maxChildRightPosition) {
                        maxChildRightPosition = childRightPosition;
                    }
                    topPosition = childBottomPosition;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.measure(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.AT_MOST));
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
}
