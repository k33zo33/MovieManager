/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.Genre;
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
public class GenreRepository implements Repository<Genre> {

    private static final String ID_GENRE = "ID";
    private static final String NAME = "Name";
    private static final String CREATE_GENRE = "{ CALL CreateGenre (?,?) }";
    private static final String UPDATE_GENRE = "{ CALL UpdateGenre (?,?) }";
    private static final String DELETE_GENRE = "{ CALL DeleteGenre (?) }";
    private static final String SELECT_GENRE = "{ CALL SelectGenre (?) }";
    private static final String SELECT_GENRES = "{ CALL SelectGenres }";

    @Override
    public int create(Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_GENRE)) {

            stmt.setString(NAME, genre.getName());
            stmt.registerOutParameter(ID_GENRE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_GENRE);
        }
    }

    @Override
    public void createList(List<Genre> genres) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_GENRE)) {

            for (Genre genre : genres) {
                stmt.setString(NAME, genre.getName());
                stmt.registerOutParameter(ID_GENRE, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_GENRE)) {

            stmt.setString(NAME, genre.getName());
            stmt.setInt(ID_GENRE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_GENRE)) {

            stmt.setInt(ID_GENRE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Genre> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_GENRE)) {

            stmt.setInt(ID_GENRE, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(NAME)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> selectAll() throws Exception {
        List<Genre> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_GENRES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                genres.add(new Genre(
                        rs.getInt(ID_GENRE),
                        rs.getString(NAME)));
            }
        }
        return genres;
    }
}
