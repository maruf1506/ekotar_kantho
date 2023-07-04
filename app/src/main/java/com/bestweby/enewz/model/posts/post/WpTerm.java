
package com.bestweby.enewz.model.posts.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WpTerm implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("taxonomy")
    @Expose
    private String taxonomy;
    public final static Parcelable.Creator<WpTerm> CREATOR = new Creator<WpTerm>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WpTerm createFromParcel(Parcel in) {
            return new WpTerm(in);
        }

        public WpTerm[] newArray(int size) {
            return (new WpTerm[size]);
        }

    }
    ;

    protected WpTerm(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.taxonomy = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public WpTerm() {
    }

    /**
     * 
     * @param id
     * @param name
     * @param link
     * @param slug
     * @param taxonomy
     */
    public WpTerm(Integer id, String link, String name, String slug, String taxonomy) {
        super();
        this.id = id;
        this.link = link;
        this.name = name;
        this.slug = slug;
        this.taxonomy = taxonomy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(link);
        dest.writeValue(name);
        dest.writeValue(slug);
        dest.writeValue(taxonomy);
    }

    public int describeContents() {
        return  0;
    }

}
