package g.trippl3dev.com.validation.domain.interfaces.Enums;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Mahmoud Abdelaal on 2/21/2018.
 * Validation states of validate data
 */
@IntDef({ValidationState.EMPTYERROR, ValidationState.VALIDATIONERROR, ValidationState.VALID})
@Retention(RetentionPolicy.SOURCE)
public @interface ValidationState {
    int EMPTYERROR = 0;
    int VALIDATIONERROR = 1;
    int VALID = 2;

}
