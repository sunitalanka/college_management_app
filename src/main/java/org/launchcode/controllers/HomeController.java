package org.launchcode.controllers;

//import org.apache.catalina.User;
import org.launchcode.models.data.CoursesDao;
import org.launchcode.models.data.StudentDao;
import org.launchcode.models.data.StudentSearchRepository;
import org.launchcode.models.data.UserDao;
import org.launchcode.models.forms.Courses;
import org.launchcode.models.forms.StudentForm;
import org.launchcode.models.forms.User_Data;
import org.launchcode.models.forms.SearchStudent;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "")
public class HomeController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CoursesDao coursesDao;

    @Autowired
    private StudentSearchRepository ssr;
    @RequestMapping(value = "", method = RequestMethod.GET)
    //@ResponseBody
    public String displayUserLogin(Model model) {
        model.addAttribute("title", "College-Management");
        model.addAttribute(new User_Data());
        // model.addAttribute("menuList", menuDao.findAll());
        return "category/login";

    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute @Valid User_Data newUserForm,
                            Errors errors, @RequestParam String login_id, @RequestParam String userPwd, Model model) {


        if (errors.hasErrors()) {
            return "category/login";
        }

        Iterable<User_Data> allUsers = userDao.findAll();
        for (User_Data uf : allUsers) {
            if (login_id.equals(uf.getLogin_id()) && userPwd.equals(uf.getUserPwd())) {
                if (uf.getIs_admin().equals('y')) {
                    model.addAttribute("title", "Welcome To Admin Page");
                    model.addAttribute(new SearchStudent());
                    return "category/home";
                } else {
                    //model.addAttribute("title", "Welcome To Student Page");
                    return "student/studenthome";
                }
            } else {
                // return "category/login";
                model.addAttribute("title", "Welcome To Student Page");
                return "student/studenthome";
            }
        }

        System.out.println("redirecting login ..." + login_id);
        return "redirect:";

    }

    @RequestMapping(value = "add-student", method = RequestMethod.GET)
    public String displayRegisterStudent(Model model) {

        model.addAttribute("title", "Register Student");
        model.addAttribute(new StudentForm());

        return "category/add-student";
    }

    @RequestMapping(value = "add-student", method = RequestMethod.POST)
    public String processRegisterStudent(@ModelAttribute @Valid StudentForm newStudentForm,
                                         Errors errors, Model model) {

        model.addAttribute("title", "Register Student");

        if (errors.hasErrors()) {
            return "category/add-student";
        }

        User_Data ud = new User_Data();
        ud.setLogin_id(newStudentForm.getStudentId());
        ud.setFirstName(newStudentForm.getFirstName());
        ud.setLastName(newStudentForm.getLastName());
        ud.setIs_admin('n');
        ud.setUserPwd("12345");
        studentDao.save(newStudentForm);
        userDao.save(ud);
        return "category/home";

    }

    @RequestMapping(value = "add-course", method = RequestMethod.GET)
    public String displayAddCourses(Model model) {

        model.addAttribute("title", "Add Courses");
        model.addAttribute(new Courses());

        return "category/add-course";
    }

    @RequestMapping(value = "add-course", method = RequestMethod.POST)
    public String processAddCourses(@ModelAttribute @Valid Courses newCourses,
                                    Errors errors, Model model) {

        model.addAttribute("title", "Add Courses");
        if (errors.hasErrors()) {
            return "category/add-course";
        }
        coursesDao.save(newCourses);
        return "category/home";
    }

    @RequestMapping(value = "search-students", method = RequestMethod.GET)
    public String displaySearchStudent(Model model) {

        model.addAttribute("title", "Search Student");
        model.addAttribute(new SearchStudent());
        return "category/search-students";
    }

    @RequestMapping(value = "search-students", method = RequestMethod.POST)
    public String processSearchStudent(@ModelAttribute @Valid SearchStudent nss,
                                     Errors errors, Model model) {
        model.addAttribute("title", "Search Results");
        Iterable<StudentForm> studentList = null;
        if (errors.hasErrors()) {
            return "category/search-students";
        }

        if((nss.getStudentId().isEmpty()) && (nss.getCourse().isEmpty()) && (nss.getFirstName().isEmpty()) && (nss.getLastName().isEmpty())) {
            studentList = studentDao.findAll();
        }else{
            studentList = ssr.findByStudentIdAndCourseAndFirstNameAndLastName(nss.getStudentId(),nss.getCourse(),nss.getFirstName(),nss.getLastName());
        }
        model.addAttribute("studentList", studentList);

        return "category/search-students";

    }
}