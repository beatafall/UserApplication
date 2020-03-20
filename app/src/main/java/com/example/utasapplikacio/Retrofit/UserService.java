package com.example.utasapplikacio.Retrofit;

import android.content.Intent;

import com.example.utasapplikacio.Class.Bus;
import com.example.utasapplikacio.Class.BusesOnTheRoad;
import com.example.utasapplikacio.Class.Line;
import com.example.utasapplikacio.Class.LineStations;
import com.example.utasapplikacio.Class.MessageType;
import com.example.utasapplikacio.Class.Messages;
import com.example.utasapplikacio.Class.UpdateLine;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    String BASE_URL = "http://192.168.0.126:45455/api/";

    @GET("line")
    Call<List<Line>> getLines();

    @GET("message")
    Call<List<Messages>> getMessages();

    @GET("messageType")
    Call<List<MessageType>> getMessageType();

    @GET("LineStation")
    Call<List<LineStations>> getLineStations();

    @GET("busesontheroad")
    Call<List<BusesOnTheRoad>> getBuses();

    @GET("allbus")
    Call<List<Bus>> getAllBuses();

    @POST("message/")
    @FormUrlEncoded
    Call<Messages> sendMessage(@Field("jelzesId") String messageTypeId, @Field("vonalId") String line,
                               @Field("datum") String date, @Field("lon") String lon, @Field("lat") String lat);

    @FormUrlEncoded
    @PUT("busesontheroad/{buszId}/")
    Call<UpdateLine> updateLine(@Path("buszId") String id, @Field("vonalId") String line,
                                @Field("buszId") String bus, @Field("lon") Double lon,
                                @Field("lat") Double lat, @Field("datum") String date);

}