package com.example.utasapplikacio.Retrofit;

import com.example.utasapplikacio.Class.Line;
import com.example.utasapplikacio.Class.MessageType;
import com.example.utasapplikacio.Class.Messages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    String BASE_URL = "http://webtraffic.conveyor.cloud/api/";

    @GET("line")
    Call<List<Line>> getLines();

    @GET("message")
    Call<List<Messages>> getMessages();

    @GET("messageType")
    Call<List<MessageType>> getMessageType();
}
