package ru.nethology.utilites;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import ru.nethology.RegistrationDto;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static io.restassured.RestAssured.given;
import static ru.nethology.utilites.Generator.AuthTest.requestSpec;

public class Generator {

    public static class AuthTest {
        public static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    private static final Faker faker = new Faker(new Locale("en"));

    private Generator() {
    }

    private static void sendRequest(RegistrationDto user) {
        given() // "дано"
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        String login = faker.internet().domainName();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            RegistrationDto user = new RegistrationDto(getRandomLogin(),getRandomPassword(),status);
            return user;
        }

        public static RegistrationDto getRegisteredUser(String status) {
            var registeredUser = new Registration().getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }
}
