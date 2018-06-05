import java.util.*;

public class SortEmployees {

    public Map<String,List<String>> sortByDesignation(List<EmpDetails> employeeDetailsList){
        Map<String,List<String>> DesignationSort = new HashMap<String, List<String>>();
        List<String> QAnames = new ArrayList<String>();
        List<String> FEnames = new ArrayList<String>();
        List<String> BEnames = new ArrayList<String>();
        for(EmpDetails empDetails : employeeDetailsList ){
            //System.out.println(empDetails.name);

            if(empDetails.designation.equals("QA")){
                QAnames.add(empDetails.name);
            }
            else if (empDetails.designation.equals("FE")|| empDetails.designation.equals("UIE")) {
                empDetails.setdesignation("FE");
                FEnames.add(empDetails.name);
            }else {
                BEnames.add(empDetails.name);
            }
        }
        DesignationSort.put("QA",QAnames);
        DesignationSort.put("BE",BEnames);
        DesignationSort.put("FE",FEnames);

       return  DesignationSort;
    }

    public Map<String,List<String>> employeePerProject(List<ProjectDetails> listProjectDetails, List<EmpDetails> employeeDetailsList){

        Map<String,List<String>> ProjectSort = new HashMap<String, List<String>>();
        for (ProjectDetails projectDetails: listProjectDetails){
            List<String> employeePerProject = new ArrayList<String>();
            System.out.println(projectDetails.getProjectName());
            for(EmpDetails empDetails: employeeDetailsList ){
                if(empDetails.getCurrentProject().equals(projectDetails.getProjectName())){
                    employeePerProject.add(empDetails.getName());
                }

            }

            ProjectSort.put(projectDetails.getProjectName(),employeePerProject);
        }

    return ProjectSort;
    }


}

