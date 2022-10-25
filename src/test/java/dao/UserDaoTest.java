package dao;

import connectionmaker.AWSConnectionMaker;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.EmptyStackException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() throws SQLException {
        userDao = context.getBean("AWSUserDao",UserDao.class);
        user1 = new User("1","쵸코","1234");
        user2 = new User("2","치콘","1234");
        user3 = new User("3","빵","1234");
        userDao.deleteAll();
    }


    @Test
    @DisplayName("Add and Get")
    public void addAndGet() throws SQLException {

        assertEquals(0,userDao.getCount());

        userDao.add(user1);

        User user = userDao.getId("1");
        assertEquals(user1.getName(),user.getName());

        assertThrows(EmptyResultDataAccessException.class,()->{
            userDao.getId("4");
        });

    }

    @Test
    @DisplayName("Get all test")
    public void getAll(){
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        List<User> userList = userDao.getAll();
        assertEquals(3,userList.size());
    }
}

