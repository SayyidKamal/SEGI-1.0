package com.example.segi10.Fragments;

import com.example.segi10.Notification.MyResponse;
import com.example.segi10.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIservice {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAEdCms-Y:APA91bHGwfE64a696OKi6dgQePle3ZRmoCDviqYk3HuBU80NEzQ2RcZ6HT-BRVSc9eqzgejHNfcJj8OWGrYTvxPgqlBqQOIV6qLYr9FnUIxy5CY3raUpFo0x2B6HGF5cHVhCwwocW9Pn"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
