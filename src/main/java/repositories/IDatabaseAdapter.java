package repositories;

import models.database.User;

/**
 * Created by Mark on 2015-01-16.
 */
public interface IDatabaseAdapter {
    public void addUserToDatabase(User user);
    public String getUserSalt(String userName);
    public boolean isUserNameInDatabase(String userName);
    public boolean isUserPasswordCorrect(User user);
}
