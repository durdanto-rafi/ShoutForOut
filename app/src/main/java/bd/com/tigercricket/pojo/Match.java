package bd.com.tigercricket.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RAFI on 09-Mar-16.
 */
public class Match implements Parcelable {
    private int id;
    private String team_1;
    private String team_2;
    private String team_1_flag;
    private String team_2_flag;
    private String situation;
    private int shouts;
    private int outs;

    public int getNot_outs() {
        return not_outs;
    }

    public void setNot_outs(int not_outs) {
        this.not_outs = not_outs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam_1() {
        return team_1;
    }

    public void setTeam_1(String team_1) {
        this.team_1 = team_1;
    }

    public String getTeam_2() {
        return team_2;
    }

    public void setTeam_2(String team_2) {
        this.team_2 = team_2;
    }

    public String getTeam_1_flag() {
        return team_1_flag;
    }

    public void setTeam_1_flag(String team_1_flag) {
        this.team_1_flag = team_1_flag;
    }

    public String getTeam_2_flag() {
        return team_2_flag;
    }

    public void setTeam_2_flag(String team_2_flag) {
        this.team_2_flag = team_2_flag;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public int getShouts() {
        return shouts;
    }

    public void setShouts(int shouts) {
        this.shouts = shouts;
    }

    public int getOuts() {
        return outs;
    }

    public void setOuts(int outs) {
        this.outs = outs;
    }

    private int not_outs;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.team_1);
        dest.writeString(this.team_2);
        dest.writeString(this.team_1_flag);
        dest.writeString(this.team_2_flag);
        dest.writeString(this.situation);
        dest.writeInt(this.shouts);
        dest.writeInt(this.outs);
        dest.writeInt(this.not_outs);
    }

    public Match() {
    }

    protected Match(Parcel in) {
        this.id = in.readInt();
        this.team_1 = in.readString();
        this.team_2 = in.readString();
        this.team_1_flag = in.readString();
        this.team_2_flag = in.readString();
        this.situation = in.readString();
        this.shouts = in.readInt();
        this.outs = in.readInt();
        this.not_outs = in.readInt();
    }

    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
        public Match createFromParcel(Parcel source) {
            return new Match(source);
        }

        public Match[] newArray(int size) {
            return new Match[size];
        }
    };
}
