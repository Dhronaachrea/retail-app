package com.skilrock.retailapp.sle_game_portrait;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.CheckBox;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

/**
 * Created by stpl on 6/16/2015.
 */
@SuppressLint("AppCompatCustomView")
public class DynamicTextCheckBox extends CheckBox {
    private float textSize = com.intuit.sdp.R.dimen._15sdp;

    private int type;

    public DynamicTextCheckBox(Context context) {
        super(context);
    }

    public DynamicTextCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicTextCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicTextCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int square = Math.max(getMeasuredHeight(), getMeasuredWidth());
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO)||Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            textSize = square * 0.7f * 0.5f;
        else
            textSize = square * 0.7f * 0.4f;

        textSize = pxToDp(textSize, getContext());
        setMeasuredDimension(square, (int) (square * (type <= 3 ? 0.30f : 0.60f)));
        init();
    }

//    protected void init(AttributeSet attrs) {
//        if (attrs!=null) {
//            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SquareCheckBox);
//            String fontNumber = a.getString(R.styleable.SquareCheckBox_typeface);
//            String fontName = null;
//            if (fontNumber!=null) {
//                switch (fontNumber){
//                    case "0": fontName = "ROBOTO-BOLD.TTF";break;
//                    case "1": fontName = "ROBOTO-MEDIUM.TTF";break;
//                    case "2": fontName = "ROBOTO-REGULAR.TTF";break;
//                    case "3": fontName = "ROBOTO-LIGHT.TTF";break;
//                    case "4": fontName = "ROBOTO-THIN.TTF";break;
//                }
//                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
//                setTypeface(myTypeface);
//            }
//            a.recycle();
//        }
//    }

    private float pxToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp - 22;
    }

    private void init() {
        setTextSize(textSize);

    }
}
