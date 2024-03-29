package com.example.mapas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class LocalizacionActivity extends AppCompatActivity implements
		GoogleMap.OnMyLocationButtonClickListener,
		GoogleMap.OnMyLocationClickListener,
		OnMapReadyCallback,
		ActivityCompat.OnRequestPermissionsResultCallback{


	private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
	private boolean mPermissionDenied = false;

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localizacion);


		SupportMapFragment mapFragment =
				(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap map) {
		mMap = map;

		mMap.setOnMyLocationButtonClickListener(this);
		mMap.setOnMyLocationClickListener(this);
		enableMyLocation();
	}

	/**
	 * Enables the My Location layer if the fine location permission has been granted.
	 */
	private void enableMyLocation() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {
			// Permission to access the location is missing.
			PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
					Manifest.permission.ACCESS_FINE_LOCATION, true);
		} else if (mMap != null) {
			// Access to the location has been granted to the app.
			mMap.setMyLocationEnabled(true);
		}
	}

	@Override
	public boolean onMyLocationButtonClick() {
		Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
		// Return false so that we don't consume the event and the default behavior still occurs
		// (the camera animates to the user's current position).
		return false;
	}

	@Override
	public void onMyLocationClick(@NonNull Location location) {
		Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
										   @NonNull int[] grantResults) {
		if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
			return;
		}

		if (PermissionUtils.isPermissionGranted(permissions, grantResults,
				Manifest.permission.ACCESS_FINE_LOCATION)) {
			// Enable the my location layer if the permission has been granted.
			enableMyLocation();
		} else {
			// Display the missing permission error dialog when the fragments resume.
			mPermissionDenied = true;
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		if (mPermissionDenied) {
			// Permission was not granted, display error dialog.
			showMissingPermissionError();
			mPermissionDenied = false;
		}
	}

	/**
	 * Displays a dialog with error message explaining that the location permission is missing.
	 */
	private void showMissingPermissionError() {
		PermissionUtils.PermissionDeniedDialog
				.newInstance(true).show(getSupportFragmentManager(), "dialog");
	}

}
