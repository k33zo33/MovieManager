/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author Kizo
 */
public final class MovieWatched {

    private int movieWatchedId;
    private int movieId;
    private int userId;

    public MovieWatched() {
    }

    public MovieWatched(int movieId, int userId) {
        this.movieId = movieId;
        this.userId = userId;
    }

    public MovieWatched(int movieWatchedId, int movieId, int userId) {
        this(movieId, userId);
        this.movieWatchedId = movieWatchedId;

    }

    public int getMovieWatchedId() {
        return movieWatchedId;
    }

    public void setMovieWatchedId(int movieWatchedId) {
        this.movieWatchedId = movieWatchedId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    

}
