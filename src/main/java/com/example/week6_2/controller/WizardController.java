package com.example.week6_2.controller;

import com.example.week6_2.pojo.Wizard;
import com.example.week6_2.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public List<Wizard> wizards(){
        return wizardService.getListWizards();
    }

    @RequestMapping(value = "/addWizard", method = RequestMethod.POST)
    public Wizard addWizard(@RequestBody MultiValueMap<String, String> data){
        Map<String, String> d = data.toSingleValueMap();
        Wizard newData = wizardService.createWizard(
                new Wizard(null,
                        d.get("sex"),
                        d.get("name"),
                        d.get("school"),
                        d.get("house"),
                        Double.parseDouble(d.get("money")),
                        d.get("position"))
        );
        System.out.println("/addWizard  :  " + newData);
        return newData;
    }

    @RequestMapping(value = "/updateWizard", method = RequestMethod.POST)
    public Wizard updateWizard(@RequestBody MultiValueMap<String, String> data){
        Map<String, String> d = data.toSingleValueMap();
        Wizard wizardOld = wizardService.retrieveById(d.get("oldId"));

        //ถ้ามีข้อมูลอยู่ใน db ถึงจะ update ได้
//        if(wizardOld != null){
        Wizard newData = wizardService.updateWizard(
                new Wizard(wizardOld.get_id(),
                        d.get("sex"),
                        d.get("name"),
                        d.get("school"),
                        d.get("house"),
                        Double.parseDouble(d.get("money")),
                        d.get("position"))
        );
        return newData;
    }
//    }

    @RequestMapping(value = "/deleteWizard", method = RequestMethod.POST)
    public Boolean deleteWizard(@RequestBody MultiValueMap<String, String> data){
        Map<String, String> d = data.toSingleValueMap();
        boolean status = wizardService.deleteWizardById(d.get("id"));
        return status;
    }

}