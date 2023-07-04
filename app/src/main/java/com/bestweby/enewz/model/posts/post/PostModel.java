package com.bestweby.enewz.model.posts.post;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostModel implements Parcelable
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("excerpt")
    @Expose
    private Excerpt excerpt;
    @SerializedName("featured_media")
    @Expose
    private Integer featuredMedia;
    @SerializedName("comment_status")
    @Expose
    private String commentStatus;
    @SerializedName("ping_status")
    @Expose
    private String pingStatus;
    @SerializedName("sticky")
    @Expose
    private Boolean sticky;
    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("categories")
    @Expose
    private List<Integer> categories = new ArrayList<>();
    @SerializedName("tags")
    @Expose
    private List<Integer> tags = new ArrayList<>();
    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;

    public final static Parcelable.Creator<PostModel> CREATOR = new Creator<PostModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        public PostModel[] newArray(int size) {
            return (new PostModel[size]);
        }

    }
    ;

    protected PostModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.dateGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.modified = ((String) in.readValue((String.class.getClassLoader())));
        this.modifiedGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((Title) in.readValue((Title.class.getClassLoader())));
        this.content = ((Content) in.readValue((Content.class.getClassLoader())));
        this.excerpt = ((Excerpt) in.readValue((Excerpt.class.getClassLoader())));
        this.featuredMedia = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.commentStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.pingStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.sticky = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.template = ((String) in.readValue((String.class.getClassLoader())));
        this.format = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.categories, (java.lang.Integer.class.getClassLoader()));
        in.readList(this.tags, (java.lang.Integer.class.getClassLoader()));
        this.embedded = ((Embedded) in.readValue((Embedded.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public PostModel() {
    }

    /**
     * 
     * @param tags
     * @param template
     * @param status
     * @param commentStatus
     * @param excerpt
     * @param link
     * @param format
     * @param dateGmt
     * @param date
     * @param type
     * @param embedded
     * @param pingStatus
     * @param modified
     * @param id
     * @param modifiedGmt
     * @param content
     * @param sticky
     * @param title
     * @param featuredMedia
     * @param categories
     * @param slug
     */
    public PostModel(Integer id, String date, String dateGmt, String modified, String modifiedGmt, String slug, String status, String type, String link, Title title, Content content, Excerpt excerpt, Integer featuredMedia, String commentStatus, String pingStatus, Boolean sticky, String template, String format, List<Integer> categories, List<Integer> tags, Embedded embedded) {
        super();
        this.id = id;
        this.date = date;
        this.dateGmt = dateGmt;
        this.modified = modified;
        this.modifiedGmt = modifiedGmt;
        this.slug = slug;
        this.status = status;
        this.type = type;
        this.link = link;
        this.title = title;
        this.content = content;
        this.excerpt = excerpt;
        this.featuredMedia = featuredMedia;
        this.commentStatus = commentStatus;
        this.pingStatus = pingStatus;
        this.sticky = sticky;
        this.template = template;
        this.format = format;
        this.categories = categories;
        this.tags = tags;
        this.embedded = embedded;
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

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Excerpt getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    public Integer getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(Integer featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    public Boolean getSticky() {
        return sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(date);
        dest.writeValue(dateGmt);
        dest.writeValue(modified);
        dest.writeValue(modifiedGmt);
        dest.writeValue(slug);
        dest.writeValue(status);
        dest.writeValue(type);
        dest.writeValue(link);
        dest.writeValue(title);
        dest.writeValue(content);
        dest.writeValue(excerpt);
        dest.writeValue(featuredMedia);
        dest.writeValue(commentStatus);
        dest.writeValue(pingStatus);
        dest.writeValue(sticky);
        dest.writeValue(template);
        dest.writeValue(format);
        dest.writeList(categories);
        dest.writeList(tags);
        dest.writeValue(embedded);
    }

    public int describeContents() {
        return  0;
    }

}
