package repositories;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.database.Game;
import models.database.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by Mark on 2015-01-20.
 */
public class RealDatabase implements IDatabaseAdapter {
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Override
    @Transactional
    public void addUserToDatabase(User user) {
        EntityManager entityManager = entityManagerProvider.get();
        entityManager.persist(user);
    }

    @Override
    public String getUserSalt(String userName) {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :userName");
        q.setParameter("userName", userName);
        User user = (User) q.getSingleResult();

        return user.getSalt();
    }

    @Override
    public boolean isUserNameInDatabase(String userName) {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :userName");
        q.setParameter("userName", userName);

        return q.getResultList().size() > 0;
    }

    @Override
    public boolean isUserPasswordCorrect(User user) {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :userName AND u.hash = :userHash");
        q.setParameter("userName", user.getName());
        q.setParameter("userHash", user.getHash());

        return q.getResultList().size() > 0;
    }

    @Override
    @Transactional
    public void addGameToDatabase(Game game) {
        EntityManager entityManager = entityManagerProvider.get();
        entityManager.persist(game);
    }

    @Override
    public List<Game> getGames() {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT g FROM Game g ORDER BY g.timestamp DESC");

        return q.getResultList();
    }
}
