/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.User;

/**
 *
 * @author Kizo
 */
public interface LoginInterface {
    //void createAdmin();
    void userLogIn(User user);
    void userLogOut();
    
}
