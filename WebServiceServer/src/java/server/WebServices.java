package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebService(serviceName = "WebService")
public class WebServices {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "googleMapsDistance")
    public String googleMapsDistance(@WebParam(name = "origin") String origin, @WebParam(name = "destination") String destination) throws ProtocolException, IOException {
        String api = "AIzaSyC30fCnCqt0kI4tdOqRMm4mg0kW5oe1tGo"; //oc do not steal 
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + origin + ",UK&destinations=" + destination + ",UK&key=" + api);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        JsonObject jsonObject = new JsonParser().parse(content.toString()).getAsJsonObject();
        String distance = jsonObject.getAsJsonArray("rows").get(0).getAsJsonObject().get("elements").getAsJsonArray().get(0).getAsJsonObject().get("distance").getAsJsonObject().get("value").getAsString();
        return distance;
    }
}
