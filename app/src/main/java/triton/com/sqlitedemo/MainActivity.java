package triton.com.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import triton.com.sqlitedemo.db.FeedReaderContract;
import triton.com.sqlitedemo.db.FeedReaderDbHelper;
import triton.com.sqlitedemo.utils.ServiceHandler;

public class MainActivity extends AppCompatActivity {
String Nameval;
    String movilenumber;
static Context context1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context1 = getApplicationContext();
        Button button2 = (Button)findViewById(R.id.button2);
        final EditText mobile = (EditText)findViewById(R.id.mobile);
        final EditText name = (EditText)findViewById(R.id.name);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nameval = name.getText().toString();
                movilenumber = mobile.getText().toString();
                SharedPreferences pref=getSharedPreferences("Driver",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();

                editor.putString("name",Nameval);
                editor.putString("mobile",movilenumber);


                editor.commit();
                Intent nextActivity = new Intent(getApplicationContext(),AfterAnimation.class);
                //startActivity(nextActivity);
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                finish();
                //push from bottom to top
                overridePendingTransition(R.anim.zoom_exit, R.anim.zoom_enter);
            }
        });
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getApplicationContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "Name: ");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "Vishnu Vigneshwar. M");

// Insert the new row, returning the primary key value of the new row
       // long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
      //  Toast.makeText(this, "newRowId "+newRowId, Toast.LENGTH_SHORT).show();
        SQLiteDatabase db2 = mDbHelper.getReadableDatabase();
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };

// Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "Name: " };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor c = db2.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(c.getCount() > 0)
        {
            c.moveToFirst();

            do {
                long itemId = c.getLong(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
                String itemvaln = c.getString(c.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));  //c.getLong( c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID) );
                String itemvalv = c.getString(c.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));  //c.getLong( c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID) );
              /*  Toast.makeText(this, "value db " + itemId, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "value db " + itemId, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "itemval title " + itemvaln, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "itemval sub title " + itemvalv, Toast.LENGTH_SHORT).show();*/
            } while (c.moveToNext());

        }
        else{
            /*Toast.makeText(this, "No Data in Db", Toast.LENGTH_SHORT).show();*/
        }




        getRegId();
        SharedPreferences sharedPreferences = getSharedPreferences("Driver", MODE_PRIVATE);
        String Name = sharedPreferences.getString("name", "");
        String Mobile = sharedPreferences.getString("mobile", "");

        if(!Mobile.equals(""))
        {
            Intent nextActivity = new Intent(getApplicationContext(),AfterAnimation.class);
            //startActivity(nextActivity);
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            finish();
        }

    }


    public void getRegId(){

        AsyncTask<Void, Void, String> gcm = new AsyncTask<Void, Void, String>() {
            String msg = "";
            String Content = null;
            @Override
            protected String doInBackground(Void... params) {


                try {
                    GoogleCloudMessaging gcm = null;
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    String regid = gcm.register("728802042803");
                    /*
                    * AIzaSyA1D2WR7u1yLLsOZvJx-xv1WoNAujji4Z4
                    * */
                    msg = regid;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                SharedPreferences sharedPreferences = getSharedPreferences("Driver", MODE_PRIVATE);
                String Name = sharedPreferences.getString("name", "");
                String Mobile = sharedPreferences.getString("mobile", "");

                String api = "AAAAqa_8o7M:APA91bE3Yip8el2mdNdiC_hyyhYsxmXiMIeFpl2PEOGCHjhY8Z7lPmi1KxcBI_FdpMxJjQWWZfgqWxNjlHESJfgsSAWKYvM1oKzeh8CusJaIy_U-qoKYdlVEpHZZXa_1US8qUlEhRwgJOEhOmb7UVQkrjG2y--uEBQ";
                ServiceHandler sh=new ServiceHandler();
//telephonyManager.getDeviceId().toString()
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
              //String response=sh.makeServiceCall("http://whitehousehub.in/demo/mototaxo/MotoUrl/trackeveryonetoken.php?token="+msg+"&api="+api+"&did="+Mobile+"&imie="+telephonyManager.getDeviceId().toString()+"&active_status="+Name, ServiceHandler.GET);
               String response=sh.makeServiceCall("http://jbugas.com/url/trackeveryonetoken.php?token="+msg+"&api="+api+"&did="+Mobile+"&imie="+telephonyManager.getDeviceId().toString()+"&active_status="+Name, ServiceHandler.GET);
                if(!Mobile.equals(""))
                {
                    Intent nextActivity = new Intent(getApplicationContext(),AfterAnimation.class);
                    //startActivity(nextActivity);
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    finish();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.v(" gcm token",msg + "\n");
                //Toast.makeText(MainActivity.this, "token is"+msg, Toast.LENGTH_SHORT).show();
               // new LoginOperation().execute();
            }
        }.execute(null, null, null);

    }

}
