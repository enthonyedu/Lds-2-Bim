package br.fai.lds.db.dao;

import br.fai.lds.models.entities.UserModel;

public interface UserDao<T> extends BaseDao<T> {

    UserModel validateUsernameAndPassword(String username, String password);
}
