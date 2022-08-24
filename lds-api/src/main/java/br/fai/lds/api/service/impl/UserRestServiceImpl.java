package br.fai.lds.api.service.impl;

import br.fai.lds.api.service.UserRestService;
import br.fai.lds.db.dao.UserDao;
import br.fai.lds.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRestServiceImpl implements UserRestService<UserModel> {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserModel> find() {
        return userDao.find();

    }

    @Override
    public UserModel findById(int id) {
        return null;
    }

    @Override
    public int create(UserModel entity) {
        return 0;
    }

    @Override
    public boolean update(UserModel entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public UserModel validateLogin(String username, String password) {

        if (username.isEmpty()
                || password.isEmpty()) {
            return null;
        }

        if (username.length() < 4
                || password.length() < 3) {
            return null;
        }

        return userDao.validateUsernameAndPassword(username, password);
    }
}
