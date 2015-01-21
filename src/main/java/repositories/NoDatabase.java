package repositories;

import models.database.Game;
import models.database.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2015-01-16.
 */
public class NoDatabase implements IDatabaseAdapter {
    List<User> users = new ArrayList<User>();

    @Override
    public void addUserToDatabase(User user) {
        User newUser = new User(user.getName(), user.getSalt(), user.getHash());
        users.add(newUser);
    }

    @Override
    public String getUserSalt(String userName){
        for (User user:users) {
            if (user.getName().equals(userName)) {
                return user.getSalt();
            }
        }
        throw new IllegalArgumentException("User not in database: "+ userName);
    }

    @Override
    public boolean isUserNameInDatabase(String userName) {
        for (User user:users) {
            if (user.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserPasswordCorrect(User user) {
        for (User dbUser:users) {
            if (dbUser.getName().equals(user.getName()) && dbUser.getHash().equals(user.getHash())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addGameToDatabase(Game game) {
        throw new NotImplementedException();
    }

    @Override
    public List<Game> getGames() {
        throw new NotImplementedException();
    }
}
