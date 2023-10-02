/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.Account;
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
public class AccountRepository implements Repository<Account> {

    private static final String ID_ACCOUNT_TYPE = "ID";
    private static final String TYPE = "Type";
    private static final String CREATE_ACCOUNT_TYPE = "{ CALL CreateAccountType (?,?) }";
    private static final String UPDATE_ACCOUNT_TYPE = "{ CALL UpdateAccountType (?,?) }";
    private static final String DELETE_ACCOUNT_TYPE = "{ CALL DeleteAccountType (?) }";
    private static final String SELECT_ACCOUNT_TYPE = "{ CALL SelectAccountType (?) }";
    private static final String SELECT_ACCOUNT_TYPES = "{ CALL SelectAccountTypes }";

    @Override
    public int create(Account acc) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACCOUNT_TYPE)) {

            stmt.setString(TYPE, acc.getType());
            stmt.registerOutParameter(ID_ACCOUNT_TYPE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_ACCOUNT_TYPE);
        }
    }

    @Override
    public void createList(List<Account> accs) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACCOUNT_TYPE)) {

            for (Account accountType : accs) {
                stmt.setString(TYPE, accountType.getType());
                stmt.registerOutParameter(ID_ACCOUNT_TYPE, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, Account acc) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_ACCOUNT_TYPE)) {

            stmt.setString(TYPE, acc.getType());
            stmt.setInt(ID_ACCOUNT_TYPE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ACCOUNT_TYPE)) {

            stmt.setInt(ID_ACCOUNT_TYPE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Account> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ACCOUNT_TYPE)) {

            stmt.setInt(ID_ACCOUNT_TYPE, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Account(
                            rs.getInt(ID_ACCOUNT_TYPE),
                            rs.getString(TYPE)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Account> selectAll() throws Exception {
        List<Account> accountTypes = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ACCOUNT_TYPES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accountTypes.add(new Account(
                        rs.getInt(ID_ACCOUNT_TYPE),
                        rs.getString(TYPE)));
            }
        }
        return accountTypes;
    }

}
