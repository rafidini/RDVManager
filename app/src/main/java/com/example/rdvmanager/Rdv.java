package com.example.rdvmanager;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

/**
 * Model for a "Rendez-vous".
 */
public class Rdv implements Parcelable {
    /* Attributes */
    private long aId; // Auto-incremented
    private String aTitle;
    private String aDate;
    private String aTime;
    private String aPerson;
    private String aAddress; // Optional
    private String aPhoneNumber; // Optional
    private boolean aDone;

    /* Constructors */

    /**
     * Natural class constructor.
     */
    public Rdv(long pId, String pTitle, String pDate, String pTime,
               String pPerson, String pAddress, String pPhoneNumber,
               boolean pDone) {
        this.aId = pId;
        this.aTitle = pTitle;
        this.aDate = pDate;
        this.aTime = pTime;
        this.aPerson = pPerson;
        this.aAddress = pAddress;
        this.aPhoneNumber = pPhoneNumber;
        this.aDone = pDone;
    } // Rdv(........)


    /**
     * Another class constructor.
     */
    public Rdv(long pId, String pTitle, String pDate, String pTime,
               String pPerson, String pAddress, String pPhoneNumber) {
        this(pId, pTitle, pDate, pTime, pPerson, pAddress, pPhoneNumber, false);
    } // Rdv(.......)

    /**
     * Another class constructor.
     */
    public Rdv() {
        this(1, null, null, null, null, null,
                null, false);
    } // Rdv(.......)

    /**
     * Another class constructor.
     */
    public Rdv(Parcel parcel) {
        this(parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString(),
                parcel.readString(), parcel.readString(), parcel.readString());
    }

    /* Methods */

    /* Getters and Setters */
    public long getId() {
        return aId;
    }

    public void setId(long aId) {
        this.aId = aId;
    }

    public String getTitle() {
        return aTitle;
    }

    public void setTitle(String aTitle) {
        this.aTitle = aTitle;
    }

    public String getDate() {
        return aDate;
    }

    public void setDate(String aDate) {
        this.aDate = aDate;
    }

    public String getTime() {
        return aTime;
    }

    public void setTime(String aTime) {
        this.aTime = aTime;
    }

    public String getPerson() {
        return aPerson;
    }

    public void setPerson(String aPerson) {
        this.aPerson = aPerson;
    }

    public String getAddress() {
        return aAddress;
    }

    public void setAddress(String aAddress) {
        this.aAddress = aAddress;
    }

    public String getPhoneNumber() {
        return aPhoneNumber;
    }

    public void setPhoneNumber(String aPhoneNumber) {
        this.aPhoneNumber = aPhoneNumber;
    }

    public boolean isDone() {
        return aDone;
    }

    public void setDone(boolean aDone) {
        this.aDone = aDone;
    }

    @Override
    public int describeContents() {
        return hashCode();
    } // describeContents()

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.aId);
        dest.writeString(this.aTitle);
        dest.writeString(this.aDate);
        dest.writeString(this.aTime);
        dest.writeString(this.aPerson);
        dest.writeString(this.aAddress);
        dest.writeString(this.aPerson);
        dest.writeBoolean(this.aDone);
    } // writeToParcel(..)

    public static final Parcelable.Creator<Rdv> CREATOR = new Parcelable.Creator<Rdv>() {
        @Override
        public Rdv createFromParcel(Parcel parcel) {
            return new Rdv(parcel);
        }

        @Override
        public Rdv[] newArray(int size) {
            return new Rdv[size];
        }
    };
} // Rdv
