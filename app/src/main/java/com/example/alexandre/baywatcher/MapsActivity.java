package com.example.alexandre.baywatcher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class MapsActivity extends Fragment implements GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    int op = 0;
    int insolacao ;

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
        mMap = googleMap;
        double lat = 0.0, longi = 0.0;

        LocationManager lm = (LocationManager) this.getContext().getSystemService(this.getContext().LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            String provider = lm.getBestProvider(criteria, true);
            myLocation = lm.getLastKnownLocation(provider);
        }

        lat = myLocation.getLatitude();
        longi = myLocation.getLongitude();
        LatLng sydney = new LatLng(lat, longi);

        mMap.setOnMyLocationChangeListener(null);
        mMap.clear();

        long date = myLocation.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String dateString = sdf.format(date);

        Date tp = null;
        try {
            tp = sdf.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(tp);

        double  n = calendario.get(Calendar.DAY_OF_YEAR);
        int  n2 = calendario.get(Calendar.HOUR_OF_DAY);


            CameraPosition updateMinhaCasa = new CameraPosition(sydney, 15, 0, 0);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(updateMinhaCasa), 3000, null);

            Marker meuMarcadorMarker = mMap.addMarker(new MarkerOptions().position(sydney));

            meuMarcadorMarker.getPosition();

        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        Date date2 = Calendar.getInstance().getTime();
        int hora = date2.getHours();

        if(hora > 6 || hora < 18){//Tentar colocar a API.

            double h = (hora -12)*15*(1/hora);

            double s = 23.45 * Math.sin((360.0*(n-80.0)/365));

            double z = ( Math.sin(lat)*Math.sin(s) ) + ( Math.cos(lat)*Math.cos(s)*Math.cos(h) );

            double insolacao = s*Math.cos(z);
            Toast.makeText(getContext(),""+insolacao, Toast.LENGTH_LONG).show();

            if(insolacao >= 13.0 && insolacao < 14.0){
                meuMarcadorMarker.setTitle("Hoje não e um bom dia para ir a Praia..");
                meuMarcadorMarker.setSnippet("Click para saber mais ...");

                meuMarcadorMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_info_red_800_24dp));
                meuMarcadorMarker.showInfoWindow();


                mMap.setOnInfoWindowClickListener(this);
                meuMarcadorMarker.getPosition();
                op = 1;
            }else if(insolacao >= 14.0 && insolacao < 15.0){
                meuMarcadorMarker.setTitle("Hoje não e um bom dia para ir a Praia.");
                meuMarcadorMarker.setSnippet("Click para saber mais ...");

                meuMarcadorMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_info_red_800_24dp));
                meuMarcadorMarker.showInfoWindow();


                mMap.setOnInfoWindowClickListener(this);
                meuMarcadorMarker.getPosition();
                op = 2;

            }else if(insolacao >= 15.0){
                meuMarcadorMarker.setTitle("Hoje não e um bom dia para ir a Praia.");
                meuMarcadorMarker.setSnippet("Click para saber mais ...");

                meuMarcadorMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_info_red_800_24dp));
                meuMarcadorMarker.showInfoWindow();


                mMap.setOnInfoWindowClickListener(this);
                meuMarcadorMarker.getPosition();
                op = 3;

            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if(op == 1){
            Toast.makeText(getContext(), "Os filtros solares são produtos capazes de proteger nossa pele dos raios ultravioleta evitando assim o " +
                    "fotoenvelhecimento e o desenvolvimento de câncer de pele. Nivel de Insolação: Médio.", Toast.LENGTH_LONG).show();
        }else if(op == 2){
            Toast.makeText(getContext(), "O câncer da pele é causado pela radiação solar excessiva. Procure semprer ir a praia e fazer passeios" +
                    "antes das 2P pm. Nivel de Insolação: Médio", Toast.LENGTH_LONG).show();
        }else if(op == 3){
            Toast.makeText(getContext(), "O câncer da pele é causado pela radiação solar excessiva. Procure semprer ir a praia e fazer passeios" +
                    "antes das 2P pm. Nivel de Insolação: Alto", Toast.LENGTH_LONG).show();
        }
    }
}

