package com.example.alexandre.baywatcher;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity1 extends SupportMapFragment implements OnMapReadyCallback,GoogleMap.OnMapClickListener, LocationListener,GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    String address;
    private LocationManager locationManager;
    Geocoder geocoder;
    List<Address> addresses;
    Location loc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.activity_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

        @Override
    public void onMapReady(GoogleMap googleMap) {
            try {
                locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

                mMap = googleMap;

                mMap.setOnMapClickListener(this);

                //mMap.setMyLocationEnabled(true);
            } catch (SecurityException ex) {
                Log.e("e", "Error", ex);
            }
        }

    @Override
    public void onMapClick(LatLng latLng) {

        double lat = latLng.latitude;
        double lng = latLng.longitude;

        //Toast.makeText(getContext(),"Lat"+lat, Toast.LENGTH_LONG).show();


        LatLng sydney = new LatLng(lat, lng);

        mMap.setOnMyLocationChangeListener(null);
        mMap.clear();

        CameraPosition updateMinhaCasa = new CameraPosition(sydney, 15, 0, 0);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(updateMinhaCasa), 3000, null);

        Marker meuMarcadorMarker = mMap.addMarker(new MarkerOptions().position(sydney));

        meuMarcadorMarker.getPosition();
        meuMarcadorMarker.setTitle("Hoje parece não ser um otimo dia para ir a Praia.");
        meuMarcadorMarker.setSnippet("Click para saber mais ...");

        meuMarcadorMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_info_red_800_24dp));
        meuMarcadorMarker.showInfoWindow();

        mMap.setOnInfoWindowClickListener(this);
        meuMarcadorMarker.getPosition();

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getContext(), "A superexposição aos raios ultravioletas causa muitos " +
                "prejuízos ao ser humano. Além dele ser emitido pelo Sol," +
                " também é proveniente de lâmpadas e câmaras de bronzeamento artificial. Portanto, é aconselhável entender " +
                "o que os efeitos nocivos desses raios podem causar ao nosso corpo em qualquer época do ano.", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
