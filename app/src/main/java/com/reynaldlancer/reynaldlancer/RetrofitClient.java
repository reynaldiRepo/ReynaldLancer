package com.reynaldlancer.reynaldlancer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public  static  Retrofit retrofit;
    public  static  final String  base_url = "https://reynaldlancer-api.herokuapp.com";
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(base_url);
            builder.addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }
}
