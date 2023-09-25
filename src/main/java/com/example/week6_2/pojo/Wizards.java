package com.example.week6_2.pojo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data // ใช้ Lombok annotation @Data เพื่อสร้าง getter, setter, toString, hashCode, และ equals ให้อัตโนมัติ
public class Wizards implements Serializable {
    // (implement Serializable) เป็นการบอก Java ว่าอ็อบเจกต์ของคลาส Wizards
    // สามารถถูก serialize และ deserialize ได้
    // และจะช่วยป้องกันปัญหา java.io.NotSerializableException

    // (List) --> ความยืดหยุ่นและสามารถเปลี่ยน model เป็น List อื่น ๆ ในอนาคต
    // (ArrayList) --> เฉพาะของ ArrayList และไม่ต้องการเปลี่ยน model เป็นชนิดอื่น ๆ ในภายหลัง
    // มีความสามารถในการจัดเก็บข้อมูลแบบแถว
    // สามารถใช้เมธอดที่เป็นพิเศษของ ArrayList ได้ เช่น
    // add(), remove(), get(), และอื่น ๆ

    private List<Wizard> model; // ประกาศตัวแปร model ที่เป็น List ของ Wizard
    public Wizards(){} // สร้างคอนสตรักเตอร์เริ่มต้นไม่รับพารามิเตอร์

    public Wizards(List<Wizard> model){
        this.model = model;
    }

}