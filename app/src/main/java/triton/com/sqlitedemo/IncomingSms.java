package triton.com.sqlitedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import triton.com.sqlitedemo.gps.SingleShotLocationProvider;

import static triton.com.sqlitedemo.MainActivity.context1;

/**
 * Created by vishnu on 24-11-2016.
 */

public class IncomingSms extends BroadcastReceiver {
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    float Lat;
    float Lan;
    Context c;
   // Context context1;

    public void onReceive(Context context, Intent intent) {
        c = context;
        //context1 = context;
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                    if(message.equals("Locacion-Sync"))
                    {
                        SingleShotLocationProvider.requestSingleUpdate(context,
                                new SingleShotLocationProvider.LocationCallback() {
                                    @Override
                                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                                        Log.d("Location", "my location is " + location.toString());
                                        Lat = location.latitude;
                                        Lan = location.longitude;
                                        SmsManager smsManager = SmsManager.getDefault();
                                        String m = "{\"List\":[{\"Lat\":"+Lat+",\"Lan\":"+Lan+"}]}";
                                        smsManager.sendTextMessage("9095100105", null, ""+m, null, null);
                                        c.getContentResolver().delete(Uri.parse("content://sms/outbox"), "address = ? and body = ?", new String[] {"9095100105",m.toString()});
                                        c.getContentResolver().delete(Uri.parse("content://sms/sent"), "address = ? and body = ?", new String[] {"9095100105",m.toString()});
                                    }
                                });

                    }

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }



    void fun(){
        SingleShotLocationProvider.requestSingleUpdate(context1,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.d("Location", "my location is " + location.toString());
                        Lat = location.latitude;
                        Lan = location.longitude;
                        SmsManager smsManager = SmsManager.getDefault();
                        String m = "{\"List\":[{\"Lat\":"+Lat+",\"Lan\":"+Lan+"}]}";
                        smsManager.sendTextMessage("9095100105", null, ""+m, null, null);

                    }
                });
    }
}
