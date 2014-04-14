package actors.messaging.exotel;


import akka.actor.UntypedActor;
import models.actor.messaging.exotel.SendSmsActorMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
//import org.openxmlformats.schemas.drawingml.x2006.main.impl.STLineEndWidthImpl;
import play.Logger;
import play.Play;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class SendSmsActor extends UntypedActor {

    public static final String EXOTEL_AUTH_SID = Play.application().configuration().getString("exotel.auth.sid");
    public static final String EXOTEL_AUTH_TOKEN = Play.application().configuration().getString("exotel.auth.token");
    public static final String FROM = "Pickme India";

    @Override
    public void onReceive(Object message) throws Exception {

        SendSmsActorMessage ssam = null;
        if(message instanceof SendSmsActorMessage)
            ssam = (SendSmsActorMessage) message;
        if(ssam == null)
            return;

        SSLContext sslContext = SSLContext.getInstance("SSL");

        // set up a TrustManager that trusts everything
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                                           String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs,
                                           String authType) {
            }

            public boolean isClientTrusted(X509Certificate[] arg0) {
                return false;
            }

            public boolean isServerTrusted(X509Certificate[] arg0) {
                return false;
            }
        }}, new SecureRandom());
        SSLSocketFactory sf = SSLSocketFactory.getSocketFactory();
        Scheme httpsScheme = new Scheme("https", sf, 443);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(httpsScheme);
        HttpParams params = new BasicHttpParams();
        ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);

        DefaultHttpClient client = new DefaultHttpClient(cm, params);
        client.getCredentialsProvider().setCredentials(
                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(EXOTEL_AUTH_SID, EXOTEL_AUTH_TOKEN)
        );
        HttpPost post = new HttpPost("https://" + EXOTEL_AUTH_SID + ":" + EXOTEL_AUTH_TOKEN + "@twilix.exotel.in/v1/Accounts/" + EXOTEL_AUTH_SID + "/Sms/send");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("From", FROM));
        nameValuePairs.add(new BasicNameValuePair("To", ssam.getPhone()));
        nameValuePairs.add(new BasicNameValuePair("Body", ssam.getBody()));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = client.execute(post, responseHandler);
        Logger.info("EXOTEL SMS RESPONSE : " + response);
    }
}
