/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author Kizo
 */
public class Account {
    
    private int id;
    private String type;

    public Account() {
    }

    public Account(String type) {
        this.type = type;
    }

    public Account(int id, String type) {
        this(type);
        this.id = id;
        
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

   
    
    
}
