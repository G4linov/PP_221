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
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User as user join fetch user.car", User.class);
        return query.getResultList();
    }

    @Override
    public User findUserByCarData(String model, int series) {
        return sessionFactory.getCurrentSession()
                .createQuery("select user from User user join fetch user.car car where car.model=:model and car.series=:series", User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .uniqueResult();
    }

}
