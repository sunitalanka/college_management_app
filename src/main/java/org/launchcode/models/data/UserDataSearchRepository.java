package org.launchcode.models.data;


import org.launchcode.models.forms.User_Data;
import org.springframework.data.repository.CrudRepository;


public interface UserDataSearchRepository extends CrudRepository<User_Data, Long> {

 public User_Data findByLoginId(String loginId);


}
