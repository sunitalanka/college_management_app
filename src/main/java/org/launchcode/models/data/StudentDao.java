package org.launchcode.models.data;

import org.launchcode.models.forms.StudentForm;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<StudentForm, Integer> {
}
