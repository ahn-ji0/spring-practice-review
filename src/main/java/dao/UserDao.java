package dao;

import connectionmaker.ConnectionMaker;
import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import statementstrategy.AddStrategy;
import statementstrategy.DeleteAllStrategy;
import statementstrategy.StatementStrategy;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

public class UserDao {
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextStatementStrategy(StatementStrategy stmst) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();

            ps = stmst.getStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){

                }
            }
            if(c!=null){
                try{
                    c.close();
                }catch (SQLException e){

                }
            }
        }
    }
    public void deleteAll() throws SQLException {
        jdbcContextStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement getStatement(Connection c) throws SQLException {
                return c.prepareStatement("DELETE FROM users");
            }
        });
    }

    public void add(User user) throws SQLException {
        jdbcContextStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement getStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("INSERT INTO users(id,name,password) VALUES(?,?,?)");
                ps.setString(1,user.getId());
                ps.setString(2, user.getName());
                ps.setString(3,user.getPassword());
                return ps;
            }
        });
    }

    public User getId(String id) throws SQLException {
        Connection c = null;
        User user = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();
            user = null;

            ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setString(1,id);

            rs = ps.executeQuery();

            if(rs.next()) {
                user = new User(rs.getString("id"),
                        rs.getString("name"), rs.getString("password"));
            }

            if(user==null){
                throw new EmptyResultDataAccessException(1);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs!=null){
                try{
                    rs.close();
                }catch (SQLException e){

                }
            }
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){

                }
            }
            if(c!=null){
                try{
                    c.close();
                }catch (SQLException e){

                }
            }
        }
    }


    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement("SELECT count(*) FROM users");

            rs = ps.executeQuery();
            rs.next();

            count = rs.getInt(1);
            return count;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs!=null){
                try{
                    rs.close();
                }catch (SQLException e){

                }
            }
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){

                }
            }
            if(c!=null){
                try{
                    c.close();
                }catch (SQLException e){

                }
            }
        }
    }
}
