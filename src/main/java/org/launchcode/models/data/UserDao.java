package org.launchcode.models.data;

import org.launchcode.models.forms.User_Data;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User_Data, Integer> {
}
