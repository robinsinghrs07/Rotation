public class ProjectDetails {


    String projectName;
    String projectLocation;
    int projQACount;
    int projBECount;
    int projFECount;
    String typeOfProject;

    public String getTypeOfProject() {
        return typeOfProject;
    }

    public void setTypeOfProject(String typeOfProject) {
        this.typeOfProject = typeOfProject;
    }

    public ProjectDetails() {
        this.projectName = projectName;
        this.projectLocation = projectLocation;
        this.projQACount = projQACount;
        this.projBECount = projBECount;
        this.projFECount = projFECount;
        this.typeOfProject= typeOfProject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public int getProjQACount() {
        return projQACount;
    }

    public void setProjQACount(int projQACount) {
        this.projQACount = projQACount;
    }

    public int getProjBECount() {
        return projBECount;
    }

    public void setProjBECount(int projBECount) {
        this.projBECount = projBECount;
    }

    public int getProjFECount() {
        return projFECount;
    }

    public void setProjFECount(int projFECount) {
        this.projFECount = projFECount;
    }

    @Override
    public String toString() {
        return "{" +
                "projectName='" + projectName + '\'' +
                ", projectLocation='" + projectLocation + '\'' +
                ", projQACount=" + projQACount +
                ", projBECount=" + projBECount +
                ", projFECount=" + projFECount +
                '}';
    }
}
