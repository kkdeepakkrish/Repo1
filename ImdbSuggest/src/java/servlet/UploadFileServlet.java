/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import Model.ExcelHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

/**
 * The servlet processes the input excel from previous survey containing users initial set of movies
 * Prepares a RecoList containing the users originally selected movies and the recommendations ranked in the order HIGH,MEDIUM ,LOW
 * @author KK
 */
@MultipartConfig
public class UploadFileServlet extends HttpServlet {
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
    
            
        //String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
    Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
    String fileName = filePart.getSubmittedFileName();
    //String fileName = extractFileName(filePart);
    System.out.println("kk_Filename=" + fileName);
   // filePart.write(savePath + File.separator +fileName);
//    String fname = FilenameUtils.getName(fileName);
//    String fprefix = FilenameUtils.getBaseName(fname) + "_";
//    String fsuffix = "." + FilenameUtils.getExtension(fname);
//    System.out.println("KK-- new file name = " + fname +"\t prefix = " + fprefix + "\t suffix = " + fsuffix);
    //File f = File.createTempFile(fprefix, fsuffix, uploadDir);
    File f = new File(uploadDir,"userInp.xlsx");
    try( InputStream is = filePart.getInputStream()) {
        Files.copy(is, f.toPath()); 
    }
    ExcelHandler et = new ExcelHandler(uploadDir+ "\\userInp.xlsx");
    // form the movie map
    et.doMovieMap();
    // form the users movie list
    et.doUserMap();
    // order the movies in the following range
        et.setHighestRatedinRange(ExcelHandler.RANGE_HIGH);
        et.setHighestRatedinRange(ExcelHandler.RANGE_MED);
        et.setHighestRatedinRange(ExcelHandler.RANGE_LOW);
        System.out.println("kk-print range -- >\n" + et.printSelectionMap(et.userSelectionMap));
        et.convertMovieSelectionMap();
        System.out.println("kk-print converted range -- >\n" + et.printSelectionMap(et.userSelectionConvoMap));
        request.setAttribute("userMap",et.userSelectionConvoMap);
        request.setAttribute("uploadDir",uploadDir);
        // pass it to Movie Servlet to create the userReco excel file
         RequestDispatcher dispatcher =
       getServletContext().getRequestDispatcher("/findMovie");
    dispatcher.forward(request, response);
//       
//filePart.write(f.getPath());
//  
//filePart.write(f.getAbsolutePath());

        
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
       // uploadDir = new File("F:\\TmpDir");
        Properties properties = new Properties();
        try {
            properties.load(getServletContext().getResourceAsStream("/WEB-INF/config.properties"));
            if(properties.getProperty("specifyuploadDir").equalsIgnoreCase("1")) {
             uploadDir= new File(properties.getProperty("uploadDir"));
            } else {
            uploadDir = (File)getServletContext().getAttribute("javax.servlet.context.tempdir");
        }
        } catch (IOException ex) {          
            Logger.getLogger(UploadFileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
