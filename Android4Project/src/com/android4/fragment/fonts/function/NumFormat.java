package com.android4.fragment.fonts.function;

import java.util.Hashtable;

import com.android4.R;

public class NumFormat {

    private final Hashtable<String, Integer> numFormatTable;

    public NumFormat() {
        numFormatTable = new Hashtable<String, Integer>();
        numFormatTable.put("0", R.drawable.num0);
        numFormatTable.put("1", R.drawable.num1);
        numFormatTable.put("2", R.drawable.num2);
        numFormatTable.put("3", R.drawable.num3);
        numFormatTable.put("4", R.drawable.num4);
        numFormatTable.put("5", R.drawable.num5);
        numFormatTable.put("6", R.drawable.num6);
        numFormatTable.put("7", R.drawable.num7);
        numFormatTable.put("8", R.drawable.num8);
        numFormatTable.put("9", R.drawable.num9);
    }

    public Integer parse(String num) {
        return numFormatTable.get(num);
    }
}
