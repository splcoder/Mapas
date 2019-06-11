package com.example.mapas;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import es.dmoral.toasty.Toasty;

// Required:	Tools -> Android SDK -> SDK Tools -> Google Play Services
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnInfoWindowClickListener {

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
	 *
	 *
	 * NOTE: the image was downloaded as a svg >>> then drawable -> new Vector Asset -> from svg (src)
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
		MarkerOptions marker = new MarkerOptions()
				.position( donosti )
				.title( "Ruta maja..." )
				.snippet( "Subida al Ulia" )
				//.icon( BitmapDescriptorFactory.fromResource( R.drawable.ic_beach ) )		// from svg ...
				.icon( BitmapDescriptorFactory.fromResource( R.drawable.beach_32x32 ) )
				.draggable( true );

		mMap.addMarker( marker );
		mMap.moveCamera( CameraUpdateFactory.newLatLng( donosti ) );

		try {
			// Customise the styling of the base map using a JSON object defined in a raw resource file.
			boolean success = mMap.setMapStyle( MapStyleOptions.loadRawResourceStyle( this, R.raw.map_style ) );
			if( ! success )		Log.e( "Map style", "Style parsing failed.");
		} catch (Resources.NotFoundException e) {
			Log.e( "Map style", "Can't find style. Error: ", e);
		}

		mMap.setOnMapClickListener( this );
		mMap.setOnMarkerDragListener( this );
		mMap.setOnInfoWindowClickListener( this );
	}

	@Override
	public void onMapClick(LatLng latLng) {
		mMap.addMarker( new MarkerOptions()
				.position( latLng )
				.title( "New" )
				.snippet( "Pos: " + latLng.latitude + ", " + latLng.longitude )
				.icon( BitmapDescriptorFactory.fromResource( R.drawable.beach_32x32 ) )
		);
		//mMap.moveCamera(CameraUpdateFactory.newLatLng( latLng ) );
		mMap.animateCamera( CameraUpdateFactory.newLatLng( latLng ) );
	}

	@Override
	public void onMarkerDragStart(Marker marker) {
		marker.hideInfoWindow();
	}

	@Override
	public void onMarkerDrag(Marker marker) {
		// Expensive
	}

	@Override
	public void onMarkerDragEnd(Marker marker) {
		LatLng latLng = marker.getPosition();
		marker.setSnippet( "Pos: " + latLng.latitude + ", " + latLng.longitude );
		marker.showInfoWindow();
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Toasty.info( getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT, true ).show();
		Intent intent = new Intent( MapsActivity.this, StreetActivity.class );
		intent.putExtra( "latLong", marker.getPosition() );
		startActivity( intent );
	}
}
