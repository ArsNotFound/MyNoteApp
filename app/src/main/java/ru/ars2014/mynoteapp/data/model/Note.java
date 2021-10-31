package ru.ars2014.mynoteapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public Long id = null;

    public String title;
    public String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(Parcel in) {
        id = (Long) in.readSerializable();
        title = in.readString();
        content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(id);
        parcel.writeString(title);
        parcel.writeString(content);
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {

        @Override
        public Note createFromParcel(Parcel parcel) {
            return new Note(parcel);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
