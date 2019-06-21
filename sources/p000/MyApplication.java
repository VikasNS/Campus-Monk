package p000;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import java.lang.Thread.UncaughtExceptionHandler;

/* renamed from: MyApplication */
public class MyApplication extends Application {

    /* renamed from: MyApplication$1 */
    class C00001 implements UncaughtExceptionHandler {
        C00001() {
        }

        public void uncaughtException(Thread thread, Throwable e) {
            Log.e(MediaRouteProviderProtocol.SERVICE_DATA_ERROR, e.toString());
        }
    }

    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new C00001());
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
