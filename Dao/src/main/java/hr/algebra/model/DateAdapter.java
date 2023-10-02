/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Kizo
 */
public class DateAdapter extends XmlAdapter<String, LocalDateTime>{
    
     private final DateTimeFormatter dateformatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String text) throws Exception {
        return LocalDateTime.parse(text, dateformatter);
    }

    @Override
    public String marshal(LocalDateTime date) throws Exception {
        return date.format(dateformatter);
    }
    
}
