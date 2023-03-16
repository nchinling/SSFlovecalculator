package sg.edu.nus.iss.lovecalculator.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.lovecalculator.model.Calculator;
import sg.edu.nus.iss.lovecalculator.service.CalculatorService;


@Controller
@RequestMapping(path="/calculate")
public class AppController {
    @Autowired 
    private CalculatorService cSvc;
    
    @GetMapping
    public String getScore(@RequestParam(required=true) String name1,
    @RequestParam(required=true) String name2, Model model) 
    throws IOException{
    Optional<Calculator> c = cSvc.getResult(name1, name2);
    model.addAttribute("calculatorresults", c.get());
    return "calculator";
    }
}

