package com.example.countriesapp.network.api;

import android.util.Log;

import com.example.countriesapp.di.DaggerDIComponent;
import com.example.countriesapp.network.model.BookModel;
import com.example.countriesapp.network.service.APIServices;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Single;

public class APIClient {

    private static APIClient instance;

    @Inject
    public APIServices api;

    private APIClient() {
        DaggerDIComponent.create().injectClient(this);
    }

    public static APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();

        }
        return instance;
    }

    public Single<List<BookModel>> getCountries(int pageNumber, int pageSize) {
        return api.getCounties(pageNumber, pageSize);
    }
}
