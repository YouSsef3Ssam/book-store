package com.example.countriesapp.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.countriesapp.R;
import com.example.countriesapp.di.DaggerDIComponent;
import com.example.countriesapp.utils.BookItemClickListner;
import com.example.countriesapp.utils.Paginator;
import com.example.countriesapp.viewmodel.BookViewModel;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BookItemClickListner {

    @BindView(R.id.book_recyclerView)
    RecyclerView bookRecyclerView;
    @BindView(R.id.error_list)
    TextView errorList;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.loadMore_progressBar)
    ProgressBar loadMoreProgressBar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.no_results_found_view)
    View noResultsFoundView;
    @BindView(R.id.loading_view)
    View loadingView;
    @BindView(R.id.end_results_textView)
    TextView endResultsTextView;

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 5;

    private BookViewModel viewModel;

    private Paginator paginator;
    private LinearLayoutManager linearLayoutManager;
    private Parcelable bookRecyclerViewState;

    @Inject
    public BookAdapter bookAdapter;

    public MainActivity() {
        super();
        DaggerDIComponent.create().injectBookAdapter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        paginator = new Paginator(PAGE_NUMBER, PAGE_SIZE);

        //Initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.fetchCountries(paginator.getPageNumber(), paginator.getItemsCount());

        //Initialize RecyclerView and Adapter
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        bookRecyclerView.setLayoutManager(linearLayoutManager);
        bookAdapter.setBookItemClickListner(this);
        bookRecyclerView.setAdapter(bookAdapter);

        //Refresh when scroll
        swipeRefreshLayout.setOnRefreshListener(this);

        //ViewModel Observable
        observeViewModel();

        //When User Start Scroll
        onNestedScrollChange();
    }

    private void observeViewModel() {
        viewModel.books.observe(this, books -> {
            loadingView.setVisibility(View.GONE);
            if (books.isEmpty() && paginator.getPageNumber() == 1) {
                bookAdapter.clearBooks();
                noResultsFoundView.setVisibility(View.VISIBLE);
                endResultsTextView.setVisibility(View.GONE);
            } else {
                noResultsFoundView.setVisibility(View.GONE);
                //Save RecycleView Position before update adapter
                bookRecyclerViewState = bookRecyclerView.getLayoutManager().onSaveInstanceState();
                bookAdapter.updateBooks(books);
                //Restore RecycleView Position after update
                bookRecyclerView.getLayoutManager().onRestoreInstanceState(bookRecyclerViewState);
                if (books.size() < PAGE_SIZE) {
                    paginator.setExtraData(false);
                    endResultsTextView.setVisibility(View.VISIBLE);
                }
            }
            loadMoreProgressBar.setVisibility(View.GONE);
        });

        viewModel.bookLoadError.observe(this, isError -> {
            if (isError != null) {
                if (isError) {
                    errorList.setVisibility(View.VISIBLE);
                    loadMoreProgressBar.setVisibility(View.GONE);
                    loadingView.setVisibility(View.GONE);
                    noResultsFoundView.setVisibility(View.GONE);
                } else {
                    errorList.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        bookAdapter.clearBooks();
        loadingView.setVisibility(View.VISIBLE);
        errorList.setVisibility(View.GONE);
        noResultsFoundView.setVisibility(View.GONE);
        endResultsTextView.setVisibility(View.GONE);
        paginator = new Paginator(PAGE_NUMBER, PAGE_SIZE);
        viewModel.fetchCountries(paginator.getPageNumber(), paginator.getItemsCount());
        swipeRefreshLayout.setRefreshing(false);
    }

    private void onNestedScrollChange() {
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    //Update LinearLayoutManager with every scroll
                    if (scrollY > oldScrollY || scrollY < oldScrollY) {
                        paginator.performChanged(linearLayoutManager);
                    }
                    // when user reach bottom
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        loadMoreProgressBar.setVisibility(View.VISIBLE);
                        if (paginator.loadMore()) {
                            viewModel.fetchCountries(paginator.getPageNumber(), paginator.getItemsCount());
                        }
                        if (!paginator.isExtraData()) {
                            loadMoreProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(String bookName) {
        Toast.makeText(this, bookName, Toast.LENGTH_SHORT).show();
    }
}
