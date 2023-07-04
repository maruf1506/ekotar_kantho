
package com.bestweby.enewz.model.posts.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sizes implements Parcelable
{

    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("medium_large")
    @Expose
    private MediumLarge mediumLarge;
    @SerializedName("full")
    @Expose
    private Full full;
    public final static Parcelable.Creator<Sizes> CREATOR = new Creator<Sizes>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Sizes createFromParcel(Parcel in) {
            return new Sizes(in);
        }

        public Sizes[] newArray(int size) {
            return (new Sizes[size]);
        }

    }
    ;

    protected Sizes(Parcel in) {
        this.thumbnail = ((Thumbnail) in.readValue((Thumbnail.class.getClassLoader())));
        this.medium = ((Medium) in.readValue((Medium.class.getClassLoader())));
        this.mediumLarge = ((MediumLarge) in.readValue((MediumLarge.class.getClassLoader())));
        this.full = ((Full) in.readValue((Full.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sizes() {
    }

    /**
     * 
     * @param mediumLarge
     * @param thumbnail
     * @param full
     * @param medium
     */
    public Sizes(Thumbnail thumbnail, Medium medium, MediumLarge mediumLarge, Full full) {
        super();
        this.thumbnail = thumbnail;
        this.medium = medium;
        this.mediumLarge = mediumLarge;
        this.full = full;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public MediumLarge getMediumLarge() {
        return mediumLarge;
    }

    public void setMediumLarge(MediumLarge mediumLarge) {
        this.mediumLarge = mediumLarge;
    }

    public Full getFull() {
        return full;
    }

    public void setFull(Full full) {
        this.full = full;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(thumbnail);
        dest.writeValue(medium);
        dest.writeValue(mediumLarge);
        dest.writeValue(full);
    }

    public int describeContents() {
        return  0;
    }

}
