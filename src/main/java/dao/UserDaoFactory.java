package dao;

import connectionmaker.AWSConnectionMaker;

public class UserDaoFactory {

    public UserDao AWSUserDao(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);

        return userDao;
    }
}
