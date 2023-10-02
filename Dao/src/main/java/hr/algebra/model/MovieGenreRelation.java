/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author Kizo
 */
public final class MovieGenreRelation {

    private int movieGenreId;
    private int movieId;
    private int genreId;

    public MovieGenreRelation() {
    }

    public int getMovieGenreId() {
        return movieGenreId;
    }

    public void setMovieGenreRelationId(int movieGenreId) {
        this.movieGenreId = movieGenreId;
    }

    public MovieGenreRelation(int movieGenreId, int movieId, int genreId) {
        this(movieId, genreId);
        this.movieGenreId = movieGenreId;

    }

    public MovieGenreRelation(int movieId, int genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

}
