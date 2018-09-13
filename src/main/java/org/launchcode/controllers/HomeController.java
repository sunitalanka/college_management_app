package org.launchcode.controllers;

//import org.apache.catalina.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.launchcode.models.data.*;
import org.launchcode.models.forms.Courses;
import org.launchcode.models.forms.StudentForm;
import org.launchcode.models.forms.User_Data;
import org.launchcode.models.forms.SearchStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    private UserDataSearchRepository udsr;

    ObjectMapper mapper = new ObjectMapper();



    @RequestMapping(value = "", method = RequestMethod.GET)
    //@ResponseBody
    public String displayUserLogin(Model model) {
        model.addAttribute("title", "College-Management");
        model.addAttribute(new User_Data());

        return "category/login";

    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute @Valid User_Data newUserForm,
                            Errors errors, @RequestParam String loginId, @RequestParam String userPwd, Model model) {


        if (errors.hasErrors()) {
            return "category/login";
        }
        User_Data ud = udsr.findByLoginId(loginId);
        if (ud == null) {
        }else {
            if (loginId.equals(ud.getLoginId()) && userPwd.equals(ud.getUserPwd())) {
                if (ud.getIs_admin().equals('y')) {
                    model.addAttribute("title", "Welcome To Admin Page");
                    model.addAttribute(new SearchStudent());
                    return "category/home";
                } else {
                    //model.addAttribute("title", "Welcome To Student Page");
                    StudentForm sf= ssr.findByStudentId(ud.getLoginId());
                    model.addAttribute("title", "Welcome To Student Page");
                    model.addAttribute("student",sf);
                    return "student/studenthome";
                }
            }
        }
        model.addAttribute("title", "College-Management");
        errors.rejectValue( "userPwd","","Invalid User or password");
        return "category/login";

    }

    @RequestMapping(value="logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, Model model){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        model.addAttribute("title", "College-Management");
        model.addAttribute(new User_Data());

        return "category/login";
    }

    @RequestMapping(value = "add-student", method = RequestMethod.GET)
    public String displayRegisterStudent(Model model) {

        model.addAttribute("title", "Register Student");
        model.addAttribute(new StudentForm());
        Iterable<Courses> cc = coursesDao.findAll();
        model.addAttribute("courses",cc);


        return "category/add-student";
    }

    @RequestMapping(value = "add-student", method = RequestMethod.POST)
    public String processRegisterStudent(@ModelAttribute @Valid StudentForm newStudentForm,
                                         Errors errors, Model model) {

        model.addAttribute("title", "Register Student");

        if (errors.hasErrors()) {
            //model.addAttribute(new StudentForm());
            Iterable<Courses> cc = coursesDao.findAll();
            model.addAttribute("courses",cc);
            return "category/add-student";
        }

        User_Data ud = new User_Data();
        ud.setLoginId(newStudentForm.getStudentId());
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

    @RequestMapping(value = "searchStudents-dt", method = RequestMethod.GET)
    public String displaySearchStudentDataTable(Model model) throws JsonGenerationException, JsonMappingException, IOException {

        model.addAttribute("title", "Search Student DataTable");
        Iterable<StudentForm> studentList = studentDao.findAll();
        String jsonStr = mapper.writeValueAsString(studentList);
        String jsonFormattedString = jsonStr.replaceAll("\\\"", "\"");
        model.addAttribute("studentDTList", jsonFormattedString);
        return "category/searchStudents-dt";
    }

    @RequestMapping(value="searchStudents-dt", method = RequestMethod.POST)
    public String updateStudentDetails(@RequestBody String jsonArray, Model model ) throws IOException {
           CollectionType javaType =mapper.getTypeFactory().constructCollectionType(List.class, StudentForm.class);
           List<StudentForm> asList = mapper.readValue(jsonArray, javaType);
           studentDao.saveAll(asList);
           return "category/searchStudent-dt";
    }

    @RequestMapping(value = "search-students", method = RequestMethod.POST)
    public String processSearchStudent(@ModelAttribute @Valid SearchStudent nss,
                                     Errors errors, Model model) {
        model.addAttribute("title", "Search Students");
        Iterable<StudentForm> studentList = null;
        if (errors.hasErrors()) {
            return "category/search-students";
        }

        if((nss.getStudentId().isEmpty()) && (nss.getCourse().isEmpty()) && (nss.getFirstName().isEmpty()) && (nss.getLastName().isEmpty())) {
            studentList = studentDao.findAll();
        }else{

            studentList = ssr.findByStudentIdOrCourseOrFirstNameOrLastName(nss.getStudentId(),nss.getCourse(),nss.getFirstName(),nss.getLastName());
        }
        model.addAttribute("studentList", studentList);

        return "category/search-students";

    }

    @RequestMapping(value="add-student/{studentId}", method  = RequestMethod.GET)
    public String  displayEditStudent(@PathVariable("studentId") String studentId,@ModelAttribute StudentForm ssf , Errors errors, Model model){


        if (errors.hasErrors()) {
            model.addAttribute("title", "Search Student");
            model.addAttribute(new SearchStudent());
            return "category/search-students";
        }
        model.addAttribute("title", "Edit Student Details");
        if(studentId!=null){
            ssf=ssr.findByStudentId(studentId);
        }


        model.addAttribute( "studentForm", ssf);
        return "category/add-student";
    }



}