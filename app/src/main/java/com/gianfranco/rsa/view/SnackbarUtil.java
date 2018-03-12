package com.gianfranco.rsa.view;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.gianfranco.rsa.R;

public final class SnackbarUtil {
    public static void showSnackbar(View root, String snackbarText, String snackbarAction) {
        if (root == null || snackbarText == null || snackbarAction == null) {
            return;
        }

        Snackbar snackbar = Snackbar.make(root, snackbarText, Snackbar.LENGTH_LONG);
        snackbar.setAction(snackbarAction, v -> snackbar.dismiss());
        snackbar.setActionTextColor(ContextCompat.getColor(root.getContext(), R.color.colorSecondaryText));

        TextView textView = snackbar.getView().findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(root.getContext(), R.color.colorPrimaryText));

        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(root.getContext(), R.color.colorPrimary));

        snackbar.show();
    }
}
