package si.komp.csgostats.Steam;

import android.net.Uri;

public class SteamUrl {

    private Uri url;
    private Class type;

    public SteamUrl(Uri url, Class<? extends ISteamObject> type) {
        this.url = url;
        this.type = type;
    }

    public Class getType() {
        return type;
    }

    @Override
    public String toString() {
        return url.toString();
    }
}
