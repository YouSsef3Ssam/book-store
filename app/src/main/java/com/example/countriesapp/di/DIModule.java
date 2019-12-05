package com.example.countriesapp.di;

import com.example.countriesapp.network.api.APIClient;
import com.example.countriesapp.network.api.APIConstants;
import com.example.countriesapp.network.service.APIServices;
import com.example.countriesapp.utils.BookItemClickListner;
import com.example.countriesapp.view.BookAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DIModule {
    @Provides
    public APIServices getServiceProvider() {
        return new Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(APIServices.class);
    }

    @Provides
    public APIClient getClientProvider(){
        return APIClient.getInstance();
    }

    @Provides
    public BookAdapter getBookAdapterProvider(){
        return new BookAdapter(new ArrayList<>());
    }
}
