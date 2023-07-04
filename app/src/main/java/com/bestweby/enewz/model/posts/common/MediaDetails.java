
package com.bestweby.enewz.model.posts.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaDetails implements Parcelable
{

    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("sizes")
    @Expose
    private Sizes sizes;
    public final static Parcelable.Creator<MediaDetails> CREATOR = new Creator<MediaDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MediaDetails createFromParcel(Parcel in) {
            return new MediaDetails(in);
        }

        public MediaDetails[] newArray(int size) {
            return (new MediaDetails[size]);
        }

    }
    ;

    protected MediaDetails(Parcel in) {
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.file = ((String) in.readValue((String.class.getClassLoader())));
        this.sizes = ((Sizes) in.readValue((Sizes.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MediaDetails() {
    }

    /**
     * 
     * @param sizes
     * @param height
     * @param file
     * @param width
     */
    public MediaDetails(Integer width, Integer height, String file, Sizes sizes) {
        super();
        this.width = width;
        this.height = height;
        this.file = file;
        this.sizes = sizes;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(width);
        dest.writeValue(height);
        dest.writeValue(file);
        dest.writeValue(sizes);
    }

    public int describeContents() {
        return  0;
    }

}
