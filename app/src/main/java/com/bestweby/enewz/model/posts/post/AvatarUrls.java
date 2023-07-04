
package com.bestweby.enewz.model.posts.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvatarUrls implements Parcelable
{

    @SerializedName("24")
    @Expose
    private String _24;
    @SerializedName("48")
    @Expose
    private String _48;
    @SerializedName("96")
    @Expose
    private String _96;
    public final static Parcelable.Creator<AvatarUrls> CREATOR = new Creator<AvatarUrls>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AvatarUrls createFromParcel(Parcel in) {
            return new AvatarUrls(in);
        }

        public AvatarUrls[] newArray(int size) {
            return (new AvatarUrls[size]);
        }

    }
    ;

    protected AvatarUrls(Parcel in) {
        this._24 = ((String) in.readValue((String.class.getClassLoader())));
        this._48 = ((String) in.readValue((String.class.getClassLoader())));
        this._96 = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public AvatarUrls() {
    }

    /**
     * 
     * @param _48
     * @param _96
     * @param _24
     */
    public AvatarUrls(String _24, String _48, String _96) {
        super();
        this._24 = _24;
        this._48 = _48;
        this._96 = _96;
    }

    public String get24() {
        return _24;
    }

    public void set24(String _24) {
        this._24 = _24;
    }

    public String get48() {
        return _48;
    }

    public void set48(String _48) {
        this._48 = _48;
    }

    public String get96() {
        return _96;
    }

    public void set96(String _96) {
        this._96 = _96;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_24);
        dest.writeValue(_48);
        dest.writeValue(_96);
    }

    public int describeContents() {
        return  0;
    }

}
