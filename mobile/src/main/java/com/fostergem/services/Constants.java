package com.fostergem.services;

import retrofit.RestAdapter;

/**
 * Created by Neelkamal on 02/11/15.
 */
public class Constants {
    public  static RestAdapter retrofit = new RestAdapter.Builder().setEndpoint("http://api.fostergem.com/v1").build();
    public static final String mypreferecne = "FosterGemData";
}
