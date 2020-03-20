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

        //final BusesOnTheRoad busesOnTheRoad = new BusesOnTheRoad();

        btn_getCurentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView currentTime = findViewById(R.id.time);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                currentTime.setText(currentDateandTime);
               //busesOnTheRoad.setDate(currentDateandTime);
              // Log.d("date",busesOnTheRoad.getDate().toString());
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

       // busesOnTheRoad.setLat(location.getAltitude());
      //  Log.d("lat", busesOnTheRoad.getLat().toString());
      //  busesOnTheRoad.setLon(location.getLongitude());
      //  Log.d("lon", busesOnTheRoad.getLon().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       // userService = retrofit.create(UserService.class);
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
                        Log.d("d", selectedLine);
                        //busesOnTheRoad.setLine(Integer.parseInt(selectedLine));
                       // Log.d("line", busesOnTheRoad.getLine().toString());
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
                        Log.d("d",selectedBus);

                        //busesOnTheRoad.setBus(Integer.parseInt(selectedBus));
                        //Log.d("line", busesOnTheRoad.getBus().toString());
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
               // updateLine(123,4,46.55090,46.55090,"2020-03-12 12:11");
                updateLine(123);
            }
        });


    }

    public void updateLine(int bus){
       UpdateLine line=new UpdateLine(4,123,"46.55090","24.55590","2020-03-12 12:11");
        Log.d("eler","eler1 " + line.getBus() + line.getDate() + line.getLine() + line.getLat() + line.getLon());

       // userService.updateLine(bus,line,bus,lat,lon,date).enqueue(new Callback<BusesOnTheRoad>() {
       // userService.updateLine(bus,4,123,"46.55090","24.55590","2020-03-12 12:11").enqueue(new Callback<UpdateLine>() {

        //JSONARRAY ?????

       /* Call<JSONArray> callUpdate = userService.updateLine(bus,String.valueOf(datas));
        callUpdate.enqueue(new Callback<JSONArray>() {
            @Override
            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
                Log.d("eler","eler2");

                try {
                    JSONArray val = response.body();
                    Log.d("eler","eler4"+val);

                } catch (Exception e) {
                    e.getMessage();
                    Log.d("eler","eler5");
                }

                if(response.isSuccessful()){

                    Toast.makeText(Signal.this, "Line updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("eler","eler3");
                    Log.d("eler",response.body().toString());

                }
            }

            @Override
            public void onFailure(Call<JSONArray> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Log.d("eler","elererror");
            }
        });
*/

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
