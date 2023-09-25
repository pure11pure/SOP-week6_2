package com.example.week6_2.repository;

import com.example.week6_2.pojo.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

//Spring Data MongoDB เพื่อจัดการกับข้อมูลในฐานข้อมูล MongoDB
@Repository //ให้ Spring รู้ว่านี่เป็นคลาส Repository ที่จัดการกับข้อมูล
public interface WizardRepository extends MongoRepository<Wizard, String> {
    @Query(value = "{_id:'?0'}")
    public Wizard findBy_Id(String id);

    //เอาไว้สร้าง query ที่ไม่ได้มีมาให้
}