package at.htl.xiboClient.helper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

// Singleton Pattern
public class AuthentificationHandler {

    //handles the authentification process with OAuth2 later on the client id/secret will be provided by params
    private static final String CLIENT_ID = "vwmpjcZ6wrYmNShhOXVQsW54N8PJulK6AEhfRVeF";
    private static final String CLIENT_SECRET = "Z93XJGgZli3yMA0gYdtJjt5qYPEi2zdMMeddzsYbMMWj5AetOuMIfNAn4kAcNtY1GoYSt9dKjfSVIGRGFHnYbf9GEcrgVibliZRNVCPZyvH1cgDJT8vJywAhoWKGQG2wSjOnViXGIqwuOQTi4ojPgX1ZHK4m6sgpbx1micAkY6e7L7xLly7h2gKEHScXOEIhfF9jAmMFxvK1fqQv9o6vsTJCNsEbfRiEKQYYSzkCqfIya9YFWmTAfykGgGsrj0";
    private static final String AUTHORIZE_URL = "http://localhost:9090/api/authorize/access_token";

    private static String TOKEN = "";

    //Authenticates at the xibo application
    public static String Authenticate() {

        // Declaration
        URL obj = null;
        HttpURLConnection con = null;

        try {

            // Building Connection
            obj = new URL(AUTHORIZE_URL);
            con = (HttpURLConnection) obj.openConnection();

            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);
            con.setRequestMethod("POST");

            DataOutputStream write = new DataOutputStream(con.getOutputStream());
            String body = String.format("client_id=" + CLIENT_ID
                    + "&client_secret=" + CLIENT_SECRET
                    + "&grant_type=client_credentials");

            write.writeBytes(body);
            write.flush();

            // Testing Output
            System.out.println("nPost-Body: " + body);
            System.out.println("nSending 'AUTHORIZATION' request to URL : " + AUTHORIZE_URL);
            System.out.println("Response Code : " + con.getResponseCode());

            //Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            // close connection
            in.close();

            //printing result from response
            System.out.println(response.toString());

            return new JSONObject(response.toString()).getString("access_token");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (con != null)
                con.disconnect();
        }
        return "noToken";
    }

    public static String getTOKEN() {
        if (TOKEN.isEmpty() || TOKEN.equals("noToken")/*Or 3600 expired*/)
            TOKEN = Authenticate();
        return TOKEN;
    }
}

