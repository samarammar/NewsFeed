package g.trippl3dev.com.validation.domain;



import android.support.annotation.IntDef;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import g.trippl3dev.com.validation.domain.interfaces.ValidationToastMethod;

/**
 * Created by Mahmoud Abdelaal on 2/20/2018.
 * class that enable default
 */

public class ValidationToast implements ValidationToastMethod {

    private @ToastTypes
    int type = ToastTypes.SNACKBAR;

    @Override
    public void showToast(View view, String error) {
        switch (type) {
            case ToastTypes.TOAST:
                toast(view, error);
                break;
            case ToastTypes.SNACKBAR:
                snack(view, error);
                break;
        }
    }


    private void snack(View view, String error) {
        Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                .show();
    }

    private void toast(View view, String error) {
        Toast.makeText(view.getContext().getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    public void setType(@ToastTypes int toastTypes) {
        this.type = toastTypes;
    }


    @IntDef({ToastTypes.SNACKBAR, ToastTypes.TOAST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ToastTypes {
        int SNACKBAR = 1;
        int TOAST = 2;
    }
}
