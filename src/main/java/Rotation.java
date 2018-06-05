import java.util.*;

public class Rotation {
    Queue<String> rotationEmployee = new LinkedList<String>();
    Queue<String> priorityOpenings = new LinkedList<String>();

    public List<EmpDetails> EmployeeInOneProject(List<String> empInOneProj, Map<String, List<String>> designationSort, String role, List<EmpDetails> employeeDetailsList, String listProjectDetails) {
        List<EmpDetails> empInOneProjSameRole = new ArrayList<EmpDetails>();

        for (String empname : empInOneProj) {
            if (designationSort.get(role).contains(empname)) {
                Boolean flag = false;
                for (EmpDetails empDetails : employeeDetailsList) {
                    if (flag) {
                        break;
                    }
                    if (empDetails.getName().equals(empname) && empDetails.getdesignation().equals(role) && empDetails.getCurrentProject().equals("")) {
                        flag = true;
                        empInOneProjSameRole.add(empDetails);
                    }
                }
            }
        }
        return empInOneProjSameRole;
    }

    public List<EmpDetails> EmpTwoMonthCont(List<EmpDetails> empInOneProjSameRole) {
        List<EmpDetails> empTwoMonthCont = new ArrayList<EmpDetails>();
        for (EmpDetails empDetails : empInOneProjSameRole) {
            if (empDetails.getCurrentProject().equals(empDetails.getPrevProject())) {
                empTwoMonthCont.add(empDetails);
            }

        }
        return empTwoMonthCont;
    }
    public int getRolecount(String role, ProjectDetails projectDetails){//checking
        if(role.equals("QA")){
            return projectDetails.getProjQACount();
        }
        if(role.equals("FE")){
            return  projectDetails.getProjFECount();
        }
        if(role.equals("BE")){
            return projectDetails.getProjBECount();
        }
        return 0;
    }

    public void addToRotationPerLocation(int countToMove, Map<String, Integer> locationCountRotation, List<EmpDetails> empDetail) {
        int addToRotation = 0;
        for (EmpDetails empDetails : empDetail) {

            if (locationCountRotation.get(empDetails.getLocation()) != null) {
                int locationRampup = locationCountRotation.get(empDetails.getLocation());
                if (locationRampup > 0 && addToRotation < countToMove) {
                    --locationRampup;
                    locationCountRotation.put(empDetails.getLocation(), locationRampup);
                    rotationEmployee.add(empDetails.getName());
                    ++addToRotation;
                }
            }
        }
        if (addToRotation != countToMove) {
            for (EmpDetails empDetails : empDetail) {
                {
                    rotationEmployee.add(empDetails.getName());
                    ++addToRotation;
                    if (addToRotation == countToMove) {
                        break;
                    }
                }
            }
        }
    }

    public void addAllToRotation(int peopleToMove, List<EmpDetails> emp2MonthsSameProj) {
        for (EmpDetails empDetails : emp2MonthsSameProj) {
            rotationEmployee.add(empDetails.getName());
        }
    }


