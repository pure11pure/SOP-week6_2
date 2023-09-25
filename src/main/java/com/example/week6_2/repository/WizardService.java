package com.example.week6_2.repository;

import com.example.week6_2.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    //ให้ไปหา Object ที่เกี่ยวข้องมาใส่ *ต่อให้เปลี่ยน db หน้านี้ไม่กระทบ
    @Autowired
    private WizardRepository repository;

    // Constructor รับค่า
    public WizardService(WizardRepository repository){
        this.repository = repository;
    }

    @Cacheable(value = "myRedis")
    public List<Wizard> getListWizards(){
        return repository.findAll();
    }

    @CacheEvict(value = "myRedis", allEntries = true)
    public Wizard createWizard(Wizard wizard){
        return repository.save(wizard);
    }
    @CachePut(value = "myRedis")
    public Wizard updateWizard(Wizard wizard){
        return repository.save(wizard);
    }
    @Cacheable(value = "myRedis")
    public Wizard retrieveById(String id){
        return repository.findBy_Id(id);
    }
    @CacheEvict(value = "myRedis", allEntries = true)
    public Boolean deleteWizardById(String id){
        try {
            repository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}