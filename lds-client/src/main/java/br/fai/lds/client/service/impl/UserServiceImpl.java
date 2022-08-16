package br.fai.lds.client.service.impl;

import br.fai.lds.client.service.UserService;
import br.fai.lds.models.entities.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserModel> {

    @Override
    public int create(UserModel entity) {
        return 0;
    }

    @Override
    public List<UserModel> find() {
        return null;
    }

    @Override
    public UserModel findById(int id) {
        return null;
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
    public UserModel validateUsernameAndPassword(String username, String password) {
        return null;
    }
}
