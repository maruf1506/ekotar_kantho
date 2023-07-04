
package com.bestweby.enewz.model.posts.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.bestweby.enewz.model.posts.post.Title;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WpFeaturedmedium implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("mime_type")
    @Expose
    private String mimeType;
    @SerializedName("media_details")
    @Expose
    private MediaDetails mediaDetails;
    @SerializedName("source_url")
    @Expose
    private String sourceUrl;
    public final static Parcelable.Creator<WpFeaturedmedium> CREATOR = new Creator<WpFeaturedmedium>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WpFeaturedmedium createFromParcel(Parcel in) {
            return new WpFeaturedmedium(in);
        }

        public WpFeaturedmedium[] newArray(int size) {
            return (new WpFeaturedmedium[size]);
        }

    }
    ;

    protected WpFeaturedmedium(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((Title) in.readValue((Title.class.getClassLoader())));
        this.mediaType = ((String) in.readValue((String.class.getClassLoader())));
        this.mimeType = ((String) in.readValue((String.class.getClassLoader())));
        this.mediaDetails = ((MediaDetails) in.readValue((MediaDetails.class.getClassLoader())));
        this.sourceUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public WpFeaturedmedium() {
    }

    /**
     * 
     * @param id
     * @param title
     * @param mediaDetails
     * @param link
     * @param slug
     * @param sourceUrl
     * @param type
     * @param date
     * @param mimeType
     * @param mediaType
     */
    public WpFeaturedmedium(Integer id, String date, String slug, String type, String link, Title title, String mediaType, String mimeType, MediaDetails mediaDetails, String sourceUrl) {
        super();
        this.id = id;
        this.date = date;
        this.slug = slug;
        this.type = type;
        this.link = link;
        this.title = title;
        this.mediaType = mediaType;
        this.mimeType = mimeType;
        this.mediaDetails = mediaDetails;
        this.sourceUrl = sourceUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public MediaDetails getMediaDetails() {
        return mediaDetails;
    }

    public void setMediaDetails(MediaDetails mediaDetails) {
        this.mediaDetails = mediaDetails;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(date);
        dest.writeValue(slug);
        dest.writeValue(type);
        dest.writeValue(link);
        dest.writeValue(title);
        dest.writeValue(mediaType);
        dest.writeValue(mimeType);
        dest.writeValue(mediaDetails);
        dest.writeValue(sourceUrl);
    }

    public int describeContents() {
        return  0;
    }

}
