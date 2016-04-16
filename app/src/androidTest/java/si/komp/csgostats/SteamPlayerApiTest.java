package si.komp.csgostats;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import si.komp.csgostats.Steam.ISteamApiManager;
import si.komp.csgostats.Steam.SteamApiManagerSingleton;
import si.komp.csgostats.Steam.SteamApiRequest;
import si.komp.csgostats.Steam.SteamPlayer;

public class SteamPlayerApiTest extends InstrumentationTestCase {
    private final String steamId = "76561198066338191";
    private Context ctx = null;

    protected void setUp() throws Exception {
        super.setUp();
        ctx = this.getInstrumentation().getTargetContext().getApplicationContext();
    }

    public void testGetPlayerRequest() throws  Exception{
        ISteamApiManager steamMgr = SteamApiManagerSingleton.getInstance(ctx);
        final CountDownLatch signal = new CountDownLatch(1);
        SteamApiRequest asd = new SteamApiRequest<>(SteamPlayer.GetPlayerSummariesUrl(steamId),
                new Response.Listener<SteamPlayer>() {
                    @Override
                    public void onResponse(SteamPlayer response) {
                        assertNotNull(response);
                        assertNotNull(response.steamid);
                        assertNotNull(response.personaname);
                        assertEquals(steamId, response.steamid);
                        signal.countDown();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        assertTrue(false);//test failed
                    }
                });
        steamMgr.addToRequestQueue(asd);
        assertTrue(signal.await(5000, TimeUnit.MILLISECONDS));
    }
}
