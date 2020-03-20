package com.example.utasapplikacio;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utasapplikacio.Class.Bus;
import com.example.utasapplikacio.Class.BusesOnTheRoad;
import com.example.utasapplikacio.Class.Line;
import com.example.utasapplikacio.Class.UpdateLine;
import com.example.utasapplikacio.Retrofit.ApiUtils;
import com.example.utasapplikacio.Retrofit.UserService;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signal extends AppCompatActivity implements LocationListener, AdapterView.OnItemSelectedListener {

    Spinner line,bus;
    TextView lat, lon;
    Button btn_getCurentTime, btn_update;
    String selectedBus, selectedLine;
    private LocationManager locationManager;
    UserService userService;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        line = findViewById(R.id.spinnerLines2);
        bus = findViewById(R.id.spinnerbus);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        btn_getCurentTime = findViewById(R.id.btn_time);
        btn_update = findViewById(R.id.update);

        final UpdateLine updateLine = new UpdateLine();

        btn_getCurentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView currentTime = findViewById(R.id.time);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                currentTime.setText(currentDateandTime);
                updateLine.setDate(currentDateandTime);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

        updateLine.setLat(location.getLongitude());
        updateLine.setLon(location.getLatitude());

        userService= ApiUtils.getAPIService();

        Call<List<Line>> call = userService.getLines();
        call.enqueue(new Callback<List<Line>>() {
            @Override
            public void onResponse(Call<List<Line>> call, Response<List<Line>> response) {
                List<Line> lines = response.body();

                List<String> lineString = new ArrayList<String>();

                for (Line l : lines) {
                    lineString.add(l.getLine().toString());
                }

                line.setOnItemSelectedListener(Signal.this);
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(Signal.this, android.R.layout.simple_spinner_dropdown_item, lineString);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                line.setAdapter(adapter);

                line.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedLine = line.getSelectedItem().toString();
                        updateLine.setLine(selectedLine);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getApplicationContext(), "Nem valasztotta ki a buszt!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable t) {
                Toast.makeText(Signal.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Bus>> callBuses = userService.getAllBuses();
        callBuses.enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                List<Bus> buses = response.body();

                List<String> busString = new ArrayList<String>();

                for (Bus l : buses) {
                    busString.add(l.getBus().toString());
                }

                bus.setOnItemSelectedListener(Signal.this);
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(Signal.this, android.R.layout.simple_spinner_dropdown_item, busString);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bus.setAdapter(adapter);

                bus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBus=bus.getSelectedItem().toString();
                        updateLine.setBus(selectedBus);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getApplicationContext(), "Nem valasztotta ki a buszt!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(Signal.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               updateLine(updateLine.getBus(),updateLine.getLine(),updateLine.getLon(),updateLine.getLat(),updateLine.getDate());
            }
        });


    }

    public void updateLine(String bus,String line, Double lon, Double lat, String date){

       userService.updateLine(bus,line,bus, lon, lat, date).enqueue(new Callback<UpdateLine>() {
            @Override
            public void onResponse(Call<UpdateLine> call, Response<UpdateLine> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Signal.this, "Sikeresen frissítette a busz helyzetét!", Toast.LENGTH_SHORT).show();
                    Log.d("successful","successful");
                }
            }

            @Override
            public void onFailure(Call<UpdateLine> call, Throwable t) {
                Toast.makeText(Signal.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("not successful","not successful");
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        lat.setText(""+latitude);
        lon.setText(""+longitude);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
