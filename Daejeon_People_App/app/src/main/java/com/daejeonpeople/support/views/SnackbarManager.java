package com.daejeonpeople.support.views;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by JoMingyu on 2017-07-04.
 */

public class SnackbarManager {
    public static Snackbar createDefaultSnackbar(View v, String text, int duration, int color) {
        Snackbar snackbar = Snackbar.make(v, text, duration);

        return snackbar;
    }

    public static Snackbar createCancelableSnackbar(View v, String text, int duration, int color) {
        Snackbar snackbar = Snackbar.make(v, text, duration).setActionTextColor(color).setAction("취소", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        return snackbar;
    }
}
