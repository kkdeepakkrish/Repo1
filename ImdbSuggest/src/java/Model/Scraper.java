/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * The module which scrapes the recommendation for a particular movie from IMDB(Will hold as long as the structure of IMDB movie  pages do not change)
 * 1) Perform the query on imdb with the movie name -> page with list of movie names matching the input string
 * 2) Take the first movie in the result and use its id to proceed to its  movie details
 * ***IMPORTANT***
 *      The movie name passed as input should EXACTLY match the names in IMDB
 * @author KK
 */
public class Scraper {
    public Movie doMovieSearch(String search) {
         String response = "";
      Movie ret = null;
        try {
            // Create a URL for the desired page            
            String[] sParams = search.split(" ");
			String modifiedParam = "";
			if (sParams != null && sParams.length > 1) {

				for (int i = 0; i < sParams.length; i++) {
					modifiedParam = modifiedParam + "+" + sParams[i];
				}
			}else{
				modifiedParam = search;
			}
                        
                        
            URL url = new URL("http://www.imdb.com/find?ref_=nv_sr_fn&q=" + modifiedParam );
            // Create an HttpURLConnection.  This is useful for setting headers.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            connection.setRequestProperty("Accept", "text/plain");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            
            
            
            in.close();
           
        } catch (IOException e) {

        }
        
        Document doc = Jsoup.parse(response);
        //scrape from particular element
        //first the screen will hold a list of movies with the name/parts of name
        // we need to select the appropriate(first matching) movie from the list and proceed to the screen displaying details for the movie
        Elements movElem = doc.select("table.findList > tbody > tr >td.result_text >a");
   
        String movLink= "http://www.imdb.com";
        boolean foundMov = false;
        // append the first movie's id to the imdb url 
        for (Element link : movElem) {
            String relHref = link.attr("href"); // == "/"
           if(link.text().equalsIgnoreCase(search)) {
               movLink += relHref;
               foundMov =true;
               break;
           }
             
             

        }
        if(foundMov) {
        //System.out.println("KK-----" + movLink);
        ret = getRecommendedMovie(movLink);
        }
       // return response;
        return ret;
    }
    
    public Movie getRecommendedMovie(String urlLink) {
        Movie m = null;
        String response = "";
        try {
              URL url = new URL(urlLink );
            // Create an HttpURLConnection.  This is useful for setting headers.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            connection.setRequestProperty("Accept", "text/plain");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            
            
            in.close();
        }catch (IOException e) {
            
        }
    
    Document doc = Jsoup.parse(response);
    // get the element for the "People also liked section"
        Elements movList = doc.select("div.rec_item > a");
        //String newurl = movElem.data();
        //System.out.println("KK -- movie url is ====" + newurl);
         //System.out.println("\n KK---Links:" + movElem.size());
//        String relHref = movElem.attr("href");
//        System.out.println(" link === \t" + relHref + "\t" + movElem.text() +"\n");
        Element recoMov= null;
        String movLink= "http://www.imdb.com";
        
        if(movList.size() != 0){
            recoMov = movList.first();
           String href = recoMov.attr("href");
           String[] sgSplit = href.split("/");
          
           //System.out.println("KK--got recomovie link = " + href);
           m =new Movie();
            if(sgSplit[2] != null) {
           m.setId(sgSplit[2]);
           }
           m.setLink(movLink + href);
           Element imageTag = recoMov.child(0);
           if(imageTag != null) {
            String title = imageTag.attr("title");
            String imgSrc = imageTag.attr("loadlate");
            imgSrc =imgSrc.replaceAll("(.*\\.)(.*\\.)(.*)","$1$3");
            //System.out.println("KK--got recomovie title = " + title);
            //System.out.println("KK--got recomovie link = " + imgSrc);
            m.setThumb(imgSrc);
            m.setTitle(title);
        }
        }
        return m;
       
    }
}
