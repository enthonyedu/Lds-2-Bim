package br.fai.lds.client.service.impl;

import br.fai.lds.client.service.RestService;
import br.fai.lds.client.service.UserService;
import br.fai.lds.models.entities.UserModel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserModel> {

    public UserServiceImpl(HttpSession httpSession, RestService<UserModel> restService, RestTemplate restTemplate) {
        this.httpSession = httpSession;
        this.restService = restService;
        this.restTemplate = restTemplate;
    }

    private HttpSession httpSession;

    // foi removido o autowired para que a injecao seja feita pelo construtor
//    @Autowired
    private RestService<UserModel> restService;
    private RestTemplate restTemplate;

    private static final String BASE_ENDPOINT = "http://localhost:8081/api/";

    private String buildEndpoint(String resource) {
        return BASE_ENDPOINT + resource;
    }


    @Override
    public int create(UserModel entity) {

        return restService.post("user/create", entity);
    }

    @Override
    public List<UserModel> find() {

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        List<UserModel> userModels = restService.get("user/find", requestHeaders);

//        for (UserModel user :
//                userModels) {
//            user.setUsername("gambiarra - " + user.getUsername());
//        }

        return userModels;
    }

    @Override
    public UserModel findById(int id) {

        if (id < 0) {
            return null;
        }

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.getById("user/find/" + id, UserModel.class, requestHeaders);
    }

    @Override
    public boolean update(int id, UserModel entity) {

        if (id < 0) {
            return false;
        }

        if (entity == null) {
            return false;
        }

        if (id != entity.getId()) {
            return false;
        }

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.put("user/update/" + id, entity, requestHeaders);
    }

    @Override
    public boolean deleteById(int id) {
        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.deleteById("user/delete/" + id, requestHeaders);
    }

    @Override
    public UserModel validateUsernameAndPassword(String username, String password) {


        HttpHeaders httpHeaders = restService.getAuthenticationHeaders(username, password);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String resource = "account/login";

        ResponseEntity<UserModel> responseEntity = restTemplate.exchange(buildEndpoint(resource),
                HttpMethod.POST, httpEntity, UserModel.class);


        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        UserModel user = responseEntity.getBody();
        return user;

    }
}
