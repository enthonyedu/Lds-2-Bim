package br.fai.lds.client.service.impl;

import br.fai.lds.client.service.RestService;
import br.fai.lds.client.service.UserService;
import br.fai.lds.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserModel> {

    private static final String BASE_ENDPOINT = "http://localhost:8081/api/";

    private String buildEndpoint(String resource) {
        return BASE_ENDPOINT + resource;
    }


    @Autowired
    private RestService<UserModel> restService;

    @Override
    public int create(UserModel entity) {

        return restService.post("user/create", entity);
    }

    @Override
    public List<UserModel> find() {

        return restService.get("user/find");
    }

    @Override
    public UserModel findById(int id) {

        return restService.getById("user/find/" + id, UserModel.class);
    }

    @Override
    public boolean update(int id, UserModel entity) {

        return restService.put("user/update/" + id, entity);
    }

    @Override
    public boolean deleteById(int id) {

        return restService.deleteById("user/delete/" + id);
    }

    @Override
    public UserModel validateUsernameAndPassword(String username, String password) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<String> httpEntity = new HttpEntity<>("");


            String resource = "account/login?username=" + username + "&password=" + password;
            ResponseEntity<UserModel> responseEntity = restTemplate.exchange(buildEndpoint(resource),
                    HttpMethod.POST, httpEntity, UserModel.class);


            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                return null;
            }

            UserModel user = responseEntity.getBody();
            return user;
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }

    }
}
