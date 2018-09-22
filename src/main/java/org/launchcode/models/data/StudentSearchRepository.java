package org.launchcode.models.data;

import org.launchcode.models.forms.StudentForm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StudentSearchRepository extends CrudRepository<StudentForm, Long> {


  public List<StudentForm> findByStudentIdOrCourseOrFirstNameOrLastNameOrActiveStatus(String studentId, String course, String firstName, String lastName, Character activeStatus);
  public List<StudentForm> findByActiveStatus(Character activeStatus);
  public StudentForm findByStudentId(String studentId);


}
