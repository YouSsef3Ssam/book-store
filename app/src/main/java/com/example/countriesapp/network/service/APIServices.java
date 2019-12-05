package com.example.countriesapp.network.service;

import com.example.countriesapp.network.api.APIConstants;
import com.example.countriesapp.network.model.BookModel;
import java.util.List;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {

    @GET(APIConstants.GET_BOOKS)
    Single<List<BookModel>> getCounties(@Query("pageNumber") int pageNumber,
                                        @Query("pageSize") int pageSize);
}
