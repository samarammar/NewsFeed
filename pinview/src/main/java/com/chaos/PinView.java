package com.chaos;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.chaos.view.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/**
 * Created by Android on 1/11/2018.
 */

public class PinView extends AppCompatEditText {
    // num of items
    int numberOfItems=4;

    // width & height of pin Item
    float pinItemWidth;
    float pinItemHeight;

    // item Spacing
    float itemSpacing;

    // define active pin color & not active pin color
    ColorStateList activePinColor;
    ColorStateList notActivePinColor;

    //define variable for itemShapeType
    @ShapeTypes  int shapeType;


    // define paint object that has colors and fonts of paper it's like the pen
    Paint paint;
    TextPaint textPaint;
    private int activeIndex;
    private Drawable activeDrawable;
    private Drawable notActiveDrawable;
    private ColorStateList finishedColor;
    private ColorStateList errorColor;
    private Drawable finishedDrawable;
    private Drawable errorDrawable;
    private boolean strokError;
    private boolean strokSelected;
    private boolean showError;
    private ColorStateList itemTextColor;
    private ColorStateList itemHintColor;

    // define types of Pin View Shapes
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            ShapeTypes.CIRECLE
            , ShapeTypes.RECTANGLE
            , ShapeTypes.LINE
            , ShapeTypes.DRAWABLE
    })
    public @interface ShapeTypes{
        int DRAWABLE = 3;
        int CIRECLE = 2;
        int RECTANGLE = 1;
        int LINE = 0;
    }

    // define pinView finished Callback
    OnPinViewFinishedCallback onPinViewFinishedCallback;





    public void setOnPinViewFinishedCallback(OnPinViewFinishedCallback callback){
        this.onPinViewFinishedCallback = callback;
    }


    public PinView(Context context) {
        super(context);
        initData();
    }

    public PinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);
        initData();
    }

    public PinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(attrs);
        initData();
    }


    public void setAttributes(AttributeSet attributes) {
        TypedArray attrs = getContext().getTheme().obtainStyledAttributes(attributes, R.styleable.PinView, 0, 0);
        Resources res = getContext().getResources();

        // Extract custom attributes into member variables
        try {
            this.numberOfItems = attrs.getInt(R.styleable.PinView_itemCount, 4);
            this.itemSpacing  = attrs.getDimensionPixelOffset(R.styleable.PinView_itemSpacing,res.getDimensionPixelOffset(com.chaos.view.R.dimen.pv_pin_view_item_spacing));
            this.pinItemHeight = attrs.getDimensionPixelOffset(R.styleable.PinView_itemHeight,res.getDimensionPixelOffset(com.chaos.view.R.dimen.pv_pin_view_item_size));
            this.pinItemWidth= attrs.getDimensionPixelOffset(R.styleable.PinView_itemWidth,res.getDimensionPixelOffset(com.chaos.view.R.dimen.pv_pin_view_item_size));
            this.shapeType= attrs.getInt(R.styleable.PinView_shapeView, ShapeTypes.CIRECLE);
            this.strokError = attrs.getBoolean(R.styleable.PinView_strokError,false);
            this.strokSelected = attrs.getBoolean(R.styleable.PinView_strokSelected,false);
            this.itemTextColor = attrs.getColorStateList(R.styleable.PinView_itemTextColor);
            this.itemHintColor = attrs.getColorStateList(R.styleable.PinView_itemHintColor);

            if (shapeType == ShapeTypes.DRAWABLE){
                this.activeDrawable = attrs.getDrawable(R.styleable.PinView_selected);
                this.notActiveDrawable = attrs.getDrawable(R.styleable.PinView_notSelected);
                this.finishedDrawable = attrs.getDrawable(R.styleable.PinView_finished);
                this.errorDrawable = attrs.getDrawable(R.styleable.PinView_error);
                if (finishedDrawable == null)finishedDrawable = notActiveDrawable;
//                if (finishedDrawable == null)finishedDrawable = notActiveDrawable;
            }else{
                this.activePinColor= attrs.getColorStateList(R.styleable.PinView_selected);
                this.notActivePinColor= attrs.getColorStateList(R.styleable.PinView_notSelected);
                this.finishedColor= attrs.getColorStateList(R.styleable.PinView_finished);
                this.errorColor= attrs.getColorStateList(R.styleable.PinView_error);
                if (finishedColor == null)finishedColor= notActivePinColor;
            }
        } finally {
            // TypedArray objects are shared and must be recycled.
            attrs.recycle();

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<numberOfItems;i++){
            drawItemAtIndex(canvas,i);
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        removeError();
        if (getText().length() == numberOfItems) {
            if (onPinViewFinishedCallback != null) {
                onPinViewFinishedCallback.onPinViewFinished(getText().toString());
            } else {
                clearFocus();
                hideKeyboard(this);
            }
        }else{
            onPinViewFinishedCallback.onPinViewNotFinished(getText().toString());
        }
        invalidate();
    }



    public static  void hideKeyboard(View view){
        final InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
//        setSelection(activeIndex);
        activeIndex   = selStart-1;
        CharSequence text = getText();
        if (text != null) {
            if (selStart != text.length() || selEnd != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }
    }

    public void initData(){
//        setWidthOfEditText();
        setTextColor(Color.TRANSPARENT);
        setBackgroundColor(Color.TRANSPARENT);
        setCursorVisible(false);
        setHintTextColor(Color.TRANSPARENT);
        setTextIsSelectable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
               // setLayoutDirection(LAYOUT_DIRECTION_LTR);
            ViewCompat.setLayoutDirection(this,ViewCompat.LAYOUT_DIRECTION_LTR);
            setTextLocale(new Locale("en"));
        }
//        setFocusableInTouchMode(false);

        setFilters(new InputFilter[] {new InputFilter.LengthFilter(numberOfItems)});
        preparePaint();
//        invalidate();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused){
            if (activeIndex >= 0)
                setSelection(activeIndex);
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        float boxHeight = pinItemHeight;

        if (widthMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            width = widthSize;
        } else {
            float boxesWidth = (numberOfItems - 1) * itemSpacing + numberOfItems* pinItemWidth;
            width = Math.round(boxesWidth + getPaddingRight() + getPaddingLeft());
            if (itemSpacing == 0) {
                width -= (numberOfItems - 1) * getContext().getResources().getDimensionPixelOffset(com.chaos.view.R.dimen.pv_pin_view_item_line_width);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            height = heightSize;
        } else {
            height = Math.round(boxHeight + getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(width, height);
    }

    public void preparePaint(){
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setStyle(Paint.Style.FILL);

        this.textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        this.textPaint.density = getContext().getResources().getDisplayMetrics().density;
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setTextSize(getTextSize());
        this.textPaint.setColor(this.itemTextColor.getDefaultColor());
    }

    ColorStateList tempColor;
    Drawable tempDrawable;
    public void flashError(){
        showError = true;
        if (shapeType  == ShapeTypes.DRAWABLE) {
            tempDrawable = finishedDrawable;
            finishedDrawable = errorDrawable;
        }else{
            tempColor = finishedColor;
            finishedColor = errorColor;
        }
    }

    public void removeError(){
        showError = false;
        if (tempDrawable != null || tempColor != null)
        if (shapeType  == ShapeTypes.DRAWABLE) {
            if (tempDrawable != null)
            finishedDrawable = tempDrawable;
        }else{
            if (tempColor != null)
            finishedColor = tempColor;
        }
    }

    // draw pin item for specific index
    public void drawItemAtIndex(Canvas canvas,int index){

        RectF rectF = getRectAtIndex(index);
        if (shapeType == ShapeTypes.DRAWABLE){
            Drawable drawable = index == activeIndex?activeDrawable:index<activeIndex?finishedDrawable:notActiveDrawable;
            Resources res = getResources();
            Bitmap bitmap = drawableToBitmap(drawable);
            canvas.drawBitmap(bitmap, rectF.left, rectF.top, paint);
        }else {
            if (index == activeIndex) {
                paint.setColor(activePinColor == null ? Color.WHITE : this.activePinColor.getColorForState(getDrawableState(), 0));
            } else {
                if (index <= activeIndex){
                    paint.setColor(finishedColor == null ? Color.WHITE : this.finishedColor.getColorForState(getDrawableState(), 0));
                }else {
                    paint.setColor(notActivePinColor == null ? Color.WHITE : this.notActivePinColor.getColorForState(getDrawableState(), 0));
                }
            }

            switch (shapeType) {
                case ShapeTypes.CIRECLE:
                    canvas.drawOval(rectF, paint);
                    break;
                case ShapeTypes.LINE:
                    canvas.drawLine(rectF.left, rectF.bottom, rectF.right, rectF.bottom, paint);
                    break;
                case ShapeTypes.RECTANGLE:
                    canvas.drawRect(rectF, paint);

                    break;
            }
        }

        drawTextAtIndex(canvas,rectF,index);
    }

    //get rectangle where draw pin item
    public RectF getRectAtIndex(int index){
        int left = 0;
//        if (index == 0) {
//            left = getPaddingLeft();
//        }else{
            left = getPaddingLeft() + (int) (index * pinItemWidth +itemSpacing*index);
//        }
        int top  = getPaddingTop();
        int bottom = (int) (getPaddingBottom() + pinItemHeight);
        int right = getPaddingLeft() +(int) ((index +1 ) * pinItemWidth + itemSpacing*index);
        return new RectF(left,top,right,bottom);
    }

    // draw text in pin item
    public void drawTextAtIndex(Canvas canvas,RectF containerRect,int index){
        String text ;
        if (getText().length() < index+1){
            text = getHint().charAt(index)+"";
            textPaint.setColor(itemHintColor.getDefaultColor());
        }else {
            text = getText().charAt(index) + "";
            textPaint.setColor(itemTextColor.getDefaultColor());
        }
        float x = (float) (containerRect.left+(float) pinItemWidth/2.0) ;
        x -= computeTextSize(text);
        float y = (float) (containerRect.top+ (float) pinItemHeight/2.0);
        y += computeTextSize(text);

        canvas.drawText(text,x,y,textPaint);
    }

    public float computeTextSize(String string){
        return textPaint.measureText(string)/2;
    }

    // convert drawable to bitmap
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public interface OnPinViewFinishedCallback{
        public void onPinViewFinished(String text);
        public void onPinViewNotFinished(String text);
    }
}
