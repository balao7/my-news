package com.eric.mynews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.Date;

@Entity(indexes = {@Index(value = "title", unique = true)})
public class Article implements Parcelable {
    @Id private Long id;

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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
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

    @Generated(hash = 1663253903)
    public Article(Long id, String author, String title, String description, String url, String urlToImage, Date publishedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {return new Article(source);}

        @Override
        public Article[] newArray(int size) {return new Article[size];}
    };
}
