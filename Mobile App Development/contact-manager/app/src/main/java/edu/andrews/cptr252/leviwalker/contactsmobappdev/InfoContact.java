package edu.andrews.cptr252.leviwalker.contactsmobappdev;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InfoContact implements Parcelable {
    private String name = "";
    private String email = "";
    private String phone = "";
    private String address = "";
    private String photo = "";
    private Long id = -1L;

    public InfoContact(){
    }

    public InfoContact(Parcel source){
        String[] data = new String[6];
        source.readStringArray(data);
        setName(data[0]);
        setEmail(data[1]);
        setPhone(data[2]);
        setAddress(data[3]);
        setPhoto(data[4]);
        setId(Long.parseLong(data[5]));
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                getName(), getEmail(), getPhone(), getAddress(), getPhoto(), String.valueOf(getId())
        });
    }

    public static final Parcelable.Creator<InfoContact> CREATOR = new Parcelable.Creator<InfoContact>(){

        @Override
        public InfoContact createFromParcel(Parcel source){
            return new InfoContact(source);
        }

        @Override
        public InfoContact[] newArray(int size) {
            return new InfoContact[0];
        }

    };

}

