package com.example.newspaper.model;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.newspaper.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
@Entity(tableName = "news_table")
public class News {
    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "news_id")
    @PrimaryKey
    @NonNull
    private String id;  //id is already given by api so no need of autogenerate
    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "news_title")
    private String title;
    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "news_description")
    private String description;
    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "news_url")
    private String url;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "news_image")
    private String image;
    @BindingAdapter("image")
    public static void loadImage(ImageView imageView, String imageUrl){
        if(imageUrl != null && (imageUrl.trim().isEmpty() || imageUrl.equalsIgnoreCase("None"))){
            imageUrl = null;    //.trim() removes trailing spaces, .isEmpty() returns true for "", .equalsIgnoreCase() returns true for strings equal to none ignoring upper or lower cases.
        }
        if(imageUrl == null){
            imageView.setVisibility(View.GONE); //kisi view ki image na milne pr imageView ko collapse kr do
            return;
        }
        imageView.setVisibility(View.VISIBLE);  //recycler view ke us item jise collapse kiya tha, ab fir se reset kr do

        //will directly use imageUrl since there is no baseUrl for images
        String finalImageUrl = imageUrl;
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("GlideDebug", "Failed to load: " + finalImageUrl, e);
                        return false; // let Glide still show the error placeholder
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .placeholder(R.drawable.placeholder)    //when image is downloading
                .error(R.drawable.placeholder)  //when url is null
                .into(imageView);
    }
    @SerializedName("language")
    @Expose
    @Ignore //don't include these in db
    private String language;
    @SerializedName("category")
    @Expose
    @Ignore
    private List<String> category;
    @SerializedName("source_category")
    @Expose
    @Ignore
    private List<String> sourceCategory;
    @SerializedName("published")
    @Expose
    @Ignore
    private String published;



//getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getSourceCategory() {
        return sourceCategory;
    }

    public void setSourceCategory(List<String> sourceCategory) {
        this.sourceCategory = sourceCategory;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

}