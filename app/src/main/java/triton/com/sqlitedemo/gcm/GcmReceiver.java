package triton.com.sqlitedemo.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import triton.com.sqlitedemo.MainActivity;
import triton.com.sqlitedemo.R;
import triton.com.sqlitedemo.utils.ServiceHandler;

public class GcmReceiver extends GcmListenerService {
    long minTime = 5 * 1000; // Minimum time interval for update in seconds, i.e. 5 seconds.
    long minDistance = 10; // Minimum distance change for update in meters, i.e. 10 meters.
    LocationManager locationManager;
    NotificationCompat.Builder builder;
    Notification notification;
    NotificationManager notificationManager;
    LocationListener locationListener;
    Location fastLocation;
    int icon;
    Uri uri;
    Intent notificationIntent;
    PendingIntent pendingIntent;
    public GcmReceiver() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        String provider = LocationManager.NETWORK_PROVIDER;

// Returns last known location, this is the fastest way to get a location fix.
        fastLocation = locationManager.getLastKnownLocation(provider);

       locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(GcmReceiver.this,
                        "Provider enabled: " + provider, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(GcmReceiver.this,
                        "Provider disabled: " + provider, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onLocationChanged(Location location) {
                // Do work with new location. Implementation of this method will be covered later.
                //doWorkWithNewLocation(location);
                Toast.makeText(GcmReceiver.this, "Location "+location.getLatitude()+" " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        };
    /*    Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
                locationManager.requestLocationUpdates(getProviderName(), minTime,
                        minDistance, locationListener);

                Toast.makeText(GcmReceiver.this, "immediat loc "+fastLocation.getLongitude()+" "+fastLocation.getLatitude(), Toast.LENGTH_SHORT).show();
            }
        };*/
    //    Toast.makeText(getBaseContext(), "immediat loc "+fastLocation.getLongitude()+" "+fastLocation.getLatitude(), Toast.LENGTH_SHORT).show();

      /*  SingleShotLocationProvider.requestSingleUpdate( GcmReceiver.this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.d("Location", "my location is " + location.toString());
                        Float Lat = location.latitude;
                        Float Lan = location.longitude;
                        SmsManager smsManager = SmsManager.getDefault();
                        String m = "{\"List\":[{\"Lat\":"+Lat+",\"Lan\":"+Lan+"}]}";
                        smsManager.sendTextMessage("9095100105", null, ""+m, null, null);
                    }
                });*/
        new NotificationDeliveryAsync().execute();
        //CustomerRequestNotification("Moto Taxo", data.getString("Notice"), data.getString("message") +  "immediat loc "+fastLocation.getLongitude()+" "+fastLocation.getLatitude(),"1",data.getString("device_id"));
       // SmsManager smsManager = SmsManager.getDefault();
        //String m = "{\"List\":[{\"Lat\":"+fastLocation.getLongitude()+",\"Lan\":"+fastLocation.getLatitude()+"}]}";
       // String m = "https://www.google.co.in/maps/place/"+fastLocation.getLatitude()+","+fastLocation.getLongitude()+" ";
      //  smsManager.sendTextMessage("9444234240", null, ""+m, null, null);
      //  smsManager.sendTextMessage("9710809161", null, ""+m, null, null);
      //  smsManager.sendTextMessage("9710809161", null, ""+m, null, null);
//
    } // onMessageReceived

    String getProviderName() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.
        criteria.setSpeedRequired(true); // Chose if speed for first location fix is required.
        criteria.setAltitudeRequired(false); // Choose if you use altitude.
        criteria.setBearingRequired(false); // Choose if you use bearing.
        criteria.setCostAllowed(false); // Choose if this provider can waste money :-)

        // Provide your criteria and flag enabledOnly that tells
        // LocationManager only to return active providers.
        return locationManager.getBestProvider(criteria, true);
    }

    void CustomerRequestNotification(String ticker, String title, String msg,String status,String device_typeid) {

        if (status.equals("1")) {
           /* SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("9095100105", null, "sending from android", null, null);*/
           /* if(message.equals("Locacion-Sync"))
            {*/


           /* }*/
            icon = R.mipmap.ic_launcher;
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            notificationIntent = new Intent(getBaseContext(), MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
           /*
           *
           * code to start an activity on message reached

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("GCMMESSAGE", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("GCM_MESSAGE", title);
            editor.putString("CUSTOMER_ID", msg);
            editor.putString("DEVICE_TYPEID", device_typeid);
            editor.commit();
            startActivity(notificationIntent);*/

            builder = new NotificationCompat.Builder(this);
            notification = builder.setSmallIcon(icon).setTicker(ticker).setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(), icon))
                    .setContentText(msg)
                    .setSound(uri)
                    .build();
            notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(new Random().nextInt(250), notification);
        }

    }

    public class NotificationDeliveryAsync extends AsyncTask<Void,Void,String> {
        ProgressDialog progressDialog;
        String response;
        String error = "x";
        Exception e1;
        HashMap<String,String> hashMap;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            ServiceHandler sh=new ServiceHandler();
            try{
                SharedPreferences sharedPreferences = getSharedPreferences("Driver", MODE_PRIVATE);
                String Name = sharedPreferences.getString("name", null);
                String Mobile = sharedPreferences.getString("mobile", null);
               // Toast.makeText(GcmReceiver.this, "name" + Name, Toast.LENGTH_SHORT).show();
                //response=sh.makeServiceCall("http://whitehousehub.in/demo/mototaxo/MotoUrl/trackeveryone.php?driver_id="+Mobile+"&driver_latitude="+fastLocation.getLatitude()+"&driver_longtitude="+fastLocation.getLongitude()+"&name="+Name+"&imie="+telephonyManager.getDeviceId().toString()+"&active_status=t1",ServiceHandler.GET);
                response=sh.makeServiceCall("http://jbugas.com/url/get_vendor_details.php?phone_no="+Mobile+"&latitude="+fastLocation.getLatitude()+"&longitude="+fastLocation.getLongitude()+"&UserName="+Name+"&imie="+telephonyManager.getDeviceId().toString()+"&active_status=t1",ServiceHandler.GET);

            }catch (Exception e){
                error = e.toString();
                e1 = e;
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



        }
    }
   }
