/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author Kizo
 */
public final class MovieCrewMember {
    private int movieCrewId;
    private int movieId;
    private int personId;
    private int roleId;

    public MovieCrewMember() {
    }
    public MovieCrewMember(int movieId, int personId, int roleId) {
        this.movieId = movieId;
        this.personId = personId;
        this.roleId = roleId;
    }

    public MovieCrewMember(int moviePersonRoleId, int movieId, int personId, int roleId) {
        this(movieId, personId, roleId);
        this.movieCrewId = moviePersonRoleId;
 
    }

    public int getMovieCrewId() {
        return movieCrewId;
    }

    public void setMovieCrewId(int moviePersonRoleId) {
        this.movieCrewId = moviePersonRoleId;
    }
    

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    
}
