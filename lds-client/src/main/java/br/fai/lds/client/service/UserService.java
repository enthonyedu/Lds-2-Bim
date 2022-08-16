package br.fai.lds.client.service;

import br.fai.lds.models.entities.UserModel;

public interface UserService<T> extends BaseService<T>{

    UserModel validateUsernameAndPassword(String username, String password);
}
