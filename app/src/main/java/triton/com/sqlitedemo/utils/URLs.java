package triton.com.sqlitedemo.utils;

/**
 * Created by TritonDev on 22/3/2016.
 */
public class URLs {
    public static final String BASE_URL="http://whitehousehub.in/demo/mototaxo/MotoUrl/";

    public static final String OLD_BASE_URL="http://blocksnwings.com/";

    public static final String DRIVER_LOGIN=BASE_URL+"driver_login.php";//CHANGED
    public static final String DRIVER_LOGOUT=BASE_URL+"driver_offline.php";//CHANGED
    public static final String DRIVER_PROFILE=BASE_URL+"EasyRider/driver_profile_summary";
    public static final String SEND_LATLNG_AND_DRIVER=BASE_URL+"driver_lat_long.php";
    public static final String SEND_GCMKEY=BASE_URL+"driver_gcm.php";//CHANGED
    public static final String START_TRIP=BASE_URL+"driver_start1.php";
    public static final String STOP_TRIP=BASE_URL+"driver_stop1.php";

    public static final String DRIVER_CONFIRM=BASE_URL+"driver_accept.php";
    public static final String DRIVER_ARRIVED=BASE_URL+"EasyRider/driver_arrieved";
    public static final String DRIVER_RIDECANCEL=BASE_URL+"driver_rejecttrip.php";
    public static final String DRIVER_TRIP_SUMMARY=BASE_URL+"EasyRider/driver_trip_summary";
    public static final String DRIVER_TRIP_ACCOUNT=BASE_URL+"EasyRider/driver_paid_history";
    public static final String DRIVER_TRIP_CANCEL=BASE_URL+"driver_cancel_booking.php";

    public static final String DRIVER_STATUS_CHANGE=BASE_URL+"drivercurrentstatus.php";


    //public static final String SEND_GCMTOKEN="http://tritonitsolutions.in/demo/gcm/register.php?regId=";
}
