import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nethology.RegistrationDto;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static ru.nethology.utilites.Generator.AuthTest.requestSpec;

public class TestModTest {

    @BeforeAll
    public static void setUpAll() {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new RegistrationDto("vasya", "password", "active")) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void should() {

        open("http://localhost:9999/");
        $("[name='login']").val("vasya");
        $("[name='password']").val("password");
        $("[class='button__text']").click();
        $("[class='App_appContainer__3jRx1']").shouldHave(Condition.exactText("Личный кабинет"));

    }

}
