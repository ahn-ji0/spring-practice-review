package dao;

import connectionmaker.ConnectionMaker;
import domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException {
        Connection c = connectionMaker.getConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id,name,password) VALUES(?,?,?)");
        ps.setString(1,user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User getId(String id) throws SQLException {
        Connection c = connectionMaker.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User(rs.getString("id"),
                rs.getString("name"),rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public void deleteAll() throws SQLException {
        Connection c = connectionMaker.getConnection();

        PreparedStatement ps = c.prepareStatement("DELETE FROM users");

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException {
        Connection c = connectionMaker.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM users");

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }
}
