package si.komp.csgostats.Steam;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SteamPlayer implements ISteamObject {

    //properties received from steam api
    public String steamid;
    public String communityvisibilitystate;
    public String profilestate;
    public String personaname;
    public String lastlogoff;
    public String profileurl;
    public String avatar;
    public String avatarmedium;
    public String avatarfull;
    public String personastate;
    public String realname;
    public String primaryclanid;
    public String timecreated;
    public String personastateflags;
    public String loccountrycode;
    public String locstatecode;

    //url assembler
    public static SteamUrl GetPlayerSummariesUrl(String steamId) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(SteamApiCostants.scheme)
                .authority(SteamApiCostants.domain)
                .appendPath("ISteamUser")
                .appendPath("GetPlayerSummaries")
                .appendPath(SteamApiCostants.version2)
                .appendQueryParameter(SteamApiCostants.apiKeyParameterName, SteamApiCostants.steamApiKey)
                .appendQueryParameter(SteamApiCostants.steamIdParamaterName, steamId);
        return new SteamUrl(uriBuilder.build(), SteamPlayer.class);
    }

    //json parser
    @Override
    public ISteamObject parseSteamObject(String data) {
        Gson gson = new Gson();
        JsonObject wholeObj = new JsonParser().parse(data).getAsJsonObject();
        return gson.fromJson(wholeObj.getAsJsonObject("response").getAsJsonArray("players").get(0), SteamPlayer.class);
    }
}
