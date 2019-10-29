package g.trippl3dev.com.validation.data;

public class ValidationTag {
    private int viewType;
    private String errorText;
    private boolean isIgnore;
    private boolean watch;
    private String pattern;
    private String emptyError;


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public boolean isIgnore() {
        return isIgnore;
    }

    public void setIgnore(boolean ignore) {
        isIgnore = ignore;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getEmptyError() {
        return emptyError;
    }

    public void setEmptyError(String emptyError) {
        this.emptyError = emptyError;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }
}
