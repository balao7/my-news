package com.eric.mynews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.Date;

public class Article implements Parcelable {
    @Json(name = "author") private String author;
    @Json(name = "title") private String title;
    @Json(name = "description") private String description;
    @Json(name = "url") private String url;
    @Json(name = "urlToImage") private String urlToImage;
    @Json(name = "publishedAt") private Date publishedAt;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.urlToImage);
        dest.writeLong(this.publishedAt != null ? this.publishedAt.getTime() : -1);
    }

    public Article() {}

    protected Article(Parcel in) {
        this.author = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.urlToImage = in.readString();
        long tmpPublishedAt = in.readLong();
        this.publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {return new Article(source);}

        @Override
        public Article[] newArray(int size) {return new Article[size];}
    };
}
