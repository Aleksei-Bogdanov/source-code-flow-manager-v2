package iprody.controller;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import iprody.entity.User;
import iprody.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Value("r2dbc:postgresql://postgres@localhost:5432/application_db")
    private String dbUrl;

    private void initData(){
        ConnectionFactory connectionFactory = ConnectionFactories.get(dbUrl);
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);

        String queryCreateDb = "CREATE TABLE IF NOT EXISTS usr (id SERIAL PRIMARY KEY, name TEXT NOT NULL, email TEXT NOT NULL);";
        template.getDatabaseClient().sql(queryCreateDb).fetch().rowsUpdated().block();
    }

    private void insertData(){
        Flux<User> userFlux = Flux.just(
                User.builder().name("John").email("john@some.ru").build(),
                User.builder().name("Arnold").email("arni@some.ru").build(),
                User.builder().name("Bill").email("billi@some.ru").build(),
                User.builder().name("Spoke").email("spoke@some.ru").build()
        );
        userRepository.deleteAll()
                .thenMany(userFlux)
                .flatMap(userRepository::save)
                .doOnNext(user -> userFlux.log())
                .blockLast();
    }

    @BeforeEach
    public void init(){
        initData();
        insertData();
    }

    @Test
    void getAll() {
        webTestClient.get().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody().jsonPath("$")
                .isArray()
                .jsonPath("$[0].name")
                .isEqualTo("John")
                .jsonPath("$[0].email")
                .isEqualTo("john@some.ru")
                .jsonPath("$[1].name")
                .isEqualTo("Arnold")
                .jsonPath("$[1].email")
                .isEqualTo("arni@some.ru")
                .jsonPath("$[2].name")
                .isEqualTo("Bill")
                .jsonPath("$[2].email")
                .isEqualTo("billi@some.ru")
                .jsonPath("$[3].name")
                .isEqualTo("Spoke")
                .jsonPath("$[3].email")
                .isEqualTo("spoke@some.ru");
    }

    @Test
    void getUser(){
        webTestClient.get()
                .uri("/api/v1/users/John")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("John")
                .jsonPath("$.email")
                .isEqualTo("john@some.ru")
                .jsonPath("$.id")
                .isNumber();
    }
}