package amcode.domain.services;

import amcode.domain.model.User;

import java.util.concurrent.ThreadLocalRandom;

public class UserControl {
    public User authenticateUser(User user) {
        // TODO: implement database
        int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
        if (randomNum < 5) {
            user = null;
        }
        return user;
    }

    public boolean updatePassword(User user) {
        return false;
    }

    public boolean tryAddUser(User user) {
        return false;
    }
}
