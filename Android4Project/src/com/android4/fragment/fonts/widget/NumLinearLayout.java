package com.android4.fragment.fonts.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android4.fragment.fonts.function.NumFormat;

public class NumLinearLayout extends LinearLayout {

    private Context context;

    private NumFormat mNumFormat;

    public NumLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public NumLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.context = context;
        mNumFormat = new NumFormat();
    }

    public void addNumImage(String num) {
        if (TextUtils.isEmpty(num)) {
            return;
        }
        this.removeAllViews();
        for (int i = 0; i < num.length(); i++) {
            Integer resId = mNumFormat.parse(String.valueOf(num.charAt(i)));
            if (resId != null) {
                ImageView cell = createImageView();
                cell.setImageResource(resId);
                this.addView(cell);
            }
        }
    }

    private ImageView createImageView() {
        ImageView cell = new ImageView(context);
        cell.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        return cell;
    }
}
