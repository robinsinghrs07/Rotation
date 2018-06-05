import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReadExcel {

    private static final String FILE_NAME = "/Users/mnaveid/Desktop/automation/AssignProjects/src/main/resources/res1.xlsx";

    public Map<String,String> makeMap(String key1, String key2, Map<String,String> newmap){
        newmap.put(key1,key2);
return newmap;
    }

    public List<EmpDetails> readAllocation() {

        List<EmpDetails> empDetailsList = new ArrayList<EmpDetails>() ;

        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            Boolean firstRun = true;
            int nameIndex = -1;
            int locationindex = -1;
            int designationindex = -1;
            Map<String, String> nameLocationMap = new HashMap<String, String>();
            Map<String, String> nameDesignationMap = new HashMap<String, String>();
            while (iterator.hasNext()) {
                String name = null;
                String location = null;
                String currentProj = null;
                String prevproj = null;
                String designation = null;
                String project = null;

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                int index = 0;


                while (cellIterator.hasNext()) {


                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if (currentCell.getStringCellValue().equals("name") && firstRun == true) {
                            nameIndex = index;
                            //System.out.print(currentCell.getStringCellValue() + "--" + nameIndex);
                        } else if (currentCell.getStringCellValue().equals("Location") && firstRun == true) {
                            locationindex = index;
                            //System.out.print(currentCell.getStringCellValue() + "--" + locationindex);
                        } else if (currentCell.getStringCellValue().equals("designation") && firstRun == true) {
                            designationindex = index;
                            //System.out.print(currentCell.getStringCellValue() + "--" + designationindex);
                        }
                            if (index == nameIndex && firstRun == false) {
                                name = currentCell.getStringCellValue();
                            }
                            if (index == locationindex && firstRun == false) {
                                location = currentCell.getStringCellValue();
                            }
                            if (index == designationindex && firstRun == false) {
                                designation = currentCell.getStringCellValue();
                            }
                            if (index>2 && firstRun==false && currentProj ==null){
                                currentProj=currentCell.getStringCellValue();
                            }else if (index>2 && firstRun==false && currentProj !=null ){
                                prevproj=currentProj;
                                currentProj=currentCell.getStringCellValue();
                            }



                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }
                    ++index;

                }
                if (name != null && location != null && firstRun==false) {

                    nameLocationMap = makeMap(name, location, nameLocationMap);
                    nameDesignationMap = makeMap(name, designation, nameDesignationMap);
                    EmpDetails emp;
                    emp = new EmpDetails();
                    emp.setName(name);
                    emp.setdesignation(designation);
                    emp.setLocation(location);
                    emp.setCurrentProject(currentProj);
                    emp.setPrevProject(prevproj);
                    empDetailsList.add(emp);
                }
               // System.out.println(empDetailsList.toString());
                firstRun = false;

            }
            //System.out.println(Collections.singletonList(nameLocationMap));
           // System.out.println(Collections.singletonList(nameDesignationMap));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empDetailsList;
    }

    public List<ProjectDetails> readProjectresource(){
        List<ProjectDetails> listProjectDetails = new ArrayList<ProjectDetails>();
        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(1);
            Iterator<Row> iterator = datatypeSheet.iterator();

            int nameProjectindex=-1;
            int locationProjectindex =-1;
            int qaindex = -1;
            int beindex= -1;
            int feindex = -1;
            int typeOfProjectindex = -1;
            Boolean firstRun= true;
            while (iterator.hasNext()) {

                String projName=null,projLocation = null,typeOfProject = null;
                int qaCount=0,beCount=0,feCount =0;


                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                int index = 0;
                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                   // Location	QA	BE	FE
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if (currentCell.getStringCellValue().equals("Team Name") && firstRun == true) {
                            nameProjectindex = index;
                        }
                        if (currentCell.getStringCellValue().equals("Location") && firstRun == true) {
                                locationProjectindex = index;
                        }
                        if (currentCell.getStringCellValue().equals("TypeOfProject") && firstRun == true) {
                            typeOfProjectindex = index;
                        }
                        if (currentCell.getStringCellValue().equals("QA") && firstRun == true) {
                            qaindex = index;
                        }
                        if (currentCell.getStringCellValue().equals("BE") && firstRun == true) {
                            beindex = index;
                        }
                        if (currentCell.getStringCellValue().equals("FE") && firstRun == true) {
                            feindex = index;
                        }
                    }

                        if (index == nameProjectindex && firstRun == false) {
                            projName = currentCell.getStringCellValue();
                        }
                    if (index == locationProjectindex && firstRun == false) {
                        projLocation = currentCell.getStringCellValue();
                    }
                    if (index == typeOfProjectindex && firstRun == false) {
                        typeOfProject = currentCell.getStringCellValue();
                    }
                    if (index == qaindex && firstRun == false) {
                        qaCount = (int)currentCell.getNumericCellValue();
                    }
                    if (index == beindex && firstRun == false) {
                        beCount = (int)currentCell.getNumericCellValue();
                    }
                    if (index == feindex && firstRun == false) {
                        feCount = (int)currentCell.getNumericCellValue();
                    }
                    ++index;
                }
                if(firstRun==false) {
                    ProjectDetails projectDetails = new ProjectDetails();
                    projectDetails.setProjectName(projName);
                    projectDetails.setProjectLocation(projLocation);
                    projectDetails.setProjQACount(qaCount);
                    projectDetails.setProjBECount(beCount);
                    projectDetails.setProjFECount(feCount);
                    projectDetails.setTypeOfProject(typeOfProject);
                    listProjectDetails.add(projectDetails);
                    //System.out.println(listProjectDetails.toString());
                }

                firstRun = false;

        }


        } catch (Exception e){
            e.printStackTrace();
        }
        return listProjectDetails;
    }
}



