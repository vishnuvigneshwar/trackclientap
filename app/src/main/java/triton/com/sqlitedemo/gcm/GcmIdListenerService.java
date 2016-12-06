package triton.com.sqlitedemo.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class GcmIdListenerService extends InstanceIDListenerService {
  static String TAG = "GcmIdListenerService";

  @Override
  public void onTokenRefresh() {
//    super.onTokenRefresh();
    Intent intent = new Intent(this, GcmIntentService.class);
    startService(intent);
  }
}
