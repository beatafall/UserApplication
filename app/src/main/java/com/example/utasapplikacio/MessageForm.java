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
import com.example.utasapplikacio.Class.Line;
import com.example.utasapplikacio.Class.MessageType;
import com.example.utasapplikacio.Class.Messages;
import com.example.utasapplikacio.Retrofit.ApiUtils;
import com.example.utasapplikacio.Retrofit.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageForm extends AppCompatActivity implements LocationListener {

    TextView message, line, lat, lon, dateAndTime;
    Button btn_sendMessage;
    private LocationManager locationManager;
    UserService service;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_form);

        line=findViewById(R.id.editTextLine);
        lat=findViewById(R.id.lat4);
        lon=findViewById(R.id.lon4);
        dateAndTime=findViewById(R.id.dateandtime);
        btn_sendMessage=findViewById(R.id.send);
        message=findViewById(R.id.message2);

        service = ApiUtils.getAPIService();

        final Messages newMessage =  new Messages();

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            message.setText(getIntent().getStringExtra("uzenet"));
        }

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

        service.getMessageType().enqueue(new Callback<List<MessageType>>() {
            @Override
            public void onResponse(Call<List<MessageType>> call, Response<List<MessageType>> response) {
                List<MessageType> messageTypes = response.body();
                for (MessageType m : messageTypes) {
                    final String messageTypeName,messageTypeId;
                    messageTypeName = getIntent().getStringExtra("uzenet");
                    if(m.getMessageTypeName().equals(messageTypeName)){
                        messageTypeId = m.getMessageTypeId();
                        newMessage.setMessageTypeId(messageTypeId.trim());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MessageType>> call, Throwable t) {
                Toast.makeText(MessageForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMessage.setLine(line.getText().toString().trim());
                newMessage.setDate(dateAndTime.getText().toString().trim());
                newMessage.setLat(lat.getText().toString().trim());
                newMessage.setLon(lon.getText().toString().trim());

                sendPost(newMessage.getMessageTypeId(),
                        newMessage.getLine(),newMessage.getDate(),
                        newMessage.getLon(),newMessage.getLat());
            }
        });


    }

    public void getCurrentTime(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        String currentDateandTime = sdf.format(new Date());
        dateAndTime.setText(currentDateandTime);
    }

    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        lat.setText(""+latitude);
        lon.setText(""+longitude);
    }

    public void sendPost(String messageType, String line, String date, String lon, String lat) {
        //service = ApiUtils.getAPIService();
        service.sendMessage(messageType,line,date,lon,lat).enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Log.d("successful","successful");

                if(!response.isSuccessful()) {
                    Log.d("not succesful",response.message());
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Toast.makeText(MessageForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("not successful","not successful");
            }
        });
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
