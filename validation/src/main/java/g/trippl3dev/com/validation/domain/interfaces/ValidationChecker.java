package g.trippl3dev.com.validation.domain.interfaces;

import android.view.View;

import g.trippl3dev.com.validation.data.ValidationTag;
import g.trippl3dev.com.validation.domain.interfaces.Enums.ValidationState;

public abstract class ValidationChecker<T> {
    private CheckerWatcher watcher;
    private ValidationModule validationModule;

    public ValidationChecker() {
        watcher = getWatcher();
    }

    public abstract int check(ValidationTag validationTag, T view);

    protected final boolean checking(ValidationTag validationTag, T view) {
        switch (check(validationTag, view)) {
            case ValidationState.EMPTYERROR:
                watcher.onNotValid(validationTag.getEmptyError(), view);
                break;
            case ValidationState.VALIDATIONERROR:
                watcher.onNotValid(validationTag.getErrorText(), view);
                break;
            case ValidationState.VALID:
                watcher.onValid(view);
                return true;
        }
        return false;
    }

    public ValidationToastMethod getToasMethod() {
        return validationModule.getValidationToast();
    }

    public CheckerWatcher<T> getWatcher() {
        return new CheckerWatcher<T>() {

            @Override
            public void onNotValid(String error, T view) {
                getToasMethod().showToast((View) view, error);
            }

            @Override
            public void onValid(T view) {

            }
        };
    }

    protected void setWatching(T v, ValidationTag validationTag) {

    }

    protected final void setValidationModule(ValidationModule validationModule) {
        this.validationModule = validationModule;
    }

    public interface OverallValidationChecker {
        void onNotValidError(String error, View view);

        void onValid();

        void onNotValid();
    }
}
