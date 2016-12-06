package triton.com.sqlitedemo.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import triton.com.sqlitedemo.R;
import triton.com.sqlitedemo.utils.ConstantFiles;
import triton.com.sqlitedemo.utils.ServiceHandler;
import triton.com.sqlitedemo.utils.Singleton;
import triton.com.sqlitedemo.utils.URLs;

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GcmIntentService extends IntentService {
    public static final String MyPREFERENCESUSER = "GCM" ;
    public static final String Gcmkey = "GcmKey";
    SharedPreferences sharedpreferencegcmkey;

    static String TAG = "GcmIntentService";
    String token;

    public GcmIntentService(String name) {
        super(name);
    }

    public GcmIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        generateGCM();
    } // onHandleIntent

    public void generateGCM() {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            sharedpreferencegcmkey = getSharedPreferences(MyPREFERENCESUSER, Context.MODE_PRIVATE);
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            SharedPreferences.Editor editoruser = sharedpreferencegcmkey.edit();
            editoruser.putString(Gcmkey, token);
            editoruser.commit();
            Log.i("Your GCM ID", token);
            // store your GCM to the database
            if(Singleton.getUtils().isNetworkAvailable(GcmIntentService.this)){
              //  new SendGcmKey().execute();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Your GCM ID", token);
            Log.i("ERROR", ""+e);
        }
    } // generateGCM

   /* public class SendGcmKey extends AsyncTask<Void,Void,String> {

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        String response,status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            ServiceHandler sh = new ServiceHandler();
            list.add(new BasicNameValuePair("driver_id", ConstantFiles.DRIVER_ID));
            list.add(new BasicNameValuePair("registration_id", token));
            list.add(new BasicNameValuePair("serverkey", "AIzaSyDZOGKZUU2WkxAX3NsjXXSl_0TTELYcXUE"));

            try {
                response = sh.makeServiceCall(URLs.SEND_GCMKEY, ServiceHandler.POST, list);
                JSONObject jsonObject = new JSONObject(response);
                //sstatus=jsonObject.getInt("success");

                JSONArray array = jsonObject.getJSONArray("message");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject1 = array.getJSONObject(i);
                    status = jsonObject1.getString("status");

                }
            } catch (Exception e) {

            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (response != null) {
                if (status.equals("1")) {
                    Log.i("Your GCM response", response+token);
                    //Toast.makeText(getApplicationContext(), "Key send", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Something wen't wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Server not responding", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

}
