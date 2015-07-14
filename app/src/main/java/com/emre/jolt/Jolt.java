package com.emre.jolt;

import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Emre Davarci on 13.07.2015.
 */
public class Jolt {

    private static Jolt jolt;
    private static JoltConnectionListener listener;
    private String url;
    private ResponseType responseType;

    /**
     * @param url Url to be connected
     */
    public static Jolt connect(String url){
        jolt = new Jolt();
        jolt.url = "http://" + url;
        return jolt;
    }

    /**
     * @param responseType ResponseType defines the type of response from url. It can be JSON, XML or HTML.
     */
    public Jolt responseType(ResponseType responseType){
        jolt.responseType = responseType;
        return jolt;
    }

    /**
     * @param JoltConnectionListener Base listener for connection proccess.
     */
    public Jolt registerListener(JoltConnectionListener listener){
        jolt.listener = listener;
        new ConnectionHandler().execute();
        return jolt;
    }

    private void onConnectionSuccessful(String result){
        jolt.listener.onSuccess(result);
    }
    private void onConnectionFailed(){
        jolt.listener.onFail();

    }
    private void onConnectionStarted(){
        jolt.listener.onStart();
    }
    private void onConnectionFinished(){
        jolt.listener.onFinish();
    }

    private class ConnectionHandler extends AsyncTask<Void,Void,Void>{

        String result;

        @Override
        protected void onPreExecute() {
            onConnectionStarted();
        }

        @Override
        protected Void doInBackground(Void... params){

            if(responseType == ResponseType.JSON){
                getJSON(jolt.url);
            }else if(responseType == ResponseType.XML){
                getXML(jolt.url);
            }else if(responseType == ResponseType.HTML){
                getHTML(jolt.url);
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {


            onConnectionFinished();
        }

        public void getHTML(String url){

            InputStream inputStream = null;
            String htmlString = "";

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {

                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                inputStream.close();
                htmlString = sBuilder.toString();

                onConnectionSuccessful(htmlString);

            } catch (Exception e) {
                onConnectionFailed();
            }

        }

        public void getXML(String url){

            Document doc = null;

            try {
                URL u = new URL(url);
                URLConnection conn = u.openConnection();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                doc = builder.parse(conn.getInputStream());

                DOMSource domSource = new DOMSource(doc);
                StringWriter writer = new StringWriter();
                StreamResult streamResult = new StreamResult(writer);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.transform(domSource, streamResult);

                String resultingString = writer.toString();
                onConnectionSuccessful(resultingString);

            }catch (Exception e){
                onConnectionFailed();
            }

        }

        public void getJSON(String url) {

            InputStream inputStream = null;
            String resultString = "";

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {

                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                inputStream.close();
                resultString = sBuilder.toString();

                onConnectionSuccessful(resultString);

            } catch (Exception e) {
                onConnectionFailed();
            }


        }


    }

}

