package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

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
        user1.setUsername(user.getUsername());
        user1.setAge(user.getAge());
        user1.setPassword(user.getPassword());
        user1.setRoles(user.getRoles());
        entityManager.persist(user1);
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
