import com.codeborne.selenide.Condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.nethology.utilites.Generator.Registration.getRegisteredUser;
import static ru.nethology.utilites.Generator.Registration.getUser;
import static ru.nethology.utilites.Generator.getRandomLogin;
import static ru.nethology.utilites.Generator.getRandomPassword;


public class TestModTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[name='login']").val(registeredUser.getLogin());
        $("[name='password']").val(registeredUser.getPassword());
        $("[class='button__text']").click();
        $("[class='App_appContainer__3jRx1']").shouldHave(Condition.exactText("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[name='login']").val(notRegisteredUser.getLogin());
        $("[name='password']").val(notRegisteredUser.getPassword());
        $("[class='button__text']").click();
        $("[class=\"notification__content\"]").shouldHave(Condition.
                exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[name='login']").val(blockedUser.getLogin());
        $("[name='password']").val(blockedUser.getPassword());
        $("[class='button__text']").click();
        $("[class=\"notification__content\"]").shouldHave(Condition.
                exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[name='login']").val(wrongLogin);
        $("[name='password']").val(registeredUser.getPassword());
        $("[class='button__text']").click();
        $("[class=\"notification__content\"]").shouldHave(Condition.
                exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[name='login']").val(registeredUser.getLogin());
        $("[name='password']").val(wrongPassword);
        $("[class='button__text']").click();
        $("[class=\"notification__content\"]").shouldHave(Condition.
                exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
