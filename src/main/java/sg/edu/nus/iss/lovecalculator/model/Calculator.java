package sg.edu.nus.iss.lovecalculator.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Calculator implements Serializable {
    
    private String fname;
    private String sname;
    private String percentage;
    private String result;

    //need to implement id

    

    public Calculator() {}

    public Calculator(String fname, String sname) {
        this.fname = fname;
        this.sname = sname;
    }

    
    public Calculator(String fname, String sname, String percentage, String result) {
        this.fname = fname;
        this.sname = sname;
        this.percentage = percentage;
        this.result = result;
    }

    public String getFname() {return fname;}
    public void setFname(String fname) {this.fname = fname;}
    
    public String getSname() {return sname;}
    public void setSname(String sname) {this.sname = sname;}
    
    public String getPercentage() {return percentage;}
    public void setPercentage(String percentage) {this.percentage = percentage;}
    
    public String getResult() {return result;}
    public void setResult(String result) {this.result = result;}

    //convert from JsonObject to Java
    public static Calculator createJson(JsonObject j){
        Calculator c = new Calculator();
        c.percentage = "%s - %s"
                .formatted(j.getString("percentage"));
        c.percentage = "%s - %s"
                .formatted(j.getString("result"));

        // c.icon = "https://openweathermap.org/img/wn/%s@4x.png"
        //     .formatted(j.getString("icon"));
        //c.icon = "https://openweathermap.org/img/wn/" + j.getString("icon")  + "@4x.png";
        
        return c;
    }

    // convert json to Java object. 
    public static Calculator create(String json) throws IOException{
        Calculator c = new Calculator();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            c.setFname(o.getString("fname"));

            // JsonObject sname = o.getJsonObject("sname");
            c.setSname(o.getString("sname"));

            // JsonObject percentage = o.getJsonObject("percentage");
            c.setPercentage(o.getString("percentage"));

            // JsonObject result = o.getJsonObject("result");
            c.setResult(o.getString("result"));

        }

        return c;
    }

    




}
