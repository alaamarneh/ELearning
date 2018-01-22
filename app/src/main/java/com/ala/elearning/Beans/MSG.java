package com.ala.elearning.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by alaam on 12/30/2017.
 */

public class MSG  implements Parcelable{
    private String text;
    private Date date;
    private Course course;
    private User to,From;

    public MSG() {
    }


    protected MSG(Parcel in) {
        text = in.readString();
        course = in.readParcelable(Course.class.getClassLoader());
        to = in.readParcelable(User.class.getClassLoader());
        From = in.readParcelable(User.class.getClassLoader());
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeParcelable(course, flags);
        dest.writeParcelable(to, flags);
        dest.writeParcelable(From, flags);
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MSG> CREATOR = new Creator<MSG>() {
        @Override
        public MSG createFromParcel(Parcel in) {
            return new MSG(in);
        }

        @Override
        public MSG[] newArray(int size) {
            return new MSG[size];
        }
    };

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return From;
    }

    public void setFrom(User from) {
        From = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


}
