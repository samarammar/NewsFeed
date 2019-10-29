package com.samar.newsfeeds.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.hannesdorfmann.mosby3.ActivityMviDelegate;
import com.hannesdorfmann.mosby3.MviDelegateCallback;
import com.hannesdorfmann.mosby3.PresenterManager;
import com.hannesdorfmann.mosby3.mvi.MviPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.samar.newsfeeds.base.MVIBasePresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import java.util.UUID;


public class ActivityMVIDelegateImpl<V extends MvpView, P extends MviPresenter<V, ?>>
        implements ActivityMviDelegate {


    public static boolean DEBUG = false;
    private static final String DEBUG_TAG = "ActivityMviDelegateImpl";
    private static final String KEY_MOSBY_VIEW_ID = "com.hannesdorfmann.mosby3.activity.mvi.id";
    private String mosbyViewId = null;

    private MviDelegateCallback<V, P> delegateCallback;
    private Activity activity;
    private boolean keepPresenterInstance;
    private P presenter;

    /**
     * Creates a new Delegate with an presenter that survives screen orientation changes
     *
     * @param activity         The activity
     * @param delegateCallback The delegate callback
     */
    public ActivityMVIDelegateImpl(@NonNull Activity activity,
                                   @NonNull MviDelegateCallback<V, P> delegateCallback) {
        this(activity, delegateCallback, true);
    }

    /**
     * Creates a new delegate
     *
     * @param activity              The activity
     * @param delegateCallback      The delegate callback
     * @param keepPresenterInstance true, if the presenter instance should be kept through screen
     *                              orientation changes, false if not (a new presenter instance will be created every time you
     *                              rotate your device)
     */
    public ActivityMVIDelegateImpl(@NonNull Activity activity,
                                   @NonNull MviDelegateCallback<V, P> delegateCallback, boolean keepPresenterInstance) {

        if (activity == null) {
            throw new NullPointerException("Activity is null");
        }

        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }
        this.delegateCallback = delegateCallback;
        this.activity = activity;
        this.keepPresenterInstance = keepPresenterInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        if (keepPresenterInstance && bundle != null) {
            mosbyViewId = bundle.getString(KEY_MOSBY_VIEW_ID);
        }

        if (DEBUG) {
            Log.d(DEBUG_TAG,
                    "MosbyView ID = " + mosbyViewId + " for MvpView: " + delegateCallback.getMvpView());
        }


        if (mosbyViewId == null) {
            // No presenter available,
            // Activity is starting for the first time (or keepPresenterInstance == false)
            presenter = createViewIdAndCreatePresenter();
            if (DEBUG) {
                Log.d(DEBUG_TAG, "new Presenter instance created: "
                        + presenter
                        + " for "
                        + delegateCallback.getMvpView());
            }
        } else {
            presenter = PresenterManager.getPresenter(activity, mosbyViewId);
            if (presenter == null) {
                // Process death,
                // hence no presenter with the given viewState id stored, although we have a viewState id
                presenter = createViewIdAndCreatePresenter();
                if (DEBUG) {
                    Log.d(DEBUG_TAG,
                            "No Presenter instance found in cache, although MosbyView ID present. This was caused by process death, therefore new Presenter instance created: "
                                    + presenter);
                }
            } else {
                if (DEBUG) {
                    Log.d(DEBUG_TAG, "Presenter instance reused from internal cache: " + presenter);
                }
            }
        }
        // presenter is ready, so attach viewState
        V view = delegateCallback.getMvpView();
        if (view == null) {
            throw new NullPointerException(
                    "MvpView returned from getMvpView() is null. Returned by " + activity);
        }

        presenter.attachView(view);
        doStuff();
    }

    public void doStuff(){
        boolean viewStateWillBeRestored = false;

        if (mosbyViewId == null) {
            // No presenter available,
            // Activity is starting for the first time (or keepPresenterInstance == false)
            presenter = createViewIdAndCreatePresenter();
            if (DEBUG) {
                Log.d(DEBUG_TAG, "new Presenter instance created: "
                        + presenter
                        + " for "
                        + delegateCallback.getMvpView());
            }
        } else {
            presenter = PresenterManager.getPresenter(activity, mosbyViewId);
            if (presenter == null) {
                // Process death,
                // hence no presenter with the given viewState id stored, although we have a viewState id
                presenter = createViewIdAndCreatePresenter();
                if (DEBUG) {
                    Log.d(DEBUG_TAG,
                            "No Presenter instance found in cache, although MosbyView ID present. This was caused by process death, therefore new Presenter instance created: "
                                    + presenter);
                }
            } else {
                viewStateWillBeRestored = true;
                if (DEBUG) {
                    Log.d(DEBUG_TAG, "Presenter instance reused from internal cache: " + presenter);
                }
            }
        }

//        // presenter is ready, so attach viewState
        V view = delegateCallback.getMvpView();
        if (view == null) {
            throw new NullPointerException(
                    "MvpView returned from getMvpView() is null. Returned by " + activity);
        }
//
        if (viewStateWillBeRestored) {
            delegateCallback.setRestoringViewState(true);
        }
//
//        presenter.attachView(view);
        if (presenter instanceof MVIBasePresenter) {
            ((MVIBasePresenter) presenter).onStart(view);
        }
        if (viewStateWillBeRestored) {
            delegateCallback.setRestoringViewState(false);
        }

        if (DEBUG) {
            Log.d(DEBUG_TAG,
                    "MvpView attached to Presenter. MvpView: " + view + "   Presenter: " + presenter);
        }
    }

    @Override
    public void onStart() {

    }

    /**
     * Generates the unique (mosby internal) view id and calls {@link
     * MviDelegateCallback#createPresenter()}
     * to create a new presenter instance
     *
     * @return The new created presenter instance
     */
    private P createViewIdAndCreatePresenter() {

        P presenter = delegateCallback.createPresenter();
        if (presenter == null) {
            throw new NullPointerException(
                    "Presenter returned from createPresenter() is null. Activity is " + activity);
        }
        if (keepPresenterInstance) {
            mosbyViewId = UUID.randomUUID().toString();
            PresenterManager.putPresenter(activity, mosbyViewId, presenter);
        }
        return presenter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (keepPresenterInstance && outState != null) {
            outState.putString(KEY_MOSBY_VIEW_ID, mosbyViewId);
            if (DEBUG) {
                Log.d(DEBUG_TAG, "Saving MosbyViewId into Bundle. ViewId: " + mosbyViewId);
            }
        }
    }

    /**
     * Determines whether or not a Presenter Instance should be kept
     *
     * @param keepPresenterInstance true, if the delegate has enabled keep
     */
    static boolean retainPresenterInstance(boolean keepPresenterInstance, Activity activity) {
        return keepPresenterInstance && (activity.isChangingConfigurations()
                || !activity.isFinishing());
    }

    //    @Override public void onStop() {
//        presenter.detachView();
//
//        if (DEBUG) {
//            Log.d(DEBUG_TAG, "detached MvpView from Presenter. MvpView "
//                    + delegateCallback.getMvpView()
//                    + "   Presenter: "
//                    + presenter);
//        }
//
//
//    }
    @Override
    public void onStop() {
//        presenter.detachView();

        if (DEBUG) {
            Log.d(DEBUG_TAG, "detached MvpView from Presenter. MvpView "
                    + delegateCallback.getMvpView()
                    + "   Presenter: "
                    + presenter);
        }


    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        boolean retainPresenterInstance = retainPresenterInstance(keepPresenterInstance, activity);
        if (!retainPresenterInstance) {
            presenter.destroy();
            if (mosbyViewId != null) { // mosbyViewId == null if keepPresenterInstance == false
                PresenterManager.remove(activity, mosbyViewId);
            }
            Log.d(DEBUG_TAG, "Destroying Presenter permanently " + presenter);
        }

        presenter = null;
        activity = null;
        delegateCallback = null;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onRestart() {
    }

    @Override
    public void onContentChanged() {
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }
}
