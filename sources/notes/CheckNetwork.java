package notes;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork {
    Activity activity;

    public CheckNetwork(Activity activity) {
        this.activity = activity;
    }

    public boolean isOnline() {
        NetworkInfo n = ((ConnectivityManager) this.activity.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        return n != null && n.isAvailable() && n.isConnected();
    }
}
