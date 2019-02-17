package edu.iastate.graysonc.fastfood;
import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

//Temp
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue aRequestQueue;

    private static AppController anInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        anInstance = this;
    }

    public static synchronized AppController getInstance() {
        return anInstance;
    }

    public RequestQueue getRequestQueue() {
        if (aRequestQueue == null) {
            aRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return aRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (aRequestQueue != null) {
            aRequestQueue.cancelAll(tag);
        }
    }
}
