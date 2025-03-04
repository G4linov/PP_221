package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User findUserByCarData(String model, int series) {
        Car car = sessionFactory.getCurrentSession()
                .createQuery("from Car where model=:model and series=:series", Car.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .getSingleResult();
        ;
        if (car == null) {
            return null;
        }
        return sessionFactory.getCurrentSession()
                .createQuery("from User where car=:car", User.class)
                .setParameter("car", car)
                .getSingleResult();
    }

}
