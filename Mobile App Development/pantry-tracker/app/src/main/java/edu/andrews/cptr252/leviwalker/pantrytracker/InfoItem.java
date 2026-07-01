package edu.andrews.cptr252.leviwalker.pantrytracker;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InfoItem implements Parcelable {
    private String name = "";
    private String expiration = "";
    private String quantity = "";
    private String photo = "";
    private Long id = -1L;

    public InfoItem(){
    }

    public InfoItem(Parcel source){
        String[] data = new String[5];
        source.readStringArray(data);
        setName(data[0]);
        setExpiration(data[1]);
        setQuantity(data[2]);
        setPhoto(data[3]);
        setId(Long.parseLong(data[4]));
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags){
        dest.writeStringArray(new String[]{
                getName(), getExpiration(), getQuantity(), getPhoto(), String.valueOf(getId())
        });
    }

    public static final Parcelable.Creator<InfoItem> CREATOR = new Parcelable.Creator<InfoItem>(){

        @Override
        public InfoItem createFromParcel(Parcel source){
            return new InfoItem(source);
        }

        @Override
        public InfoItem[] newArray(int size) {
            return new InfoItem[0];
        }

    };

}

