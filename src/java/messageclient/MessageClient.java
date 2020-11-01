/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageclient;

import entities.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author yhoucee
 */
public class MessageClient {
    
   public boolean sendMessage(Message message) throws Exception{
        String[] recipients = {"234" + message.getTo().substring(1)};
        String xmlrecipients = "";
        String username = "ugwuu2017@outlook.com";
        String apikey = "00e7cb17c57da0644861b650a5b592b72e2a4d95";
        String sendername = message.getSender();
        String msg = message.getBody();
        String flash = String.valueOf(message.getRouteID());
        String theoutput = "";
        String randmsgid = Double.toString(Math.random());
        for( int i =0; i < recipients.length; i++ ){
            xmlrecipients += "<gsm><msidn>"+ recipients[i] + "</msidn><msgid>" + randmsgid + "_" + i + "</msgid>" + "</gsm>";
        }
        String xmlrequest =
                "<SMS>\n"
                + "<auth>"
                + "<username>" + username + "</username>\n"
                + "<apikey>" + apikey + "</apikey>\n"
                + "</auth>\n"
                + "<message>"
                + "<sender>" + sendername + "</sender>\n"
                + "<messagetext>" + message.getBody() + "</messagetext>\n"
                + "<flash>" + flash + "</flash>\n"
                + "</message>\n"
                + "<recipients>\n"
                + xmlrecipients
                + "</recipients>\n"
                + "</SMS>";
        String theurl = "http://api.ebulksms.com:80/sendsms.xml";
        theoutput = postXMLData(xmlrequest, theurl);
        if(theoutput.contains("100")){
            return true;
        }
        else{
            return false;
        }
    } 
   /*public boolean sendMessage(Message message) throws MalformedURLException, IOException{
         String url = "https://Expressbulksms.com/api/send?&username=" + message.getUsername() + "&password" +
"d=" + message.getPassword() + "&sender=" + message.getSender() + "&to=234" + message.getTo().substring(1) + "&route=" + message.getRouteID() + "&body="
+ message.getBody();
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    int responseCode = 0;
                    responseCode = con.getResponseCode();
                    if(responseCode == 200 || responseCode == 202){
                        return true;
                    }else{
                        return false;
                    }
    } */

    private String postXMLData(String xmlrequest, String theurl) {
        System.out.println("XML: " + xmlrequest);
        System.out.println("url: " + theurl);
        String result = "";
        try {
            URL myurl = new URL(theurl);
            URLConnection urlconn = myurl.openConnection();
 
            urlconn.setRequestProperty("Content-Type", "text/xml");
            urlconn.setDoOutput(true);
            urlconn.setDoInput(true);
            urlconn.connect();
 
            //Create a writer to the url
            PrintWriter pw = new PrintWriter(urlconn.getOutputStream());
            pw.write(xmlrequest, 0, xmlrequest.length());
            pw.close();
 
            //Create a reader for the output of the connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                result = result + line + "\n";
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void main (String[] args){
        System.out.println("na here");
    }
}
