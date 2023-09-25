package com.example.week6_2.pojo;

import com.vaadin.flow.component.template.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data // สร้างต่าง ๆ อัตโนมัติเช่น getter, setter, toString, hashCode, และ equals เพื่อลดจำนวนโค้ดที่ต้องเขียนเอง
@Document("Wizard")
public class Wizard implements Serializable {
    //เพื่อใช้ในการแมปข้อมูลของพ่อมดไปยัง MongoDB และสามารถนำมาใช้ในการเพิ่มข้อมูล, อัปเดตข้อมูล, ค้นหาข้อมูล, หรือใช้งานข้อมูล
    @Id  //ใช้บ่งบอกว่า attribute ใดเป็น Key และสร้างให้รองรับ ObjectId ของ Mongo
    private String _id;
    private String sex;
    private String name;
    private String school;
    private String house;
    private Double money;
    private String position;

    public Wizard(){}
    public Wizard(String _id, String sex, String name, String school, String house, Double money, String position){
        this._id = _id;
        this.sex = sex;
        this.name = name;
        this.school = school;
        this.house = house;
        this.money = money;
        this.position = position;
    }

}