/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author KK
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * This servlet performs operations related to the excel sheet
 * @author KK
 */
public class ExcelHandler {

    private  Map<String, String> movieMap = null;
    private  Map userMap = null;
    public  Map<String, ArrayList> userSelectionMap = new HashMap<String, ArrayList>();
    public static final String RANGE_HIGH = "RANGE_HIGH";
    public static final String RANGE_MED = "RANGE_MED";
    public static final String RANGE_LOW = "RANGE_LOW";
    public  Map<String, ArrayList> userSelectionConvoMap = new HashMap<String, ArrayList>();
    private String fileName;

    public ExcelHandler(String fname) {
    fileName = fname;
    }

    public static void main(String args[]) {
        
        ExcelHandler ee =new ExcelHandler("");
        ee.doMovieMap();
        ee.doUserMap();
        ee.setHighestRatedinRange(RANGE_HIGH);
        ee.setHighestRatedinRange(RANGE_MED);
        ee.setHighestRatedinRange(RANGE_LOW);
        System.out.println("kk-print range -- >\n" + ee.printSelectionMap(ee.userSelectionMap));
        ee.convertMovieSelectionMap();
        System.out.println("kk-print converted range -- >\n" + ee.printSelectionMap(ee.userSelectionConvoMap));

    }

     public  void convertMovieSelectionMap() {
         for (Iterator it = userSelectionMap.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String user = (String) entry.getKey();
                    ArrayList<String> valueList = (ArrayList) entry.getValue();
                    ArrayList<String> convertedList = new ArrayList();
                    int emptyCount = 0;
                    for (String i : valueList) {
                        if(!i.equals("EMPTY")) {
                        convertedList.add(movieMap.get(i));
                        } else {
                            convertedList.add("EMPTY");
                            emptyCount++;
                        }
                    }
                    if(emptyCount != 3) {
          userSelectionConvoMap.put(user, convertedList);
                    }
         }
     }
    /**
     * Form a map from the excel sheet for the movie id's and names
     */
     public  void doMovieMap() {
        try {
//String fileName="C:\\Users\\KK\\Documents\\NetBeansProjects\\TestJava\\src\\testjava\\TestFile.xlsx";
//File file=new File("C:\\Users\\KK\\Documents\\NetBeansProjects\\TestJava\\src\\testjava\\TestFile.xlsx");
           // FileInputStream fin = new FileInputStream(new File("C:\\Users\\KK\\Documents\\NetBeansProjects\\TestJava\\src\\testjava\\Test File.xlsx"));
 FileInputStream fin = new FileInputStream(new File(fileName));
//Get the workbook instance for XLS file
            XSSFWorkbook workbook = new XSSFWorkbook(fin);

//Get first sheet from the workbook //for xsls -office 2007 and above otherwise HSSF
            XSSFSheet sheet = workbook.getSheetAt(1);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            movieMap = new HashMap<String, String>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                int cellCount = 1;
                Cell prev = null;
                Cell curr = null;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    curr = cell;

                    if (cellCount % 2 == 0) {
                        movieMap.put(prev.getRichStringCellValue().toString(), curr.getRichStringCellValue().toString());
                        prev = null;
                        curr = null;
                    } else {
                        prev = cell;
                    }
                    cellCount++;
//        switch(cell.getCellType()) 
//            {
//      case Cell.CELL_TYPE_BOOLEAN:
//         System.out.print(cell.getBooleanCellValue() + "\t\t");
//         break;
//      case Cell.CELL_TYPE_NUMERIC:
//         System.out.print(cell.getNumericCellValue() + "\t\t");
//         break;
//      case Cell.CELL_TYPE_STRING:
//         System.out.print(cell.getRichStringCellValue() + "\t\t");
//         break;
//    }
                }

            }
            System.out.println("KK --print map \n" + printMap(movieMap));
            fin.close();
//FileOutputStream out =new FileOutputStream(file);
//workbook.write(out); 
//out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void setHighestRatedinRange(String range) {
        