    public void checkteamwise(List<ProjectDetails> listProjectDetails, Map<String, List<String>> employeePerProjectSort, String role, Map<String, List<String>> designationSort, List<EmpDetails> employeeDetailsList, Map<String, Integer> locationCountRotation) {

        for (ProjectDetails projectDetails : listProjectDetails) {   //1 project
            if (projectDetails.getTypeOfProject().equalsIgnoreCase("Rotation")) {
                if (projectDetails.getProjectLocation().equalsIgnoreCase("all")) {  //any location
                    System.out.println(projectDetails.getProjectName());
                    List<String> empInOneProj = employeePerProjectSort.get(projectDetails.getProjectName()); //map get project name
                    List<EmpDetails> empInOneProjSameRole = EmployeeInOneProject(empInOneProj, designationSort, role, employeeDetailsList, projectDetails.projectName);
                    List<EmpDetails> Emp2MonthsSameProj = EmpTwoMonthCont(empInOneProjSameRole);
                    int roleCount = getRolecount(role, projectDetails);
                    // Currrent Allocation equal to Rotation List i.e 2+Month

                    //Current Allocation Greater than new Allocation.
                    if (empInOneProjSameRole.size() > roleCount) {
                        int totalRampUp = empInOneProjSameRole.size() - roleCount;
                        if (Emp2MonthsSameProj.size() < totalRampUp) {
                            int peopleToMove = totalRampUp - Emp2MonthsSameProj.size();
                            addAllToRotation(Emp2MonthsSameProj.size(), Emp2MonthsSameProj);
                            addToRotationPerLocation(peopleToMove, locationCountRotation, empInOneProjSameRole);

                        } else if (Emp2MonthsSameProj.size() == totalRampUp) {// 6-4-2 // remove 2 which have 2 Months already
                            int peopleToMove = totalRampUp;
                            addAllToRotation(peopleToMove, Emp2MonthsSameProj);
                        } else if (Emp2MonthsSameProj.size() > totalRampUp && Emp2MonthsSameProj.size() != empInOneProjSameRole.size()) { // 6-4-3 rampup 6-4=2<3 i.e 6 is old value .. 4 is new Value
                            int opening = Emp2MonthsSameProj.size() - totalRampUp;
                            addAllToRotation(Emp2MonthsSameProj.size(), Emp2MonthsSameProj);
                        } else if ((Emp2MonthsSameProj.size() > totalRampUp && Emp2MonthsSameProj.size() == empInOneProjSameRole.size())) {
                            int peopleToMove = totalRampUp + totalRampUp / 2;
                            addToRotationPerLocation(peopleToMove, locationCountRotation, Emp2MonthsSameProj);
                        }
                    } else if (empInOneProjSameRole.size() < roleCount) {
                        int totalRampUp = roleCount - empInOneProjSameRole.size();

                        if (Emp2MonthsSameProj.size() < totalRampUp && Emp2MonthsSameProj.size() != empInOneProjSameRole.size()) { //4,6,1  put 1 in rotate and add 1 to total Ramp-up

                            addAllToRotation(Emp2MonthsSameProj.size(), Emp2MonthsSameProj);
                            int openings = totalRampUp + Emp2MonthsSameProj.size();

                        } else if (Emp2MonthsSameProj.size() < totalRampUp && Emp2MonthsSameProj.size() == empInOneProjSameRole.size()) {// 2,5,2 if we remove 2 no employee will be left hence removing only 50%
                            int peopleToMove = Emp2MonthsSameProj.size() / 2; // To make overlapping purposefully removing
                            int opening = Emp2MonthsSameProj.size() / 2;
                            addToRotationPerLocation(peopleToMove, locationCountRotation, Emp2MonthsSameProj);

                        } else if (Emp2MonthsSameProj.size() == totalRampUp && Emp2MonthsSameProj.size() != empInOneProjSameRole.size()) {// 5-8-3 // remove  3 common purposefully and also 1(5-3)/2 from 5 to maintain overlap
                            int peopleToMove = totalRampUp + (empInOneProjSameRole.size() - Emp2MonthsSameProj.size()) / 2; // To make overlapping purposefully removing
                            int opening = empInOneProjSameRole.size() - Emp2MonthsSameProj.size() / 2;
                            addAllToRotation(totalRampUp, Emp2MonthsSameProj);
                            addToRotationPerLocation((empInOneProjSameRole.size() - Emp2MonthsSameProj.size() / 2), locationCountRotation, empInOneProjSameRole);

                        } else if (Emp2MonthsSameProj.size() == totalRampUp && Emp2MonthsSameProj.size() == empInOneProjSameRole.size()) {
                            int peopleToMove = Emp2MonthsSameProj.size() / 2; // To make overlapping purposefully removing
                            int opening = Emp2MonthsSameProj.size() / 2;
                            addToRotationPerLocation(peopleToMove, locationCountRotation, Emp2MonthsSameProj);
                        } else if (Emp2MonthsSameProj.size() > totalRampUp && Emp2MonthsSameProj.size() != empInOneProjSameRole.size()) { // 4-5-3 rampup,  remove all 3  get 3+ rampup openings
                            int opening = totalRampUp + Emp2MonthsSameProj.size();
                            addAllToRotation(Emp2MonthsSameProj.size(), Emp2MonthsSameProj);
                        } else if ((Emp2MonthsSameProj.size() > totalRampUp && Emp2MonthsSameProj.size() == empInOneProjSameRole.size())) {  //4,6,4 here we only reduce the rampup
                            int peopleToMove = totalRampUp;
                            int opening = 0;
                            addToRotationPerLocation(peopleToMove, locationCountRotation, Emp2MonthsSameProj);
                        }
                    } else if (empInOneProjSameRole.size() == roleCount) {
                        if (Emp2MonthsSameProj.size() == empInOneProjSameRole.size() && roleCount != 1) {//All common
                            int peopleToMove = Emp2MonthsSameProj.size() / 2;
                            int opening = peopleToMove;
                            addToRotationPerLocation(peopleToMove, locationCountRotation, Emp2MonthsSameProj);
                        } else if (Emp2MonthsSameProj.size() == empInOneProjSameRole.size() && roleCount == 1) {//All common
                            int peopleToMove = Emp2MonthsSameProj.size();
                            int opening = peopleToMove;
                            addAllToRotation(Emp2MonthsSameProj.size(), Emp2MonthsSameProj);
                        } else if (Emp2MonthsSameProj.size() == 0 && empInOneProjSameRole.size() != 1) {
                            int peopleToMove = empInOneProjSameRole.size() / 2;
                            int opening = peopleToMove;
                            addToRotationPerLocation(peopleToMove, locationCountRotation, empInOneProjSameRole);

                        } else if (Emp2MonthsSameProj.size() == 0 && empInOneProjSameRole.size() == 1) {
                        } else if (empInOneProjSameRole.size() > Emp2MonthsSameProj.size() && Emp2MonthsSameProj.size() == 0) {//generic}
                            int peopleToMove = Emp2MonthsSameProj.size();
                            int opening = peopleToMove;
                            addAllToRotation(Emp2MonthsSameProj.size(), Emp2MonthsSameProj);
                        }

                    }
                }
            }
        }


    }


