package org.launchcode.models.forms;

//import javax.validation.constraints.Size;

public class SearchStudent {




    private String studentId;


    private String course;



    private String firstName;


    private String lastName;


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {

        this.studentId = studentId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
