package g.trippl3dev.com.validation.domain;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import g.trippl3dev.com.validation.data.ValidationTag;
import g.trippl3dev.com.validation.domain.interfaces.Enums.PATTERN;
import g.trippl3dev.com.validation.domain.interfaces.Enums.ValidationState;
import g.trippl3dev.com.validation.domain.interfaces.ValidationChecker;
import g.trippl3dev.com.validation.domain.interfaces.ValidationToastMethod;

public interface CommonTypes {
    class EditTextType extends ValidationChecker<EditText> {
        public static final int EDITTEXT = 0x00211;

        public EditTextType() {
            super();
        }

        @Override
        public @ValidationState
        int check(ValidationTag validationTag, EditText view) {
//            replace("[$,.]".toRegex(), "")
            String value = view.getText().toString().trim();
            if(!validationTag.getPattern().equals(PATTERN.EMAIL)) {
                 value = view.getText().toString().trim().replaceAll("[$,.]", "");
            }
            if (value.isEmpty()) return ValidationState.EMPTYERROR;
            return value.matches(validationTag.getPattern()) ? ValidationState.VALID : ValidationState.VALIDATIONERROR;
        }



        @Override
        public void setWatching(EditText editText, ValidationTag validationTag) {
            super.setWatching(editText, validationTag);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    checking(validationTag, editText);
                }
            });

        }


    }


}
