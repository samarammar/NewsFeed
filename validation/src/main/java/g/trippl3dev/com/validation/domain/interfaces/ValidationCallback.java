package g.trippl3dev.com.validation.domain.interfaces;

/**
 * Created by Mahmoud Abdelaal on 2/20/2018.
 * Whole Validation Callback
 */

public interface ValidationCallback {
    void onValidationError();

    void onSuccess();
}