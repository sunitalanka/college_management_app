package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "student")
public class StudentController {

    //@Autowired
    /* private StudentDao studentDao; */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayStudentHome(Model model) {

        //model.addAttribute("studentList", studentDao.findAll());
        model.addAttribute("title", "Welcome to Student Page");

        return "student/studenthome";

    }
}
