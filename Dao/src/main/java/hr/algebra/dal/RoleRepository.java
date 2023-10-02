/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.Role;
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
public class RoleRepository implements Repository<Role> {

    private static final String ID_ROLE = "ID";
    private static final String ROLE = "Role";
    private static final String CREATE_ROLE = "{ CALL CreateRole (?,?) }";
    private static final String UPDATE_ROLE = "{ CALL UpdateRole (?,?) }";
    private static final String DELETE_ROLE = "{ CALL DeleteRole (?) }";
    private static final String SELECT_ROLE = "{ CALL SelectRole (?) }";
    private static final String SELECT_ROLES = "{ CALL SelectRoles }";

    @Override
    public int create(Role role) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ROLE)) {

            stmt.setString(ROLE, role.getRole());
            stmt.registerOutParameter(ID_ROLE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_ROLE);
        }
    }

    @Override
    public void createList(List<Role> roles) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ROLE)) {

            for (Role role : roles) {
                stmt.setString(ROLE, role.getRole());
                stmt.registerOutParameter(ID_ROLE, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, Role role) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_ROLE)) {

            stmt.setString(ROLE, role.getRole());
            stmt.setInt(ID_ROLE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ROLE)) {

            stmt.setInt(ID_ROLE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Role> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ROLE)) {

            stmt.setInt(ID_ROLE, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Role(
                            rs.getInt(ID_ROLE),
                            rs.getString(ROLE)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Role> selectAll() throws Exception {
        List<Role> roles = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ROLES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roles.add(new Role(
                        rs.getInt(ID_ROLE),
                        rs.getString(ROLE)));
            }
        }
        return roles;
    }

}
