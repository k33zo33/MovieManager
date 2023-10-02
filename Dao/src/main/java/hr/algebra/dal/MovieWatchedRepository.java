/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.MovieWatched;
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
public class MovieWatchedRepository implements Repository<MovieWatched> {

    private static final String ID_WATCHED_MOVIE = "ID";
    private static final String USER_ID = "UserID";
    private static final String MOVIE_ID = "MovieID";

    private static final String CREATE_WATCHED_MOVIE = "{ CALL CreateWatchedMovie (?,?,?) }";
    private static final String UPDATE_WATCHED_MOVIE = "{ CALL UpdateWatchedMovie (?,?,?) }";
    private static final String DELETE_WATCHED_MOVIE = "{ CALL DeleteWatchedMovie (?) }";
    private static final String SELECT_WATCHED_MOVIE = "{ CALL SelectWatchedMovie (?) }";
    private static final String SELECT_WATCHED_MOVIES = "{ CALL SelectWatchedMovies }";
    private static final String SELECT_WATCHED_MOVIES_WITH_ID = "{ CALL SelectWatchedMoviesWithId (?)}";

    @Override
    public int create(MovieWatched watchedMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_WATCHED_MOVIE)) {

            stmt.setInt(USER_ID, watchedMovie.getUserId());
            stmt.setInt(MOVIE_ID, watchedMovie.getMovieId());
            stmt.registerOutParameter(ID_WATCHED_MOVIE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_WATCHED_MOVIE);
        }
    }

    @Override
    public void createList(List<MovieWatched> watchedMovies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_WATCHED_MOVIE)) {

            for (MovieWatched favoriteMovie : watchedMovies) {
                stmt.setInt(USER_ID, favoriteMovie.getUserId());
                stmt.setInt(MOVIE_ID, favoriteMovie.getMovieId());
                stmt.registerOutParameter(ID_WATCHED_MOVIE, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, MovieWatched watchedMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_WATCHED_MOVIE)) {

            stmt.setInt(USER_ID, watchedMovie.getUserId());
            stmt.setInt(MOVIE_ID, watchedMovie.getMovieId());
            stmt.setInt(ID_WATCHED_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_WATCHED_MOVIE)) {

            stmt.setInt(ID_WATCHED_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<MovieWatched> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_WATCHED_MOVIE)) {

            stmt.setInt(USER_ID, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new MovieWatched(
                            rs.getInt(ID_WATCHED_MOVIE),
                            rs.getInt(MOVIE_ID),
                            rs.getInt(USER_ID)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MovieWatched> selectAll() throws Exception {
        List<MovieWatched> watchedMovies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_WATCHED_MOVIES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                watchedMovies.add(new MovieWatched(
                        rs.getInt(ID_WATCHED_MOVIE),
                        rs.getInt(MOVIE_ID),
                        rs.getInt(USER_ID)));
            }
        }
        return watchedMovies;
    }
    
    public List<MovieWatched> selectAllById(int userId) throws Exception {
        List<MovieWatched> watchedMovies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_WATCHED_MOVIES_WITH_ID)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    watchedMovies.add(new MovieWatched(
                            rs.getInt(ID_WATCHED_MOVIE),
                            rs.getInt(MOVIE_ID),
                            rs.getInt(USER_ID)));
                }
            }
        }
        return watchedMovies;
    }
    

}
