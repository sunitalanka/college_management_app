package org.launchcode.models.data;

import org.launchcode.models.forms.StudentForm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StudentSearchRepository extends CrudRepository<StudentForm, Long> {

  public List<StudentForm> findByStudentIdAndCourseAndFirstNameAndLastName(String studentId, String course, String firstName, String lastName);

  public StudentForm findByStudentId(String studentId);


}
