package interdroid.swan.swanexpressions.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by steven on 24/04/16.
 */
public class ConfigurationItem implements Parcelable {

    public String key;
    public String value;

    public ConfigurationItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    protected ConfigurationItem(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    public static final Creator<ConfigurationItem> CREATOR = new Creator<ConfigurationItem>() {
        @Override
        public ConfigurationItem createFromParcel(Parcel in) {
            return new ConfigurationItem(in);
        }

        @Override
        public ConfigurationItem[] newArray(int size) {
            return new ConfigurationItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }
}
