package si.komp.csgostats.Steam;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class SteamApiManagerSingleton implements ISteamApiManager {

    //Singleton pattern
    private static ISteamApiManager ourInstance = null;
    private RequestQueue queue;

    private SteamApiManagerSingleton(Context ctx) {
        //setup volley
        Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start(); //start the queue thread
    }

    public static ISteamApiManager getInstance(Context ctx) {
        if (ourInstance == null) {
            ourInstance = new SteamApiManagerSingleton(ctx);
        }
        return ourInstance;
    }

    @Override
    public void addToRequestQueue(Request request) {
        queue.add(request);
    }
}
