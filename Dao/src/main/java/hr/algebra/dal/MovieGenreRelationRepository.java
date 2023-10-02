/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.MovieGenreRelation;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Kizo
 */
public class MovieGenreRelationRepository implements Repository<MovieGenreRelation> {

    private static final String ID = "ID";
    private static final String MOVIE_ID = "MovieID";
    private static final String GENRE_ID = "GenreID";
    private static final String CREATE_MOVIE_GENRE = "{ CALL CreateMovieGenreRelation (?,?,?) }";
    private static final String UPDATE_MOVIE_GENRE = "{ CALL UpdateMovieGenreRelation (?,?,?) }";
    private static final String DELETE_MOVIE_GENRE = "{ CALL DeleteMovieGenreRelation (?) }";
    private static final String SELECT_MOVIE_GENRE = "{ CALL SelectMovieGenreRelation (?) }";
    private static final String SELECT_MOVIE_GENRES = "{ CALL SelectMovieGenreRelations }";

    @Override
    public int create(MovieGenreRelation movieGenre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE_GENRE)) {

            stmt.setInt(MOVIE_ID, movieGenre.getMovieId());
            stmt.setInt(GENRE_ID, movieGenre.getGenreId());
            stmt.registerOutParameter(ID, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID);
        }
    }

    @Override
    public void createList(List<MovieGenreRelation> movieGenres) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE_GENRE)) {

            for (MovieGenreRelation movieGenre : movieGenres) {
                stmt.setInt(MOVIE_ID, movieGenre.getMovieId());
                stmt.setInt(GENRE_ID, movieGenre.getGenreId());
                stmt.registerOutParameter(ID, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, MovieGenreRelation movieGenre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE_GENRE)) {

            stmt.setInt(MOVIE_ID, movieGenre.getMovieId());
            stmt.setInt(GENRE_ID, movieGenre.getGenreId());
            stmt.setInt(ID, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE_GENRE)) {

            stmt.setInt(ID, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<MovieGenreRelation> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE_GENRE)) {

            stmt.setInt(ID, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new MovieGenreRelation(
                            rs.getInt(ID),
                            rs.getInt(MOVIE_ID),
                            rs.getInt(GENRE_ID)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MovieGenreRelation> selectAll() throws Exception {
        List<MovieGenreRelation> movieGenres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE_GENRES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                movieGenres.add(new MovieGenreRelation(
                        rs.getInt(ID),
                        rs.getInt(MOVIE_ID),
                        rs.getInt(GENRE_ID)));
            }
        }
        return movieGenres;
    }

}
