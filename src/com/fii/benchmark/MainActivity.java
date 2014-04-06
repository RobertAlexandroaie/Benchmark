package com.fii.benchmark;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements SensorEventListener, LocationListener {
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
	for (Sensor s : sensorList) {
	    Log.e("INFO", s.getName() + " " + s.getVendor() + " " + s.getType());
	}
	LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	boolean gps = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
	boolean net = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	Log.e("GPS", "GPS:" + String.valueOf(gps) + " Net:" + String.valueOf(net));
	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    @Override
    public void onAccuracyChanged(Sensor s, int value) {
	Log.e("AC-CH", "Sensor = " + s.getName());
	Log.e("AC-CH", "Vendor = " + s.getVendor());
	Log.e("AC-CH", "Version = " + String.valueOf(s.getVersion()));
	Log.e("AC-CH", "Value = " + String.valueOf(value));
    }

    @Override
    public void onSensorChanged(SensorEvent s) {
	Log.e("S-CH", "Sensor Changed");
	Log.e("S-CH", "Sensor = " + s.sensor.getName());
	String values = "";
	for (int tr = 0; tr < s.values.length; tr++)
	    values += String.format("%f,", s.values[tr]);
	Log.v("S-CH", "Values = " + values);
    }

    @Override
    public void onLocationChanged(Location l) {
	// TODO Auto-generated method stub
	Log.e("L-CH", String.format("Location changed: Long:%f, Lat:%f", (float) l.getLongitude(), (float) l.getLatitude()));
    }

    @Override
    public void onProviderDisabled(String txt) {
	Log.e("P-DIS", String.format("Provider disabled: %s", txt));
    }

    @Override
    public void onProviderEnabled(String txt) {
	Log.e("P-EN", String.format("Provider enabled: %s", txt));
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	Log.e("STS-CH", "Status Changed");
    }

}
