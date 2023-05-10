package com.ty.ty.web;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ty.ty.Entity.patient;
import com.ty.ty.repository.patientRepository;

import jakarta.validation.Valid;


@Controller
public class PatientControler {

    private patientRepository patientrepository;

    public PatientControler(patientRepository patientrepository) {
        this.patientrepository = patientrepository;
    }

    // @GetMapping("/teste")
    // public String test(){
    //     return "test";
    // }

    @GetMapping("/index")
    public String index(Model model,
            @RequestParam(name="page",defaultValue = "0" )int page, 
            @RequestParam(name = "size",defaultValue="4") int size,
            @RequestParam(name = "Keyword",defaultValue="") String kw
            ){
        Page<patient> pagePatients=patientrepository.findByNomContains(kw,PageRequest.of(page, size));
        model.addAttribute("listePatients",pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("Keyword", kw);
        return "patients";
    }

    

    // public String index(Model model){
    //     Page<patient> pagePatients=patientrepository.findAll(PageRequest.of(0, 4));
    //     model.addAttribute("listePatient", pagePatients.getContent());
    //     return "patients";
    // }

    @GetMapping("/delete")
    public String delete(Long id,String Keyword,int page){
        patientrepository.deleteById(id);
        return "redirect:/index?page="+page+"&Keyword="+Keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient",new patient());
        return "formPatient";
    }

    @PostMapping("/savePatient")
    public String savePatient(@Valid patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "formPatient";
        }
         patientrepository.save(patient);
         return "redirect:/index?page="+Math.floorDiv(patient.getId(),4);
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model,@RequestParam(name = "id") Long id){
        patient patient=patientrepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }
}