    public void rotateFree(List<EmpDetails> employeeDetailsList) {
        for (EmpDetails empDetails : employeeDetailsList) {
            if (empDetails.getCurrentProject().equalsIgnoreCase("Free")) {
                rotationEmployee.add(empDetails.getName());
            }
        }
        // System.out.println(rotationEmployee.peek());
    }

    public void rotateTwoMonths(List<EmpDetails> employeeDetailsList, List<ProjectDetails> listProjectDetails) {

        for (EmpDetails empDetails : employeeDetailsList) {

            //      if (listProjectDetails.getTypeOfProject().equals("Rotation")) {
            if (empDetails.getPrevProject().equals(empDetails.getCurrentProject())) {
                for (ProjectDetails projectDetails : listProjectDetails) {
                    if (empDetails.getCurrentProject().equals(projectDetails.projectName)) {

                        if (projectDetails.getTypeOfProject().equals("Rotation") && projectDetails.getProjectLocation().equals("all")) {
                            rotationEmployee.add(empDetails.getName());
                        }
                    }
                }


            }
        }
        for (String s : rotationEmployee) System.out.println("finnal queue" + s);
        //System.out.println(rotationEmployee.element().toString());

    }

    public Map<String, Integer> LocationProjects(List<ProjectDetails> listProjectDetails, Map<String, List<String>> employeePerProjectSort, String role, Map<String, List<String>> designationSort, List<EmpDetails> employeeDetailsList) {
        Map<String, Integer> locationCount = new HashMap<String, Integer>();// Map to return Location + Rampup
        List<String> empInOneProjSameRoleSameLocation = new ArrayList<String>();
        for (ProjectDetails projectDetails : listProjectDetails) {
            if (!projectDetails.getProjectLocation().equalsIgnoreCase("all")) {
                if (projectDetails.getTypeOfProject().equals("Rotation")) {
                    String location = projectDetails.getProjectLocation();    // getting project Location
                    int locationRampup = 0;
                    System.out.println(projectDetails.getProjectName());
                    List<String> empInOneProj = employeePerProjectSort.get(projectDetails.getProjectName()); //All List of Employees working in that Project
                    List<String> Emp2MonthsSameProj = new ArrayList<String>();
                    for (String empname : empInOneProj) { //taking one employee of project one at a time.
                        if (designationSort.get(role).contains(empname)) { //DestinationSort gives me Map with Role of employee Vs employees
                            Boolean flag = false;
                            for (EmpDetails empDetails : employeeDetailsList) {
                                if (flag) {
                                    break;
                                }
                                if (empDetails.getName().equals(empname) && empDetails.getdesignation().equals(role)) {
                                    flag = true;
                                    if (projectDetails.getProjectLocation().equalsIgnoreCase(empDetails.getLocation())) {
                                        //Checking if same location has compleated two months
                                        if (empDetails.getPrevProject().equals(empDetails.getCurrentProject()) && empDetails.getdesignation().equals(role)) {
                                            Emp2MonthsSameProj.add(empname);
                                            //Removing only if there is
                                            //rotationEmployee.add(empname);
                                            break;
                                        }
                                        empInOneProjSameRoleSameLocation.add(empDetails.getName());
                                        break;
                                    } else {
                                        //adding it to rotation queue
                                        //System.out.println(empname + "location reason");
                                        rotationEmployee.add(empname);

                                    }
                                }


                            }


                        }
                    }
                    int roleCount = getRolecount(role, projectDetails);
                    if (empInOneProjSameRoleSameLocation.size() == empInOneProj.size()) {
                        System.out.println("All the Employees need Rotation");
                    }
                    //All the Employees to be removed.
                    //checking if only 1 employee in team or more.
                    if (empInOneProjSameRoleSameLocation.size() == 0 && roleCount > 1) {
                        System.out.println("All the Employees to be removed" + Emp2MonthsSameProj.size());
                        int removal = Emp2MonthsSameProj.size() / 2;
                        if (removal == 0 && Emp2MonthsSameProj.size() > 0) {         //Hack for scenario where rolecount <1 but only one employee is present despite serving 2 months cannot be rotated.
                            empInOneProjSameRoleSameLocation.add(Emp2MonthsSameProj.get(0));
                        } else {
                            for (int i = 0; i < removal; i++) {
                                rotationEmployee.add(Emp2MonthsSameProj.get(i));
                                // --locationRampup;
                            }
                        }
                    } else {
                        for (int i = 0; i < Emp2MonthsSameProj.size(); i++) {
                            rotationEmployee.add(Emp2MonthsSameProj.get(i));
                            // --locationRampup;
                        }
                    }

                    //if people present in project are more
                    if (empInOneProjSameRoleSameLocation.size() > roleCount) {
                        System.out.println(roleCount - empInOneProj.size());  //addition of employees

                        int rotate = roleCount - empInOneProj.size();
                        int rotateint = 0;
                        if (rotate != 0) {

                            for (String empname : empInOneProjSameRoleSameLocation) {

                                System.out.println(empname + "Removing Extra employee in same project of same location");
                                rotationEmployee.add(empname);
                                // --locationRampup;
                                ++rotateint;
                                // making project type as compromised for the Role as we cannot move all the resources out
                                if (rotateint == rotate) {
                                    break;
                                }
                            }
                        }
                    }
                    if (empInOneProjSameRoleSameLocation.size() < roleCount) {
                        int generateEmployment = 0;
                        int total_rampup = roleCount - empInOneProjSameRoleSameLocation.size();
                        locationRampup += total_rampup;
                        for (int i = total_rampup; i > 0; i--) {
                            priorityOpenings.add(projectDetails.getProjectName());
                            --total_rampup;
                        }

                    } else {
                        System.out.println(projectDetails.getProjectName() + " Allignment fit no need to rotate");
                    }

                    if (locationCount.get(location) != null) {
                        locationRampup += locationCount.get(location);
                        //System.out.println("AAA" + locationRampup);
                        locationCount.put(location, locationRampup);
                    } else
                        locationCount.put(location, locationRampup);
                }
                System.out.println(Arrays.asList(locationCount) + "Rotation" + projectDetails.getProjectName());
            }
        }
        for (String empname : rotationEmployee) {
            Boolean flag = false;

            for (EmpDetails empDetails : employeeDetailsList) {
                if (flag) {
                    break;
                }

                if (empDetails.getName().equals(empname)) {  // to check employees in rotationEmployee and remove there count
                    flag = true;
                    if (locationCount.get(empDetails.getLocation()) != null) {
                        int locationRampup = locationCount.get(empDetails.getLocation());
                        // if(!empDetails.getName().equals(projectDetails.projectName)) {
                        String currentProj = empDetails.getCurrentProject();
                        String projLocation = null;
                        //only updating list when employee location and project location do not match
                        for (ProjectDetails projectDetails : listProjectDetails) {
                            if (currentProj.equals(projectDetails.getProjectName())) {
                                projLocation = projectDetails.getProjectLocation();
                                break;
                            }
                        }
                        if (!empDetails.getLocation().equals(projLocation)) {
                            --locationRampup;
                            locationCount.put(empDetails.getLocation(), locationRampup);
                        }
                    }
                    System.out.println(Arrays.asList(locationCount));
                }
            }
        }
        for (String s : priorityOpenings) System.out.println("Priority Openings " + s);
        for (String s : rotationEmployee) System.out.println("Rotation Employee one Location " + s);
        //System.out.println(Arrays.asList(locationCount));
        return locationCount;
    }

