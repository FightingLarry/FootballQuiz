package com.android4.fragment.fonts;

import roboguice.inject.InjectView;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android4.R;
import com.android4.fragment.BaseFragment;

public class FontsFragment extends BaseFragment {

    @InjectView(R.id.etFont)
    private EditText etFont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fonts, null);
        try {

            Typeface croissant_sandwich = Typeface.createFromAsset(mActivity.getAssets(),
                    "fonts/croissant_sandwich.ttf");
            TextView tvCroissantSandwich = (TextView) view.findViewById(R.id.tvFonts2);
            tvCroissantSandwich.setTypeface(croissant_sandwich);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.onCreateView(inflater, container, savedInstanceState);
        }

        return view;
    }

    public static Fragment newInstance() {
        return new FontsFragment();
    }
}