        switch (range) {
            case ExcelHandler.RANGE_HIGH: {

                for (Iterator it = userMap.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String user = (String) entry.getKey();
                    Map vaueMap = (TreeMap) entry.getValue();

                    int count = 0;
                    for (Iterator it3 = vaueMap.entrySet().iterator(); it3.hasNext();) {
                        Map.Entry entry3 = (Map.Entry) it3.next();
                        String key = String.valueOf(entry3.getKey());
                        Double value = (Double) entry3.getValue();
                        if (value > 80 && value <= 100) {
                            if (userSelectionMap.get(user) == null) {
                                ArrayList nn = new ArrayList();
                                nn.add(key);
                                userSelectionMap.put(user, nn);
                            } else {
                                userSelectionMap.get(user).add(key);
                            }
                        } else {
                            if (userSelectionMap.get(user) == null) {
                                ArrayList nn = new ArrayList();
                                nn.add("EMPTY");
                                userSelectionMap.put(user, nn);
                            } else {
                                userSelectionMap.get(user).add("EMPTY");
                            }
                        }
                        break;
                        

                    }

                    //stateString += "{" + key + ":" + retVal + "} \n";
                }

                break;

            }
            
            case ExcelHandler.RANGE_MED: {

                for (Iterator it = userMap.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String user = (String) entry.getKey();
                    Map vaueMap = (TreeMap) entry.getValue();

                    int count = 0;
                    boolean added =false;
                    for (Iterator it3 = vaueMap.entrySet().iterator(); it3.hasNext();) {
                        Map.Entry entry3 = (Map.Entry) it3.next();
                        String key = String.valueOf(entry3.getKey());
                        Double value = (Double) entry3.getValue();
                        if (value > 60 && value <= 80) {
                            if (userSelectionMap.get(user) == null) {
                                ArrayList nn = new ArrayList();
                                nn.add(key);
                                userSelectionMap.put(user, nn);
                                
                            } else {
                                userSelectionMap.get(user).add(key);
                            }
                            added =true;
                        break;
                        } else if(value <= 60) {
                            if (userSelectionMap.get(user) == null) {
                                ArrayList nn = new ArrayList();
                                nn.add("EMPTY");
                                userSelectionMap.put(user, nn);
                            } else {
                                userSelectionMap.get(user).add("EMPTY");
                            }
                            added =true;
                            break;
                        }
                        
                        

                    }
                      if(!added) {
                           if (userSelectionMap.get(user) == null) {
                                ArrayList nn = new ArrayList();
                                nn.add("EMPTY");
                                userSelectionMap.put(user, nn);
                            } else {
                                userSelectionMap.get(user).add("EMPTY");
                            }
                      }
                    //stateString += "{" + key + ":" + retVal + "} \n";
                }

                break;

            }
            
            case ExcelHandler.RANGE_LOW: {
                 
                for (Iterator it = userMap.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String user = (String) entry.getKey();
                    Map vaueMap = (TreeMap) entry.getValue();
                    double min = 99999;
                    String minKey = "";
                    boolean added =false;
                    for (Iterator it3 = vaueMap.entrySet().iterator(); it3.hasNext();) {
                        Map.Entry entry3 = (Map.Entry) it3.next();
                        String key = String.valueOf(entry3.getKey());
                        Double value = (Double) entry3.getValue();
                        if (value > 40 && value <= 60 && Math.abs(50 - value) <= min) {
                            min = Math.abs(50 - value);

                            minKey = key;
                            added =true;
                        } else if(value <= 40 && minKey.equals("")) {
                            if (userSelectionMap.get(user) == null) {
                                ArrayList nn = new ArrayList();
                                nn.add("EMPTY");
                                userSelectionMap.put(user, nn);
                            } else {
                                userSelectionMap.get(user).add("EMPTY");
                            }
                            added =true;
                            break;
                        }
                        
                        

                    }
                    if(!minKey.equals("")) {
                    if (userSelectionMap.get(user) == null) {
                                ArrayList nn = new ArrayList();
                                nn.add(minKey);
                                userSelectionMap.put(user, nn);
                                
                            } else {
                                userSelectionMap.get(user).add(minKey);
                            }
                    }
                    
                    if(!added) {
                        if (userSelectionMap.get(user) == null) {
                                ArrayList nn = new ArrayList();
                                nn.add("EMPTY");
                                userSelectionMap.put(user, nn);
                            } else {
                                userSelectionMap.get(user).add("EMPTY");
                            }
                    }
                    //stateString += "{" + key + ":" + retVal + "} \n";
                }

                break;

            }
            
        }

        
    }
