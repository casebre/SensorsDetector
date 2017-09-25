package ca.casebre.sensorsdetection;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtGps, txtPowerSavingMode;
    private Button btnRefresh;
    private PowerManager powerManager;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPowerSavingMode = (TextView) findViewById(R.id.text_view_power_saving);
        txtGps = (TextView) findViewById(R.id.text_view_gps);
    }

    @Override
    public void onStart() {
        super.onStart();
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        getPowerSavingStatus();
        getGpsStatus();
    }

    public void getPowerSavingStatus() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (powerManager.isPowerSaveMode())
                txtPowerSavingMode.setText("Power Saving Mode is ON");
            else
                txtPowerSavingMode.setText("Power Saving is OFF");
        } else {
            final String result = Settings.System.getString(getContentResolver(), "psm_switch");
            Log.v("Debug", "Power saving active: " + result);
        }


    }

    public void getGpsStatus() {
        try {
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                txtGps.setText("GPS is ON");
            else
                txtGps.setText("GPS is OFF");
        } catch(Exception ex) {
            txtGps.setText("GPS is OFF");
        }


    }
}
