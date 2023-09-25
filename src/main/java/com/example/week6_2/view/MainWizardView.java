package com.example.week6_2.view;

import com.example.week6_2.pojo.Wizard;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.SQLOutput;
import java.util.List;

//http://localhost:8080/mainPage.it
@Route(value = "mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField t_Fullname;
    private NumberField t_dollar;
    private RadioButtonGroup<String> rd_gender;
    private ComboBox cb_position, cb_school, cb_house;
    private Button btLeft, btCreate, btUpdate, btDelete , btRight;
    private int page;
    private Notification nf;
    private List<Wizard> w;

    public MainWizardView(){
        //รวมข้อมูล Wizards ทุกคนเอาไว้
        this.w = WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Wizard>>() {})
                .block();

        this.t_Fullname = new TextField();
        this.t_Fullname.setPlaceholder("Fullname");

        this.rd_gender = new RadioButtonGroup<>();
        this.rd_gender.setLabel("Gender: ");
        this.rd_gender.setItems("Male", "Female");

        this.cb_position = new ComboBox();
        this.cb_position.setPlaceholder("Position");
        this.cb_position.setItems("Student", "Teacher");

        this.t_dollar = new NumberField("Dollars");
        this.t_dollar.setPrefixComponent(new Span("$"));

        this.cb_school = new ComboBox();
        this.cb_school.setPlaceholder("School");
        this.cb_school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");

        this.cb_house = new ComboBox();
        this.cb_house.setPlaceholder("House");
        this.cb_house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slytherin");

        this.btLeft = new Button("<<");
        this.btCreate = new Button("Create");
        this.btUpdate = new Button("Update");
        this.btDelete = new Button("Delete");
        this.btRight = new Button(">>");

        HorizontalLayout h1 = new HorizontalLayout();
        h1.add(btLeft, btCreate, btUpdate, btDelete, btRight);

        this.nf = new Notification();
        this.nf.setDuration(3000);

        //add
        this.btCreate.addClickListener(event -> {
            System.out.println("btCreate---------------");
            // สร้างข้อมูลแบบ MultiValueMap จากคอมโพเนนต์ที่ผู้ใช้กรอก
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            if(this.rd_gender.getValue().equals("Male")){
                formData.add("sex", "m");
            }else{
                formData.add("sex", "f");
            }
            formData.add("name", this.t_Fullname.getValue());
            formData.add("school", (String) this.cb_school.getValue());
            formData.add("house", (String) this.cb_house.getValue());
            formData.add("money", String.valueOf(this.t_dollar.getValue()));
            formData.add("position", (String) this.cb_position.getValue());

            //จะรับผลลัพธ์เป็นอ็อบเจกต์ Wizards ที่ระบุว่าวิซาร์ดใหม่ถูกสร้างเรียบร้อย
            Wizard out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();
            //ข้อมูลใน db เปลี่ยนเเล้ว แต่ใน this.w ยังไม่ถูกเปลี่ยน ดังนั้นจึงต้อง add เพื่อให้ตรงกับ db
            this.w.add(out);
            System.out.println("id : "+ out.get_id());
            //หาหน้า page ที่มี id ตรงกับ id ใหม่ที่เพิ่งเพิ่มเข้าไป
            // 1-------------'ทำไปทำไม :('
//            for(int i = 0; i < this.w.size(); i++){
//                if(this.w.get(i).get_id().equals(out.get_id())){
//                    this.page = i;
//                    break;
//                }
//            }
//            setData(this.page);
            //2-----------------'ง่ายกว่าตั้งเยอะ :<'
            this.page = this.w.size()-1;
            System.out.println("btCreate --> " + this.page);
            System.out.println(this.w.size());
            nf.setText("Wizard has been created" + "page : " + this.page);
            nf.setPosition(Notification.Position.BOTTOM_START);
            nf.open();
        });

        //delete
        this.btDelete.addClickListener(event -> {
            System.out.println("btDelete---------------");
            // สร้าง MultiValueMap เพื่อเก็บข้อมูลที่จะส่งไปยังเซิร์ฟเวอร์
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("id", this.w.get(this.page).get_id()); //เพิ่มข้อมูล id (รหัส) ของวิซาร์ดที่จะลบลงใน formData
            //ส่งคำขอ HTTP POST ไปยัง URL http://localhost:8080/deleteWizard พร้อมกับข้อมูล formData
            boolean delete = Boolean.TRUE.equals(WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block());

            // ตรวจสอบว่าการลบสำเร็จหรือไม่
            if(delete){
                System.out.println("delete --> " + this.page);
                //ข้อมูลใน db เปลี่ยนเเล้ว แต่ใน this.w ยังไม่ถูกเปลี่ยน ดังนั้นจึงต้อง remove เพื่อให้ตรงกับ db
                this.w.remove(this.page);
                System.out.println("current --> " + this.page);
                System.out.println("all --> " + this.w.size());
                //เมื่อลบแล้ว
                if(this.page == this.w.size()){
                    //ถ้า pageปจบ. เป็น pageสุดท้ายของ w แล้วไม่มีข้อมูลใน w แล้ว
                    if(this.w.size() == 0){
                        setDataSpace();
                    }
                    //ถ้า pageปจบ. เป็น pageหน้าสุดท้าย แต่ยังเหลือข้อมูลในหน้า page อื่นๆอีก ให้แสดง page ก่อนหน้า
                    else{
                        this.page --;
                        setData(this.page);
                    }
                } else {
                    //ถ้าไม่ได้เป็น pageหน้าสุดท้าย เวลาลบให้แสดง page ด้านหลัง
                    setData(this.page);
                }
                nf.setText("Wizard has been removed");
            }else {
                nf.setText("Failed to delete the Wizard");
            }
        });

        //update
        this.btUpdate.addClickListener(event -> {
            System.out.println("btUpdate---------------");
            //this.page มีค่าน้อยกว่า 0 แสดงว่ายังไม่มีวิซาร์ดที่ถูกแสดงอยู่ และการอัปเดตจึงไม่สามารถทำได้
            if(this.page >= 0){
                // สร้างข้อมูลแบบ MultiValueMap จากคอมโพเนนต์ที่ผู้ใช้กรอก
                MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
                formData.add("oldId", this.w.get(this.page).get_id());
                System.out.println(this.page + "  :   " + this.w.get(this.page).get_id());
                if(this.rd_gender.getValue().equals("Male")){
                    formData.add("sex", "m");
                }else{
                    formData.add("sex", "f");
                }
                formData.add("name", this.t_Fullname.getValue());
                formData.add("school", (String) this.cb_school.getValue());
                formData.add("house", (String) this.cb_house.getValue());
                formData.add("money", String.valueOf(this.t_dollar.getValue()));
                formData.add("position", (String) this.cb_position.getValue());

                //จะรับผลลัพธ์เป็นอ็อบเจกต์ Wizard ที่ระบุว่าวิซาร์ดใหม่ถูกสร้างเรียบร้อย
                Wizard out = WebClient.create()
                        .post()
                        .uri("http://localhost:8080/updateWizard")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData(formData))
                        .retrieve()
                        .bodyToMono(Wizard.class)
                        .block();
                //ข้อมูลใน db เปลี่ยนเเล้ว แต่ใน this.w ยังไม่ถูกเปลี่ยน ดังนั้นจึงต้อง set เพื่อให้ตรงกับ db
                this.w.set(this.page, out);
//                setData(this.page);
                System.out.println("btUpdate --> " + this.page);
                nf.setText("Wizard has been created"+ "page : "  + this.page);
                nf.setPosition(Notification.Position.BOTTOM_START);
                nf.open();
            }
        });

        //คลิก ขวา
        this.btRight.addClickListener(event -> {
            //ถ้าไม่ใช่ page สุดท้ายสามารถเลื่อนไปทางขวาได้
            if(this.page < w.size()-1){
                this.page ++;
            }
            System.out.println("btRight --> "+this.page);
            System.out.println("all --> " + this.w.size());
            // เปลี่ยนข้อมูลวิซาร์ดที่แสดงบนหน้ามุมมองเป็นวิซาร์ดใหม่
            setData(page);
        });

        //คลิก ซ้าย
        this.btLeft.addClickListener(event -> {
            //ถ้าไม่ใช่ page แรกสุดสามารถเลื่อนไปทางซ้ายได้
            if(this.page > 0){
                this.page --;
            }
            System.out.println("btLeft --> "+this.page);
            System.out.println("all --> " + this.w.size());
            // เปลี่ยนข้อมูลวิซาร์ดที่แสดงบนหน้ามุมมองเป็นวิซาร์ดใหม่
            setData(page);
        });

        //ถ้าเริ่มต้น Wizards ไม่มีข้อมูล
        if(this.w.size() == 0){
            setDataSpace();
        }
        //ถ้าเริ่มต้น Wizards มีข้อมูล
        else{
            setData(0);
        }

        this.add(t_Fullname, rd_gender, cb_position, t_dollar, cb_school, cb_house, h1);
    }

    //ใช้ set ข้อมูลลง view ในกรณีที่มีข้อมูล
    public void setData(int i) {
        Wizard wz = this.w.get(i);
        this.t_Fullname.setValue(wz.getName());
        this.rd_gender.setValue(wz.getSex().equals("m") ? "Male" : "Female");
        this.cb_position.setValue(wz.getPosition());
        this.t_dollar.setValue(wz.getMoney());
        this.cb_school.setValue(wz.getSchool());
        this.cb_house.setValue(wz.getHouse());
    }


    //ใช้ set ข้อมูลลง view ในกรณีที่ไม่มีข้อมูล
    public void setDataSpace() {
        this.t_Fullname.setValue("");
        this.rd_gender.setValue("");
        this.cb_position.setValue("");
        this.cb_school.setValue("");
        this.cb_house.setValue("");
        this.t_dollar.setValue(null);
    }

}