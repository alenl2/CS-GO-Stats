package si.komp.csgostats.Steam;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

public class SteamApiRequest<T extends ISteamObject> extends Request<ISteamObject> {
    private final Class<T> clazz;
    private final Response.Listener listener;

    //create a custom request for ISteamObject
    public SteamApiRequest(SteamUrl url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.GET, url.toString(), errorListener);
        this.clazz = url.getType();
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(ISteamObject response) {
        //type cast the response to the proper class
        listener.onResponse((T) response);
    }

    @Override
    protected Response<ISteamObject> parseNetworkResponse(NetworkResponse response) {
        try {
            //make the string with proper encoding
            String json = new String(response.data,  HttpHeaderParser.parseCharset(response.headers));

            //return the parsed string to deliverResponse for last type casting
            return Response.success(clazz.newInstance().parseSteamObject(json), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (InstantiationException e) {
            return Response.error(new ParseError(e));
        } catch (IllegalAccessException e) {
            return Response.error(new ParseError(e));
        }
    }
}