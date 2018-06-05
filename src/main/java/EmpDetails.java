public class EmpDetails {
    String name;
    String designation;
    String location;
    String currentProject;
    String prevProject;

    public EmpDetails() {
        this.name = name;
        this.designation = designation;
        this.location = location;
        this.currentProject = currentProject;
        this.prevProject = prevProject;
    }

    public String getName() {
        return name;
    }

    public String getdesignation() {
        return designation;
    }

    public String getLocation() {
        return location;
    }

    public String getCurrentProject() {
        return currentProject;
    }

    public String getPrevProject() {
        return prevProject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setdesignation(String designation) {
       this.designation = designation;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCurrentProject(String currentProject) {
        this.currentProject = currentProject;
    }

    public void setPrevProject(String prevProject) {
        this.prevProject = prevProject;
    }



    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", location='" + location + '\'' +
                ", currentProject='" + currentProject + '\'' +
                ", prevProject='" + prevProject + '\'' +
                '}';
    }
}
