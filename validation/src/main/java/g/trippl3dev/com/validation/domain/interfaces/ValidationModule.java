package g.trippl3dev.com.validation.domain.interfaces;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import g.trippl3dev.com.validation.data.ValidationTag;
import g.trippl3dev.com.validation.domain.CommonTypes;
import g.trippl3dev.com.validation.domain.ValidationToast;

public class ValidationModule {
    private ValidationToastMethod validationToast;
    private ValidationChecker.OverallValidationChecker overallValidationChecker;

    private SparseArray<ValidationChecker> validationCheckerSparseArray = new SparseArray<>();

    private ValidationModule() {
        registerCommonTypes();
    }

    public static ValidationModule get() {
        return new ValidationModule();
    }

    private void registerCommonTypes() {
        setDefaultToast(ValidationToast.ToastTypes.SNACKBAR);
        addType(CommonTypes.EditTextType.EDITTEXT, new CommonTypes.EditTextType());
    }

    public ValidationModule setToastMethod(ValidationToastMethod toastMethod) {
        this.validationToast = toastMethod;
        return this;
    }

    public ValidationModule setDefaultToast(@ValidationToast.ToastTypes int validationType) {
        ValidationToast validationToast = new ValidationToast();
        validationToast.setType(validationType);
        this.validationToast = validationToast;
        return this;
    }

    public ValidationModule addType(int type, ValidationChecker validationChecker) {
        validationChecker.setValidationModule(this);
        validationCheckerSparseArray.put(type, validationChecker);
        return this;
    }




    private boolean isIgnore(View view) {
        if (view.getTag() instanceof ValidationTag)
            return ((ValidationTag) view.getTag()).isIgnore();
        return false;
    }

    public boolean validate(View view) {
        return validateView(view);
    }

    private boolean validateView(View view) {
        if (isIgnore(view)) return true;
        if (view instanceof ViewGroup) {
            if (view.getVisibility() != View.VISIBLE) return true;
            if (!validateData(view)) return false;
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View viewChild = ((ViewGroup) view).getChildAt(i);
                if (!validateView(viewChild)) return false;
            }
        } else {
            if (view.getVisibility() != View.VISIBLE) return true;
            if (!validateData(view)) return false;
        }
        return validateData(view);
    }

    private boolean validateData(View view) {
    if (!(view.getTag() instanceof ValidationTag)) return true;
        ValidationTag validationTag = (ValidationTag) view.getTag();
        if (validationTag == null) return true;
        ValidationChecker checker = validationCheckerSparseArray.get(validationTag.getViewType());
        return checker.checking(validationTag, view);
    }

    public void enableWatching(View view) {
        if (isIgnore(view)) return;
        if (view instanceof ViewGroup) {
            if (view.getVisibility() != View.VISIBLE) return;
            watchView(view);
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View viewChild = ((ViewGroup) view).getChildAt(i);
                enableWatching(viewChild);
            }
        } else {
            if (view.getVisibility() != View.VISIBLE) return;
            watchView(view);
        }
    }

    private void watchView(View view) {
        ValidationTag validationTag = (ValidationTag) view.getTag();
        if (validationTag == null || !validationTag.isWatch()) return;
        ValidationChecker checker = validationCheckerSparseArray.get(validationTag.getViewType());
        if (checker == null) return;
        checker.setWatching(view, validationTag);
    }


    /*****************/


    public ValidationToastMethod getValidationToast() {
        return validationToast;
    }

    public ValidationModule setOverallValidationChecker(ValidationChecker.OverallValidationChecker overallValidationChecker) {
        this.overallValidationChecker = overallValidationChecker;
        return this;
    }

    public ValidationChecker.OverallValidationChecker getOverallValidationChecker(){
        return  this.overallValidationChecker ;
    }



}
