package com.android4.fragment.fonts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android4.R;
import com.android4.fragment.BaseFragment;
import com.android4.fragment.fonts.widget.NumLinearLayout;

public class FontsFragment extends BaseFragment {

    private EditText etFont;

    private NumLinearLayout llNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fonts, null);
        try {
            Typeface android_robot = Typeface.createFromAsset(mActivity.getAssets(),
                    "fonts/ANDROID_ROBOT.ttf");
            TextView tvAndroidRobot = (TextView) view.findViewById(R.id.tvFonts);
            tvAndroidRobot.setTypeface(android_robot);

            Typeface croissant_sandwich = Typeface.createFromAsset(mActivity.getAssets(),
                    "fonts/croissant_sandwich.ttf");
            TextView tvCroissantSandwich = (TextView) view.findViewById(R.id.tvFonts2);
            tvCroissantSandwich.setTypeface(croissant_sandwich);

            Typeface splat = Typeface.createFromAsset(mActivity.getAssets(), "fonts/SPLAT.TTF");
            TextView tvSplat = (TextView) view.findViewById(R.id.tvFonts3);
            tvSplat.setTypeface(splat);

            Typeface prida61 = Typeface.createFromAsset(mActivity.getAssets(), "fonts/Prida61.otf");
            TextView tvPrida61 = (TextView) view.findViewById(R.id.tvFonts4);
            tvPrida61.setTypeface(prida61);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.onCreateView(inflater, container, savedInstanceState);
        }
        llNum = (NumLinearLayout) view.findViewById(R.id.llNum);
        etFont = (EditText) view.findViewById(R.id.etFont);
        etFont.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                llNum.addNumImage(s.toString());
            }
        });

        return view;
    }

    public static Fragment newInstance() {
        return new FontsFragment();
    }
}
