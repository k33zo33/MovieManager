/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.MovieCrewMember;
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
public class MovieCrewRepository implements Repository<MovieCrewMember> {

    private static final String ID = "ID";
    private static final String MOVIE_ID = "MovieID";
    private static final String PERSON_ID = "PersonID";
    private static final String ROLE_ID = "RoleID";
    private static final String CREATE_MOVIE_CREW_MEMBER = "{ CALL CreateMovieCrewMember (?,?,?,?) }";
    private static final String UPDATE_MOVIE_CREW_MEMBER = "{ CALL UpdateMovieCrewMember (?,?,?,?) }";
    private static final String DELETE_MOVIE_CREW_MEMBER = "{ CALL DeleteMovieCrewMember (?) }";
    private static final String SELECT_MOVIE_CREW_MEMBER = "{ CALL SelectMovieCrewMember (?) }";
    private static final String SELECT_MOVIE_CREW_MEMBERS = "{ CALL SelectMovieCrewMembers }";

    @Override
    public int create(MovieCrewMember movieCrew) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE_CREW_MEMBER)) {

            stmt.setInt(MOVIE_ID, movieCrew.getMovieId());
            stmt.setInt(PERSON_ID, movieCrew.getPersonId());
            stmt.setInt(ROLE_ID, movieCrew.getRoleId());
            stmt.registerOutParameter(ID, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID);
        }
    }

    @Override
    public void createList(List<MovieCrewMember> movieCrews) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE_CREW_MEMBER)) {

            for (MovieCrewMember movieCrew : movieCrews) {
                stmt.setInt(MOVIE_ID, movieCrew.getMovieId());
                stmt.setInt(PERSON_ID, movieCrew.getPersonId());
                stmt.setInt(ROLE_ID, movieCrew.getRoleId());
                stmt.registerOutParameter(ID, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, MovieCrewMember movieCrew) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE_CREW_MEMBER)) {

            stmt.setInt(MOVIE_ID, movieCrew.getMovieId());
            stmt.setInt(PERSON_ID, movieCrew.getPersonId());
            stmt.setInt(ROLE_ID, movieCrew.getRoleId());
            stmt.setInt(ID, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE_CREW_MEMBER)) {

            stmt.setInt(ID, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<MovieCrewMember> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE_CREW_MEMBER)) {

            stmt.setInt(ID, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new MovieCrewMember(
                            rs.getInt(ID),
                            rs.getInt(MOVIE_ID),
                            rs.getInt(PERSON_ID),
                            rs.getInt(ROLE_ID)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MovieCrewMember> selectAll() throws Exception {
        List<MovieCrewMember> movieCrewMembers = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE_CREW_MEMBERS); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                movieCrewMembers.add(new MovieCrewMember(
                        rs.getInt(ID),
                        rs.getInt(MOVIE_ID),
                        rs.getInt(PERSON_ID),
                        rs.getInt(ROLE_ID)));
            }
        }
        return movieCrewMembers;
    }

}
