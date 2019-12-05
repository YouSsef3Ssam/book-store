package com.example.countriesapp.network.model;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.example.countriesapp.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class BookModel {

    @SerializedName("name")
    @Expose
    private String bookName;

    @SerializedName("image")
    @Expose
    private String bookImage;

    @SerializedName("author")
    @Expose
    private String authorName;

    @SerializedName("price")
    @Expose
    private double price;

    public BookModel(String bookName, String authorName, String bookImage, double price) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookImage = bookImage;
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public String getPrice() {
        return price + " USD";
    }

    @BindingAdapter("load_image")
    public static void loadImage(ImageView view, String bookImage) {
        Picasso.get()
                .load(bookImage)
                .placeholder(R.drawable.img_placeholder)
                .into(view);

    }
}
