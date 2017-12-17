package homeds.htl.at.homedsremote.entity;

/**
 * Created by Felix on 30.11.2017.
 */

public class AccessData {

    String access_token;
    String BASEUrl;
    String clientId;
    String clientSecret;

    public AccessData() {
    }

    public AccessData(String access_token, String BASEUrl, String clientId, String clientSecret) {
        this.access_token = access_token;
        this.BASEUrl = BASEUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getBASEUrl() {
        return BASEUrl;
    }

    public void setBASEUrl(String BASEUrl) {
        this.BASEUrl = BASEUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
