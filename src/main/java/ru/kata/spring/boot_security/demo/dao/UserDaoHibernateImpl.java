package Aaahooly.SpringBootLesson331.dao;


import Aaahooly.SpringBootLesson331.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import java.util.List;

@Component
public class UserDaoHibernateImpl implements UserDao {

    private final EntityManager entityManager;

    @Autowired
    public UserDaoHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> index() {
        return entityManager.createQuery("SELECT u from User u", User.class).getResultList();
    }


    @Override
    public User show(int idUser) {
        return entityManager.find(User.class, idUser);
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(int idUser) {
        entityManager.remove(entityManager.find(User.class, idUser));
    }

    @Override
    public void updateUser(int idUser, User user) {
        User user1 = entityManager.find(User.class, idUser);
        user1.setName(user.getName());
        user1.setAge(user.getAge());
        entityManager.persist(user1);
    }
}
