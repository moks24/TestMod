package ru.nethology.utilites;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.BeforeAll;
import ru.nethology.RegistrationDto;

import static io.restassured.RestAssured.given;

@UtilityClass
public class Generator {
    @UtilityClass
    public static class AuthTest {
        public static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    }
}