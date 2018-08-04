package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {

    @RequestMapping(value="")
    @ResponseBody
    public String index(){

        return "College Management System";
    }
}
