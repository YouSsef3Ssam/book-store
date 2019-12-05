package com.example.countriesapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.countriesapp.network.model.BookModel;
import com.example.countriesapp.network.service.APIServices;
import com.example.countriesapp.viewmodel.BookViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class BookViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    APIServices apiServices;

    @InjectMocks
    BookViewModel bookViewModel = new BookViewModel();

    private Single<List<BookModel>> testSingle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getBooksSuccess() {
        BookModel bookModel = new BookModel("Name", "Author", "Image", 14.0);
        ArrayList<BookModel> books = new ArrayList<>();
        books.add(bookModel);

        testSingle = Single.just(books);

        Mockito.when(apiServices.getCounties(1, 1)).thenReturn(testSingle);

        bookViewModel.fetchCountries(1, 1);

        Assert.assertEquals(1, bookViewModel.books.getValue().size());
        Assert.assertEquals(false, bookViewModel.bookLoadError.getValue());
    }

    @Test
    public void getBooksFailure() {

        testSingle = Single.error(new Throwable());

        Mockito.when(apiServices.getCounties(-1, 1)).thenReturn(testSingle);
        bookViewModel.fetchCountries(-1, 1);

        Assert.assertEquals(true, bookViewModel.bookLoadError.getValue());
    }

    @Before
    public void setupRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(runnable -> {
                    runnable.run();
                }, true);
            }
        };

        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);
    }
}
