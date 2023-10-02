/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.User;
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
public class UserRepository implements Repository<User> {

    private static final String ID_USER = "ID";
    private static final String USERNAME = "Username";
    private static final String PASS_HASH = "PassHash";
    private static final String PASS_SALT = "PassSalt";
    private static final String ACCOUNT_TYPE_ID = "AccountTypeID";
    private static final String CREATE_USER = "{ CALL CreateUser (?,?,?,?,?) }";
    private static final String UPDATE_USER = "{ CALL UpdateUser (?,?,?,?,?) }";
    private static final String DELETE_USER = "{ CALL DeleteUser (?) }";
    private static final String SELECT_USER = "{ CALL SelectUser (?) }";
    private static final String SELECT_USERS = "{ CALL SelectUsers }";
    private static final String SELECT_SALT = "{ CALL SelectSalt (?)}";
    private static final String SELECT_USER_WITH_CREDENTIALS = "{ CALL SelectUserWithCredentials (?, ?, ?)}";

    @Override
    public int create(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            String salt = User.generateSalt();
            String hash = User.generateHash(user.getPassword(), salt);

            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASS_HASH, hash);
            stmt.setString(PASS_SALT, salt);
            stmt.setInt(ACCOUNT_TYPE_ID, user.getAccountTypeId());
            stmt.registerOutParameter(ID_USER, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_USER);
        }
    }

    @Override
    public void createList(List<User> users) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            for (User user : users) {
                String salt = User.generateSalt();
                String hash = User.generateHash(user.getPassword(), salt);
                stmt.setString(USERNAME, user.getUsername());
                stmt.setString(PASS_HASH, hash);
                stmt.setString(PASS_SALT, salt); // Empty salt for now, to be generated during password hashing
                stmt.setInt(ACCOUNT_TYPE_ID, user.getAccountTypeId());
                stmt.registerOutParameter(ID_USER, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_USER)) {
            String salt = User.generateSalt();
            String hash = User.generateHash(user.getPassword(), salt);
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASS_HASH, hash);
            stmt.setString(PASS_SALT, salt); // Empty salt for now, to be generated during password hashing
            stmt.setInt(ACCOUNT_TYPE_ID, user.getAccountTypeId());
            stmt.registerOutParameter(ID_USER, Types.INTEGER);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_USER)) {
            stmt.setInt(ID_USER, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<User> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USER)) {
            stmt.setInt(ID_USER, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString(USERNAME),
                            rs.getInt(ACCOUNT_TYPE_ID)
                    );
                    user.setId(rs.getInt(ID_USER));
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> selectAll() throws Exception {
        List<User> users = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USERS); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getString(USERNAME),
                        rs.getString(PASS_HASH),
                        rs.getInt(ACCOUNT_TYPE_ID)
                );
                user.setId(rs.getInt(ID_USER));
                users.add(user);
            }
        }
        return users;
    }

    public Optional<User> selectUserWithCredentials(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_SALT)) {

            stmt.setString(USERNAME, user.getUsername());

            String salt;
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    salt = rs.getString(PASS_SALT);
                } else {
                    return Optional.empty(); // user not found
                }
            }

            String hash = User.generateHash(user.getPassword(), salt);

            try (CallableStatement stmt2 = con.prepareCall(SELECT_USER_WITH_CREDENTIALS)) {
                stmt2.setString(USERNAME, user.getUsername());
                stmt2.setString(PASS_HASH, hash);
                stmt2.setString(PASS_SALT, salt);

                try (ResultSet rs = stmt2.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt(ID_USER);
                        User retrievedUser = new User(
                                userId,
                                rs.getString(USERNAME),
                                rs.getInt(ACCOUNT_TYPE_ID)
                        );

                        return Optional.of(retrievedUser);
                    }
                }
            }
        }
        return Optional.empty();
    }

}
