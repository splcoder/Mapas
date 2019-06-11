package com.example.mapas;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StreetActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

	//private GoogleMap mMap;
	private StreetViewPanorama streetViewPanorama;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_street);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		/*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);*/

		StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById( R.id.fragmentStreet );
		streetViewPanoramaFragment.getStreetViewPanoramaAsync( this );
	}

	// Deleted onMapReady()

	@Override
	public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
		this.streetViewPanorama = streetViewPanorama;
		//LatLng donosti = new LatLng(43.327929, -1.959019 );
		//streetViewPanorama.setPosition( donosti );
		Intent intent = getIntent();
		LatLng coord = (LatLng) intent.getExtras().get( "latLong" );
		streetViewPanorama.setPosition( coord );
	}
}
