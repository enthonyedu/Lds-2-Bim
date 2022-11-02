package br.fai.lds.api.service;

import br.fai.lds.models.entities.UserModel;

public interface JwtService {

    String getJwtToken(UserModel user);
}
