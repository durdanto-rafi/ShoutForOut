package bd.com.tigercricket.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RAFI on 08-Mar-16.
 */
public class User implements Parcelable {

    private String name;
    private String email;
    private String facebookID;
    private String gender;

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

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.facebookID);
        dest.writeString(this.gender);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.facebookID = in.readString();
        this.gender = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
