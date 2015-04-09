/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import Model.Movie;
import Model.Scraper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Parse the intermediate userRecoMap to create the userReco excel file
 * @author KK
 */
@WebServlet(name = "MovieServlet", urlPatterns = {"/findMovie"})
public class MovieServlet extends HttpServlet {

    Scraper sm = null;
    public  Map<String, ArrayList<Movie>> userRecoMap = new HashMap<String, ArrayList<Movie>>();
    private File uploadDir;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        // get the search parameter if it exists
        //String search = request.getParameter("searchWord");
         Map<String, ArrayList> userMap = (HashMap<String, ArrayList>)request.getAttribute("userMap");
         uploadDir = (File)request.getAttribute("uploadDir");
        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        String nextView;
        
        if (userMap != null) {
            
            for (Iterator it = userMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            //userId
            String key = (String) entry.getKey();
            List<String> value = (ArrayList<String>) entry.getValue();
            ArrayList<Movie> recoList = new ArrayList<Movie>();
            for(String searchStr : value) {
                if(!"EMPTY".equalsIgnoreCase(searchStr)) {
                    //recommendation for the particular movie in the range
            Movie rec = sm.doMovieSearch(searchStr.trim());
            if(rec == null) {
            System.out.println("KK -null value for" + searchStr);
            }else {
                rec.setSuggestedFor(searchStr.trim());
            }
            recoList.add(rec);
                } else {
                   
                   recoList.add(null);
                }
        }
            userRecoMap.put(key, recoList);
            
            
            
        }
            printMap(userRecoMap);
            createExcel();
            
          

    }
    }

    /**
     * creates the excel sheet
     */
    private void createExcel() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Recommendations");
         Row initrow = sheet.createRow(0);
         initrow.createCell(0).setCellValue("UserId");
         initrow.createCell(1).setCellValue("User_Selection_Movie1-Title");
         initrow.createCell(2).setCellValue("Reco_Movie1-Id");
         initrow.createCell(3).setCellValue("Reco_Movie1-Title");
         initrow.createCell(4).setCellValue("Reco_Movie1-Link");
         initrow.createCell(5).setCellValue("Reco_Movie1-Image");
         
         initrow.createCell(6).setCellValue("User_Selection_Movie2-Title");
         initrow.createCell(7).setCellValue("Reco_Movie2-Id");
         initrow.createCell(8).setCellValue("Reco_Movie2-Title");
         initrow.createCell(9).setCellValue("Reco_Movie2-Link");
         initrow.createCell(10).setCellValue("Reco_Movie2-Image");
         
         initrow.createCell(11).setCellValue("User_Selection_Movie3-Title");
         initrow.createCell(12).setCellValue("Reco_Movie3-Id");
         initrow.createCell(13).setCellValue("Reco_Movie3-Title");
         initrow.createCell(14).setCellValue("Reco_Movie3-Link");
         initrow.createCell(15).setCellValue("Reco_Movie3-Image");
         
         //change for incorporating additional movies with images
         //***********************
         initrow.createCell(16).setCellValue("User_Selection_Movie4-Title");
         initrow.createCell(17).setCellValue("Reco_Movie4-Id");
         initrow.createCell(18).setCellValue("Reco_Movie4-Title");
         initrow.createCell(19).setCellValue("Reco_Movie4-Link");
         initrow.createCell(20).setCellValue("Reco_Movie4-Image");
         
         initrow.createCell(21).setCellValue("User_Selection_Movie5-Title");
         initrow.createCell(22).setCellValue("Reco_Movie5-Id");
         initrow.createCell(23).setCellValue("Reco_Movie5-Title");
         initrow.createCell(24).setCellValue("Reco_Movie5-Link");
         initrow.createCell(25).setCellValue("Reco_Movie5-Image");
         
         initrow.createCell(26).setCellValue("User_Selection_Movie6-Title");
         initrow.createCell(27).setCellValue("Reco_Movie6-Id");
         initrow.createCell(28).setCellValue("Reco_Movie6-Title");
         initrow.createCell(29).setCellValue("Reco_Movie6-Link");
         initrow.createCell(30).setCellValue("Reco_Movie6-Image");
         ////////////////////
         initrow.createCell(31).setCellValue("User_Selection_Movie7-Title");
         initrow.createCell(32).setCellValue("Reco_Movie7-Id");
         initrow.createCell(33).setCellValue("Reco_Movie7-Title");
         initrow.createCell(34).setCellValue("Reco_Movie7-Link");
         initrow.createCell(35).setCellValue("Reco_Movie7-Image");
         
         initrow.createCell(36).setCellValue("User_Selection_Movie8-Title");
         initrow.createCell(37).setCellValue("Reco_Movie8-Id");
         initrow.createCell(38).setCellValue("Reco_Movie8-Title");
         initrow.createCell(39).setCellValue("Reco_Movie8-Link");
         initrow.createCell(40).setCellValue("Reco_Movie8-Image");
         
         initrow.createCell(41).setCellValue("User_Selection_Movie9-Title");
         initrow.createCell(42).setCellValue("Reco_Movie9-Id");
         initrow.createCell(43).setCellValue("Reco_Movie9-Title");
         initrow.createCell(44).setCellValue("Reco_Movie9-Link");
         initrow.createCell(45).setCellValue("Reco_Movie9-Image");
         
         
         //**********************
        int rownum = 1;
         for (Iterator it = userRecoMap.entrySet().iterator(); it.hasNext();) {
             Row row = sheet.createRow(rownum);
             rownum++;
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            row.createCell(0).setCellValue(key);
            List<Movie> value = (ArrayList<Movie>) entry.getValue();
            int cellnum=1;
            for(Movie m : value) {
                if(m != null) {
                row.createCell(cellnum++).setCellValue(m.getSuggestedFor());                
                row.createCell(cellnum++).setCellValue(m.getId());                
                row.createCell(cellnum++).setCellValue(m.getTitle());
                row.createCell(cellnum++).setCellValue(m.getLink());
                row.createCell(cellnum++).setCellValue(m.getThumb());
                } else {
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");                
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");
                }
            
        }
            
            //change for addiotnal movies
            for(Movie m : value) {
                if(m != null) {
                row.createCell(cellnum++).setCellValue(m.getSuggestedFor());                
                row.createCell(cellnum++).setCellValue(m.getId());                
                row.createCell(cellnum++).setCellValue(m.getTitle());
                row.createCell(cellnum++).setCellValue(m.getLink());
                row.createCell(cellnum++).setCellValue(m.getThumb());
                } else {
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");                
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");
                }
            
        }
            for(Movie m : value) {
                if(m != null) {
                row.createCell(cellnum++).setCellValue(m.getSuggestedFor());                
                row.createCell(cellnum++).setCellValue(m.getId());                
                row.createCell(cellnum++).setCellValue(m.getTitle());
                row.createCell(cellnum++).setCellValue(m.getLink());
                row.createCell(cellnum++).setCellValue(m.getThumb());
                } else {
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");                
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");
                row.createCell(cellnum++).setCellValue("");
                }
            
        }
            //***
            
            
        }
         
          try {
            FileOutputStream out = 
                    new FileOutputStream(new File(uploadDir,"UserReco.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void printMap(Map m) {
        String stateString = "";
        for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            List value = (ArrayList) entry.getValue();
            String retVal = printMovieList(value);
            stateString += "{" + key + ":" + retVal + "} \n";
        }
        System.out.println("KK -- recco map  --\n" + stateString);
    }
    public  String printMovieList(List<Movie> state) {
        String stateString = "";
        for(Movie i : state) {
            stateString += "[[" + i + "]] ";
        }
        
        return stateString;
    }
            
    @Override
    public void init() throws ServletException {
        sm = new Scraper();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