/**
 * For each user
 */
    public  void doUserMap() {
        try {
//String fileName="C:\\Users\\KK\\Documents\\NetBeansProjects\\TestJava\\src\\testjava\\TestFile.xlsx";
//File file=new File("C:\\Users\\KK\\Documents\\NetBeansProjects\\TestJava\\src\\testjava\\TestFile.xlsx");
            //FileInputStream fin = new FileInputStream(new File("C:\\Users\\KK\\Documents\\NetBeansProjects\\TestJava\\src\\testjava\\Test File.xlsx"));
FileInputStream fin = new FileInputStream(new File(fileName));
//Get the workbook instance for XLS file
            XSSFWorkbook workbook = new XSSFWorkbook(fin);

//Get first sheet from the workbook //for xsls -office 2007 and above otherwise HSSF
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            userMap = new HashMap();
            int rowCount = 0;

            Map moviesListMap = new HashMap<Integer, String>();
            int userIdIndex = -1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (rowCount == 0) {
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        String val = cell.getRichStringCellValue().toString();
                        if (val.indexOf("MID") != -1) {
                            userIdIndex = cell.getColumnIndex();
                        }
                        if (val.indexOf("sinterest") != -1) {

//                            movieListStartIndex = cell.getColumnIndex();
//                            moviesListMap.put(movieListStartIndex, val);
                            moviesListMap.put(cell.getColumnIndex(), val);

                        }
                    }
                } else if (rowCount == 1) {
                    rowCount++;
                    continue;
                } else {
                    //System.out.println("kk -- UserIdIndex=" + userIdIndex);
                    TreeMap usersMovieRatingMap = new TreeMap<String, Double>();
               // iterate movie list map

                    for (Iterator it = moviesListMap.entrySet().iterator(); it.hasNext();) {
                        Map.Entry entry = (Map.Entry) it.next();
                        Integer key = (Integer) entry.getKey();
                        String value = ((String) entry.getValue());
        //                System.out.println("kk --MovColIndex=" + key);
          //              System.out.println("kk --MovColVal=" + value);
                        Cell userMovCell = row.getCell(key);
                        if (userMovCell != null && userMovCell.getCellType() != Cell.CELL_TYPE_BLANK) {
                            try {
                                double cellRating = userMovCell.getNumericCellValue();
                                //usersMovieRatingMap.put(cellRating, value);
                                usersMovieRatingMap.put(value, cellRating);
                            } catch (NumberFormatException te) {
                                System.out.println("unable to convert to integer");
                            }
                        }
            //usersMovieRatingMap.put(key, value);
                        //stateString += "{" + key + ":" + value + "} ";
                    }
                    Map finalMap = sortByValues(usersMovieRatingMap);
                    userMap.put(row.getCell(userIdIndex).getStringCellValue(), finalMap);
//Iterator<Cell> cellIterator2 = row.cellIterator();
                }
                rowCount++;

            }
            System.out.println("KK --print user map \n" + printUserMap(userMap));
            fin.close();
//FileOutputStream out =new FileOutputStream(file);
//workbook.write(out); 
//out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  String printMap(Map state) {
        String stateString = "";
        for (Iterator it = state.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            stateString += "[[" + key + ":" + value + "]] ";
        }
        return stateString;
    }

    public  String printUserMap(Map state) {
        String stateString = "";
        for (Iterator it = state.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Map value = (TreeMap) entry.getValue();
            String retVal = printMap(value);
            stateString += "{" + key + ":" + retVal + "} \n";
        }
        return stateString;
    }
    
    public  String printSelectionMap(Map state) {
        String stateString = "";
        for (Iterator it = state.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            List value = (ArrayList) entry.getValue();
            String retVal = printList(value);
            stateString += "{" + key + ":" + retVal + "} \n";
        }
        return stateString;
    }

    
    public  String printList(List<String> state) {
        String stateString = "";
        for(String i : state) {
            stateString += "[[" + i + "]] ";
        }
        
        return stateString;
    }
    public  <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) {
                    return 1;
                } else {
                    return compare;
                }
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

}
