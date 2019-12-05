package com.example.countriesapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countriesapp.di.DaggerDIComponent;
import com.example.countriesapp.network.api.APIClient;
import com.example.countriesapp.network.model.BookModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BookViewModel extends ViewModel {

    public MutableLiveData<List<BookModel>> books = new MutableLiveData<>();
    public MutableLiveData<Boolean> bookLoadError = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public APIClient apiClient;

    public BookViewModel() {
        super();
        DaggerDIComponent.create().injectViewModel(this);
    }

    public void fetchCountries(int pageNumber, int pageSize) {
        disposable.add(
                apiClient.getCountries(pageNumber, pageSize)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<BookModel>>() {
                            @Override
                            public void onSuccess(List<BookModel> Countries) {
                                books.setValue(Countries);
                                bookLoadError.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                bookLoadError.setValue(true);
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
