package br.fai.lds.client.controller;

import br.fai.lds.client.config.security.SecurityConfig;
import br.fai.lds.client.config.security.providers.FaiAuthenticationProvider;
import br.fai.lds.client.service.ReportService;
import br.fai.lds.client.service.UserService;
import br.fai.lds.models.entities.UserModel;
import br.fai.lds.models.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, FaiAuthenticationProvider.class})
class UserControllerTest {

    public static final String LIST_PAGE_ENDPOINT = "/user/list";
    @MockBean
    private UserService userService;

    @MockBean
    private ReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldInjectBeans() {
        assertThat(userService).isNotNull();

        assertNotNull(reportService);
        assertNotNull(mockMvc);
    }

    @Test
    void getUsers_whenNotAuthenticated_shouldRedirectToSignInPage() throws Exception {
        mockMvc.perform(get(LIST_PAGE_ENDPOINT))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/account/sign-in"));
    }

    @Test
    @WithMockUser
    void getUsers_whenAuthenticated_whenNoUsersIsReturned_shouldShowEmptyListPage() throws Exception {

        mockMvc.perform(get(LIST_PAGE_ENDPOINT)
                        .sessionAttr("currentUser", new UserModel()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("user", "users"));


    }

    @Test
    @WithMockUser
    void getUsers_whenAuthenticated_whenThereAreUsers_shouldListPage() throws Exception {

        UserModel tiburssinho = new UserModel();
        tiburssinho.setId(1);
        tiburssinho.setFullName("Tiburssinho Tiburssius");
        tiburssinho.setEmail("tiburssinho@gmail.com");
        tiburssinho.setType(UserType.ADMINISTRADOR);

        UserModel aroldo = new UserModel();
        aroldo.setId(2);
        aroldo.setFullName("Aroldo Aroldus");
        aroldo.setEmail("aroldo@gmail.com");
        aroldo.setType(UserType.USUARIO);

//        forma antiga
//        List<UserModel> users = new ArrayList<>();
//        users.add(aroldo);
//        users.add(tiburssinho);

        //forma nova
        List<UserModel> users = Arrays.asList(tiburssinho, aroldo);

        when(userService.find()).thenReturn(users);

        MvcResult mvcResult = mockMvc.perform(get(LIST_PAGE_ENDPOINT)
                        .sessionAttr("currentUser", new UserModel()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user", "users"))
                .andExpect(view().name("user/list"))
                .andReturn();

        final String html = mvcResult.getResponse().getContentAsString();
        assertThat(html).contains("<td>" + tiburssinho.getId() + "</td>");
        assertThat(html).contains("<td>" + tiburssinho.getFullName() + "</td>");
        assertThat(html).contains("<td>" + tiburssinho.getEmail() + "</td>");

        assertThat(html).contains("<td>" + aroldo.getId() + "</td>");
        assertThat(html).contains("<td>" + aroldo.getFullName() + "</td>");
        assertThat(html).contains("<td>" + aroldo.getEmail() + "</td>");
    }

}