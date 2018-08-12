package org.launchcode.models.data;

import org.launchcode.models.forms.Courses;
import org.springframework.data.repository.CrudRepository;

public interface CoursesDao extends CrudRepository<Courses, Integer> {
}
