package com.example.countriesapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countriesapp.R;
import com.example.countriesapp.databinding.BookItemBinding;
import com.example.countriesapp.network.model.BookModel;
import com.example.countriesapp.utils.BookItemClickListner;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookHolder> {

    private List<BookModel> books;
    private BookItemClickListner listner;

    public BookAdapter(List<BookModel> books) {
        this.books = books;
    }

    public void setBookItemClickListner(BookItemClickListner listner){
        this.listner = listner;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.book_item, parent, false);
        return new BookHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        holder.bind(books.get(position));
        holder.itemView.setOnClickListener(view ->
                listner.onItemClick(books.get(position).getBookName())
        );
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void updateBooks(List<BookModel> newCountries) {
        books.addAll(newCountries);
    }

    public void clearBooks() {
        books.clear();
        notifyDataSetChanged();
    }
}
