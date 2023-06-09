package com.ty.ty.web;





import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping("/user/index")
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


    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id,String Keyword,int page){
        patientrepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&Keyword="+Keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model){
        model.addAttribute("patient",new patient());
        return "formPatient";
    }

    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(@Valid patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "formPatient";
        }
         patientrepository.save(patient);
         return "redirect:/user/index?page="+Math.floorDiv(patient.getId(),4);
    }

    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(Model model,@RequestParam(name = "id") Long id){
        patient patient=patientrepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }
}
