package g.trippl3dev.com.validation.domain.interfaces;


public interface CheckerWatcher<T> {

    public void onNotValid(String error, T view);

    public void onValid(T view);
}
