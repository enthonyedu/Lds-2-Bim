package br.fai.lds.api.service;

import br.fai.lds.models.entities.UserModel;

public interface UserRestService<T> extends BaseRestService<T>{

    UserModel validateLogin(String username, String password);
}
