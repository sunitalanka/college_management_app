
package org.launchcode.models.data;

import org.launchcode.models.forms.StudentForm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StudentDao extends CrudRepository<StudentForm, Integer> {
}

