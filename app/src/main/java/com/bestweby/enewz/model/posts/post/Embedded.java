
package com.bestweby.enewz.model.posts.post;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;


import com.bestweby.enewz.model.posts.common.WpFeaturedmedium;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Embedded implements Parcelable
{

    @SerializedName("author")
    @Expose
    private List<Author> author = new ArrayList<>();
    @SerializedName("replies")
    @Expose
    private List<List<Reply>> replies = new ArrayList<>();
    @SerializedName("wp:featuredmedia")
    @Expose
    private List<WpFeaturedmedium> wpFeaturedmedia = new ArrayList<>();
    @SerializedName("wp:term")
    @Expose
    private List<List<WpTerm>> wpTerm = new ArrayList<>();
    public final static Parcelable.Creator<Embedded> CREATOR = new Creator<Embedded>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Embedded createFromParcel(Parcel in) {
            return new Embedded(in);
        }

        public Embedded[] newArray(int size) {
            return (new Embedded[size]);
        }

    }
    ;

    protected Embedded(Parcel in) {
        in.readList(this.author, (Author.class.getClassLoader()));
        in.readList(this.replies, (Reply.class.getClassLoader()));
        in.readList(this.wpFeaturedmedia, (WpFeaturedmedium.class.getClassLoader()));
        in.readList(this.wpTerm, (WpTerm.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Embedded() {
    }

    /**
     * 
     * @param author
     * @param wpTerm
     * @param replies
     * @param wpFeaturedmedia
     */
    public Embedded(List<Author> author, List<List<Reply>> replies, List<WpFeaturedmedium> wpFeaturedmedia, List<List<WpTerm>> wpTerm) {
        super();
        this.author = author;
        this.replies = replies;
        this.wpFeaturedmedia = wpFeaturedmedia;
        this.wpTerm = wpTerm;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public List<WpFeaturedmedium> getWpFeaturedmedia() {
        return wpFeaturedmedia;
    }

    public void setWpFeaturedmedia(List<WpFeaturedmedium> wpFeaturedmedia) {
        this.wpFeaturedmedia = wpFeaturedmedia;
    }

    public List<List<Reply>> getReplies() {
        return replies;
    }

    public void setReplies(List<List<Reply>> replies) {
        this.replies = replies;
    }

    public List<List<WpTerm>> getWpTerm() {
        return wpTerm;
    }

    public void setWpTerm(List<List<WpTerm>> wpTerm) {
        this.wpTerm = wpTerm;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(author);
        dest.writeList(wpFeaturedmedia);
        dest.writeList(replies);
        dest.writeList(wpTerm);
    }

    public int describeContents() {
        return  0;
    }

}
