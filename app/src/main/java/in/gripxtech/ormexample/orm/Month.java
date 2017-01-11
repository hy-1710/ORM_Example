package in.gripxtech.ormexample.orm;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * These classes will use as TABLE for ORM and also POJO Models for RecyclerView
 */
public class Month extends SugarRecord implements Parcelable {

    public static final Creator<Month> CREATOR = new Creator<Month>() {
        @Override
        public Month createFromParcel(Parcel in) {
            return new Month(in);
        }

        @Override
        public Month[] newArray(int size) {
            return new Month[size];
        }
    };
    // Don't add id field, it's already there in SugarRecord
    // private Long id
    private int number;
    private String name;
    private String description;

    // Empty Constructor is required for SugarORM
    // Each Model must have Empty constructor
    public Month() {
    }

    public Month(int number, String name, String description) {
        this.number = number;
        this.name = name;
        this.description = description;
    }

    protected Month(Parcel in) {
        number = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Month(id=" + getId() + ", number=" + number + ", " +
                "name=" + name + ", " +
                "description=" + description + ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(name);
        dest.writeString(description);
    }
}
