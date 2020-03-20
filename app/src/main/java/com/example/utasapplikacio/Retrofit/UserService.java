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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    String BASE_URL = "http://webtraffic.conveyor.cloud/api/";

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

//    @FormUrlEncoded
//    @PUT("busesontheroad/{buszId}/")
//    Call<UpdateLine> updateLine(@Path("buszId") int id, @Field("vonalId") int line,@Field("buszId") int bus,
//                                    @Field("lon") String lon,@Field("lat") String lat,
//                                    @Field("datum") String date);

    //@FormUrlEncoded
   // @PUT("busesontheroad/{buszId}")
   // Call<UpdateLine> updateLine(@Path("buszId") int id, String json);

    //string, jsonarray,updateline ???
//     @FormUrlEncoded
//     @PUT("busesontheroad/{buszId}")
//     Call<String> updateLine(@Path("buszId") int id, @Field("vonalId") int line, @Field("buszId") int bus,
//                                @Field("lon") String lon, @Field("lat") String lat,
//                                @Field("datum") String date);

    @FormUrlEncoded
    @PUT("busesontheroad/{buszId}")
    Call<JSONArray> updateLine(@Path("buszId") int id, @Body String data);
}