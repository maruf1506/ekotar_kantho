
package com.bestweby.enewz.model.posts.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reply implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parent")
    @Expose
    private Integer parent;
    @SerializedName("author")
    @Expose
    private Integer author;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("author_url")
    @Expose
    private String authorUrl;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("author_avatar_urls")
    @Expose
    private AvatarUrls authorAvatarUrls;


    public final static Creator<Reply> CREATOR = new Creator<Reply>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Reply createFromParcel(Parcel in) {
            return new Reply(in);
        }

        public Reply[] newArray(int size) {
            return (new Reply[size]);
        }

    }
    ;

    protected Reply(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.parent = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.author = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.authorName = ((String) in.readValue((String.class.getClassLoader())));
        this.authorUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.dateGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((Content) in.readValue((Content.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.authorAvatarUrls = ((AvatarUrls) in.readValue((AvatarUrls.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Reply() {
    }

    /**
     *
     * @param content
     * @param id
     * @param author
     * @param link
     * @param authorName
     * @param authorUrl
     * @param parent
     * @param authorAvatarUrls
     * @param type
     * @param date
     * @param dateGmt
     */
    public Reply(Integer id, Integer parent, Integer author, String authorName, String authorUrl, String date, String dateGmt, Content content, String link, String type, AvatarUrls authorAvatarUrls) {
        super();
        this.id = id;
        this.parent = parent;
        this.author = author;
        this.authorName = authorName;
        this.authorUrl = authorUrl;
        this.date = date;
        this.dateGmt = dateGmt;
        this.content = content;
        this.link = link;
        this.type = type;
        this.authorAvatarUrls = authorAvatarUrls;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AvatarUrls getAuthorAvatarUrls() {
        return authorAvatarUrls;
    }

    public void setAuthorAvatarUrls(AvatarUrls authorAvatarUrls) {
        this.authorAvatarUrls = authorAvatarUrls;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(parent);
        dest.writeValue(author);
        dest.writeValue(authorName);
        dest.writeValue(authorUrl);
        dest.writeValue(date);
        dest.writeValue(dateGmt);
        dest.writeValue(content);
        dest.writeValue(link);
        dest.writeValue(type);
        dest.writeValue(authorAvatarUrls);
    }

    public int describeContents() {
        return  0;
    }

}
