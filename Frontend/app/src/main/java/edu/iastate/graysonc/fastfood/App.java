package edu.iastate.graysonc.fastfood;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import edu.iastate.graysonc.fastfood.di.component.DaggerAppComponent;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initDagger();
        context = getApplicationContext();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    // ---

    private void initDagger() {
        DaggerAppComponent.builder().application(this).build().inject(this);
    }


    public final static String LOADING = "Loading";
    public final static String LOADED = "Loaded";
    public final static String CHECK_NETWORK_ERROR = "Check your network connection.";

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}