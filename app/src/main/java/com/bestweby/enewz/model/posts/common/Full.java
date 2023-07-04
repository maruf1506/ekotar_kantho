
package com.bestweby.enewz.model.posts.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Full implements Parcelable
{

    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("mime_type")
    @Expose
    private String mimeType;
    @SerializedName("source_url")
    @Expose
    private String sourceUrl;
    public final static Parcelable.Creator<Full> CREATOR = new Creator<Full>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Full createFromParcel(Parcel in) {
            return new Full(in);
        }

        public Full[] newArray(int size) {
            return (new Full[size]);
        }

    }
    ;

    protected Full(Parcel in) {
        this.file = ((String) in.readValue((String.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mimeType = ((String) in.readValue((String.class.getClassLoader())));
        this.sourceUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Full() {
    }

    /**
     * 
     * @param height
     * @param file
     * @param width
     * @param sourceUrl
     * @param mimeType
     */
    public Full(String file, Integer width, Integer height, String mimeType, String sourceUrl) {
        super();
        this.file = file;
        this.width = width;
        this.height = height;
        this.mimeType = mimeType;
        this.sourceUrl = sourceUrl;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(file);
        dest.writeValue(width);
        dest.writeValue(height);
        dest.writeValue(mimeType);
        dest.writeValue(sourceUrl);
    }

    public int describeContents() {
        return  0;
    }

}
