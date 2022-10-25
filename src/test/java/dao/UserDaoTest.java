package dao;

import connectionmaker.AWSConnectionMaker;
import domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    @DisplayName("Add and Get")

    public void addAndGet() throws SQLException {
        UserDaoFactory userDaoFactory = new UserDaoFactory();
        UserDao userDao = userDaoFactory.AWSUserDao();

        userDao.deleteAll();

        assertEquals(0,userDao.getCount());

        User user1 = new User("1","안지영","1234");
        userDao.add(user1);

        User user2 = userDao.getId("1");
        assertEquals(user1.getName(),user2.getName());
    }
}