package com.example.springboot_security403;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CloudinaryConfig cloudc;


    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }

    @RequestMapping("/")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "list";
    }
    @GetMapping("/add")
    public String courseForm(Model model){
        model.addAttribute("course", new Course());
        return "courseform";
    }
    @PostMapping("/process")
    public String processForm(@Valid Course course,
        BindingResult result){
        if(result.hasErrors()){
            return "courseform";
        }
        courseRepository.save(course);
        return  "redirect:/";
    }
    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id,
                             Model model){
        model.addAttribute("course", courseRepository.findById(id).get());
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id,
                               Model model){
        model.addAttribute("course", courseRepository.findById(id).get());
        return "courseform";
    }
    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        courseRepository.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login?logout=true";
    }


}
