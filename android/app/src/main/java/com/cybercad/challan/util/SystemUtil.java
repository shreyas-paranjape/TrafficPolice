package com.cybercad.challan.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public final class SystemUtil {

    private static final String TAG = SystemUtil.class.getName();

    public static final LayoutInflater getLayoutInflator(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static final void setTextViewText(View parent, int childId, String text) {
        View childView = parent.findViewById(childId);
        if (childView instanceof TextView) {
            ((TextView) childView).setText(text);
        } else {
            Log.d(TAG, "Cannot set text.The view is not a text view.");
        }
    }
}
