/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.Movie;
import java.security.interfaces.RSAKey;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Kizo
 */
public class MovieRepository implements Repository<Movie> {

    private static final String ID_MOVIE = "ID";
    public static final String TITLE = "Title";
    public static final String PUB_DATE = "PubDate";
    public static final String ORIGINAL_TITLE = "OriginalTitle";
    public static final String DESCRIPTION = "Description";
    public static final String DURATION = "Duration";
    public static final String YEAR = "Year";
    public static final String POSTER = "Poster";
    public static final String LINK = "Link";
    public static final String RESERVATION = "Reservation";
    public static final String DISPLAY_DATE = "DisplayDate";
    public static final String PERFORMANCES = "Performances";
    public static final String TRAILER = "Trailer";
    private static final String CREATE_MOVIE = "{ CALL CreateMovie (?,?,?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL UpdateMovie (?,?,?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL DeleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL SelectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL SelectMovies }";

    @Override
    public int create(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(PUB_DATE, movie.getPubDate().format(Movie.DATE_FORMATTER));
            stmt.setString(ORIGINAL_TITLE, movie.getOriginalTitle());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setInt(DURATION, movie.getDuration());
            stmt.setInt(YEAR, movie.getYear());
            stmt.setString(POSTER, movie.getPoster());
            stmt.setString(LINK, movie.getLink());
            stmt.setString(RESERVATION, movie.getReservation());
            stmt.setString(DISPLAY_DATE, movie.getDisplayDate().format(Movie.DATE_FORMATTER));
            stmt.setString(PERFORMANCES, movie.getPerformances());
            stmt.setString(TRAILER, movie.getTrailer());
            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_MOVIE);
        }
    }

    @Override
    public void createList(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            for (Movie movie : movies) {
                stmt.setString(TITLE, movie.getTitle());
                stmt.setString(PUB_DATE, movie.getPubDate().format(Movie.DATE_FORMATTER));
                stmt.setString(ORIGINAL_TITLE, movie.getOriginalTitle());
                stmt.setString(DESCRIPTION, movie.getDescription());
                stmt.setInt(YEAR, movie.getYear());
                stmt.setInt(DURATION, movie.getDuration());
                stmt.setString(POSTER, movie.getPoster());
                stmt.setString(LINK, movie.getLink());
                stmt.setString(RESERVATION, movie.getReservation());
                stmt.setString(DISPLAY_DATE, movie.getDisplayDate().format(Movie.DATE_FORMATTER));
                stmt.setString(PERFORMANCES, movie.getPerformances());
                stmt.setString(TRAILER, movie.getTrailer());
                stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {

            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(PUB_DATE, movie.getPubDate().format(Movie.DATE_FORMATTER));
            stmt.setString(ORIGINAL_TITLE, movie.getOriginalTitle());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setInt(DURATION, movie.getDuration());
            stmt.setInt(YEAR, movie.getYear());
            stmt.setString(POSTER, movie.getPoster());
            stmt.setString(LINK, movie.getLink());
            stmt.setString(RESERVATION, movie.getReservation());
            stmt.setString(DISPLAY_DATE, movie.getDisplayDate().format(Movie.DATE_FORMATTER));
            stmt.setString(PERFORMANCES, movie.getPerformances());
            stmt.setString(TRAILER, movie.getTrailer());
            stmt.setInt(ID_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {

            stmt.setInt(ID_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {

            stmt.setInt(ID_MOVIE, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUB_DATE), Movie.DATE_FORMATTER),
                            rs.getString(ORIGINAL_TITLE),
                            rs.getString(DESCRIPTION),
                            rs.getInt(DURATION),
                            rs.getInt(YEAR),
                            rs.getString(POSTER),
                            rs.getString(LINK),
                            rs.getString(RESERVATION),
                            LocalDateTime.parse(rs.getString(DISPLAY_DATE), Movie.DATE_FORMATTER),
                            rs.getString(PERFORMANCES),
                            rs.getString(TRAILER))
                    );
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectAll() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                //String Title = rs.getString("Title");
                //System.out.println("Title: " + Title);
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUB_DATE), Movie.DATE_FORMATTER),
                        rs.getString(ORIGINAL_TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getInt(DURATION),
                        rs.getInt(YEAR),
                        rs.getString(POSTER),
                        rs.getString(LINK),
                        rs.getString(RESERVATION),
                        LocalDateTime.parse(rs.getString(DISPLAY_DATE), Movie.DATE_FORMATTER),
                        rs.getString(PERFORMANCES),
                        rs.getString(TRAILER))
                );
            }
        }

        //System.out.println();
        return movies;

    }

}