    public void checkLocationBeforeRemoval(int rotate, List<String> empInOneProjSameRole, ProjectDetails projectDetails, String role, List<EmpDetails> employeeDetailsList, Map<String, Integer> locationCountRotation, Boolean setTypeOfProject) {

        int rotateint = 0;
        Boolean check = false;
        for (String empname : empInOneProjSameRole) {
            boolean flag = false;
            for (EmpDetails empDetails : employeeDetailsList) {
                if (flag) {
                    break;
                }
                if (empDetails.getName().equals(empname) && empDetails.getdesignation().equals(role)) {
                    flag = true;
                    if (empDetails.getPrevProject().equals(empDetails.getCurrentProject())) {
                        if (locationCountRotation.get(empDetails.getLocation()) != null) {
                            int locationRampup = locationCountRotation.get(empDetails.getLocation());
                            if (locationRampup > 0) {
                                --locationRampup;
                                locationCountRotation.put(empDetails.getLocation(), locationRampup);
                                rotationEmployee.add(empname);
                                ++rotateint;
                                if (setTypeOfProject)
                                    projectDetails.setTypeOfProject("compromised");

                            }
                            if (rotateint == rotate) {
                                check = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!check) {
            for (String empname : empInOneProjSameRole) {
                rotationEmployee.add(empname);
                ++rotateint;
                if (setTypeOfProject)
                    projectDetails.setTypeOfProject("compromised");
                if (rotateint == rotate) {
                    check = true;
                    break;
                }
            }

        }

    }

}