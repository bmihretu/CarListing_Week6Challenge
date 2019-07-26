package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;



@Controller
public class HomeController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CategoryRepository categoryRepository;



    @RequestMapping("/")
    public String Home(Model model){
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "home";
    }

    @GetMapping("/categoryInput")
    public String CategoryInput(Model model){
        model.addAttribute("category", new Category());
        return "categoryInput";
    }

    @GetMapping("/carInput")
    public String CarInput(Model model){
        model.addAttribute("car", new Car());
        model.addAttribute("categories", categoryRepository.findAll());
        return "carInput";
    }



    @PostMapping("/processCategory")
    public String processCategory(@Valid Category category, BindingResult result){
        if(result.hasErrors()){
            return "addCategory";
        }
        categoryRepository.save(category);
        return "redirect:/";
    }

    @PostMapping("/processCar")
    public String processCar(@Valid Car car, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryRepository.findAll());
            return "addCar";
        }

        carRepository.save(car);
        return "redirect:/";
    }

  // Update Vehicle

    @RequestMapping("/updateCategory/{id}")
    public String updateCategory(
            @PathVariable("id") long id, Model model){
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "addCategory";
    }

    @RequestMapping("/updateCar/{id}")
    public String updateCar(
            @PathVariable("id") long id, Model model){
        model.addAttribute("car", carRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addCar";
    }

    @RequestMapping("/CategoryView/{id}")
    public String CategoryView(
            @PathVariable("id") long id, Model model){
        model.addAttribute("category", categoryRepository.findById(id).get());
        model.addAttribute("cars", carRepository.findAll());
        return "CategoryView";
    }

    @RequestMapping("/CarView/{id}")
    public String CarView(
            @PathVariable("id") long id, Model model){
        model.addAttribute("car", carRepository.findById(id).get());
        return "CarView";
    }

//    Delete Vehicle

    @RequestMapping("/deleteCategory/{id}")
    public String deleteCategory(
            @PathVariable("id") long id){
        categoryRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/deleteCar/{id}")
    public String deleteCar(
            @PathVariable("id") long id){
        carRepository.deleteById(id);
        return "redirect:/";
    }

}