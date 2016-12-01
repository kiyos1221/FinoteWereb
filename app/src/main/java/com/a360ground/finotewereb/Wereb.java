package com.a360ground.finotewereb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kiyos Solomon on 11/26/2016.
 */

public class Wereb implements Parcelable {
    int werebId;
    int werebThumbNail;
    String werebName;
    String werebGeez;
    String werebAmharic;


    public Wereb(String werebName) {
        this.werebName = werebName;
    }

    public int getWerebId() {
        return werebId;
    }

    public String getWerebAmharic() {
        return werebAmharic;
    }

    public void setWerebAmharic(String werebAmharic) {
        this.werebAmharic = werebAmharic;
    }

    public String getWerebGeez() {
        return werebGeez;
    }

    public void setWerebGeez(String werebGeez) {
        this.werebGeez = werebGeez;
    }

    public void setWerebId(int werebId) {
        this.werebId = werebId;
    }

    public int getWerebThumbNail() {
        return werebThumbNail;
    }

    public void setWerebThumbNail(int werebThumbNail) {
        this.werebThumbNail = werebThumbNail;
    }

    public String getWerebName() {
        return werebName;
    }

    public void setWerebName(String werebName) {
        this.werebName = werebName;
    }

    public static Creator<Wereb> getCREATOR() {
        return CREATOR;
    }

    protected Wereb(Parcel in) {
    }

    public static final Creator<Wereb> CREATOR = new Creator<Wereb>() {
        @Override
        public Wereb createFromParcel(Parcel in) {
            return new Wereb(in);
        }

        @Override
        public Wereb[] newArray(int size) {
            return new Wereb[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
