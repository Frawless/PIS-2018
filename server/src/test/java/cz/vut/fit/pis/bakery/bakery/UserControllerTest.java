package cz.vut.fit.pis.bakery.bakery;

import cz.vut.fit.pis.bakery.bakery.model.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }

//    @Test
//    public void createUser()
//    {
//        // uzivatel uz existuje => 400
//
//        User bakeryUser = new User();
//        bakeryUser.setFirstname("Arya");
//        bakeryUser.setPassword("shit");
//        bakeryUser.setLastname("Stark");
//        bakeryUser.setUsername("Stark");
//        bakeryUser.setEmail("aS@manyFacedGods.com");
//        bakeryUser.setPhoneNumber("");
//
//        String jsonContent;
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            jsonContent = mapper.writeValueAsString(bakeryUser);
//
//            mockMvc.perform(post("/users/sing-up")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(jsonContent))
//                    .andExpect(status().isOk());
//
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Test
    public void login() throws Exception
    {
        User user = new User();
        Auth auth = new Auth();
        auth.username = "Kocka";
        auth.password = "shit";
        user.setPassword("shit");
        user.setUsername("Kocka");

        String jsonContent;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonContent = mapper.writeValueAsString(auth);

            mockMvc.perform(post("/login")
                    .content(jsonContent))
                    .andExpect(status().isOk());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

//    @Test
//    public void getUser() throws Exception
//    {
//        String authKey = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJLb2NrYSIsInJvbGVzIjpbXSwiZXhwIjoxNTIxMzkxNTk3fQ.IrmljbZvGhEGGrlFeAbrLqskfBxlrCpTtCgkOJ1AzA9ZnuqQktgcsuFjHxh6oygMrZekeIOxZ754m9-eIX9v_g";
//        mockMvc.perform(get("/users/" + "Stark")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }
//
    public class Auth
    {
        public String username;
        public String password;

    }
}


