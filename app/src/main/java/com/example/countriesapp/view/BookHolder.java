package com.example.countriesapp.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.countriesapp.databinding.BookItemBinding;
import com.example.countriesapp.network.model.BookModel;

public class BookHolder extends RecyclerView.ViewHolder {

    private BookItemBinding itemBinding;

    public BookHolder(@NonNull BookItemBinding itemBinding) {
        super(itemBinding.getRoot());
        this.itemBinding = itemBinding;
    }

    public void bind(BookModel bookModel) {
        itemBinding.setBookModel(bookModel);
    }
}
