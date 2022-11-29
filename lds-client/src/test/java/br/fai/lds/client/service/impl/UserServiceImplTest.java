package br.fai.lds.client.service.impl;

import br.fai.lds.client.service.RestService;
import br.fai.lds.models.entities.UserModel;
import br.fai.lds.models.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private HttpSession httpSession;

    @Mock
    private RestService restService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceImpl cut;
    // sut - system under test
    // cut - class under test

    @Test
    void ShouldInjectBeans() {
        assertNotNull(cut);
        assertNotNull(httpSession);
        assertNotNull(restService);
        assertNotNull(restTemplate);
    }

    @Test
    void whenValidUserIsProvided_shouldCreate() {

        UserModel user = createFirstMockUser();

        final int id = cut.create(user);

        assertThat(id).isEqualTo(-1);

    }

    @Test
    void whenNoUsersFound_shouldReturnEmptyList() {
        List<UserModel> users = cut.find();

        assertThat(users).isEmpty();
    }

    private UserModel createFirstMockUser() {
        UserModel user = new UserModel();
        user.setId(1);
        user.setUsername("tiburssinho");
        user.setFullName("Tiburssio Tiburssius");
        user.setEmail("tiburssinho@gmail.com");
        user.setPassword("123");
        user.setType(UserType.ADMINISTRADOR);
        user.setActive(true);

        Timestamp dateTime = new Timestamp(System.currentTimeMillis());

        user.setLastModified(dateTime);
        user.setCreatedAt(dateTime);

        return user;

    }

    private UserModel createSecondMockUser() {
        UserModel user = new UserModel();
        user.setId(2);
        user.setUsername("aroldo");
        user.setFullName("Aroldo Aroldus");
        user.setEmail("aroldo@gmail.com");
        user.setPassword("123");
        user.setType(UserType.USUARIO);
        user.setActive(false);

        Timestamp dateTime = new Timestamp(System.currentTimeMillis());

        user.setLastModified(dateTime);
        user.setCreatedAt(dateTime);

        return user;
    }

}