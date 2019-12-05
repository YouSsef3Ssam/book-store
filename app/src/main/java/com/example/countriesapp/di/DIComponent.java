package com.example.countriesapp.di;

import com.example.countriesapp.network.api.APIClient;
import com.example.countriesapp.utils.BookItemClickListner;
import com.example.countriesapp.view.BookAdapter;
import com.example.countriesapp.view.MainActivity;
import com.example.countriesapp.viewmodel.BookViewModel;
import dagger.Component;

@Component(modules = DIModule.class)
public interface DIComponent {
    void injectViewModel(BookViewModel viewModel);
    void injectClient(APIClient client);
    void injectBookAdapter(MainActivity activity);
}
