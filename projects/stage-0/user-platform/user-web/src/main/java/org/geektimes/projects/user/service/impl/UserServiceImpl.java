package org.geektimes.projects.user.service.impl;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.UserRepository;
import org.geektimes.projects.user.service.UserService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author jixiaoliang
 * @since 2021/02/28
 **/
public class UserServiceImpl implements UserService {

    @Resource(name = "bean/UserRepository")
    private UserRepository userRepository;

    @Resource(name = "bean/EntityManager")
    private EntityManager entityManager;

    @Resource(name = "bean/Validator")
    private Validator validator;


    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean register(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        violations.forEach(rst->{
            throw new RuntimeException(String.format("%s:%s", rst.getPropertyPath(), rst.getMessage()));
        });
        entityManager.persist(user);
        //return userRepository.save(user);
        return true;
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public void createTable() {
        userRepository.createTable();
    }


}
