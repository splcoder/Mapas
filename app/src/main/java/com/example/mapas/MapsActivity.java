package com.example.mapas;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

// Required:	Tools -> Android SDK -> SDK Tools -> Google Play Services
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}


	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );
		//mMap.getUiSettings().setZoomControlsEnabled( true );	<<< Set in the xml

		// Add a marker in Sydney and move the camera
		/*LatLng sydney = new LatLng(-34, 151 );
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
		//43.327929, -1.959019
		LatLng donosti = new LatLng(43.327929, -1.959019 );
		mMap.addMarker( new MarkerOptions().position( donosti ).title( "Ruta maja..." ).snippet( "Subida al Ulia" ) );
		mMap.moveCamera( CameraUpdateFactory.newLatLng( donosti ) );

		try {
			// Customise the styling of the base map using a JSON object defined in a raw resource file.
			boolean success = mMap.setMapStyle( MapStyleOptions.loadRawResourceStyle( this, R.raw.map_style ) );
			if( ! success )		Log.e( "Map style", "Style parsing failed.");
		} catch (Resources.NotFoundException e) {
			Log.e( "Map style", "Can't find style. Error: ", e);
		}
	}
}
