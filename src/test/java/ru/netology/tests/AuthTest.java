package ru.netology.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void setUp(){
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginRegisteredUser() {
        UserInfo userInfo = DataGenerator.Registration.generateUser("active");
        DataGenerator.Registration.sendRequest(userInfo);
        $("[data-test-id='login'] input").setValue(userInfo.getLogin());
        $("[data-test-id='password'] input").setValue(userInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldBe(Condition.exactText("Личный кабинет"));


    }

    @Test
    void shouldNotLoginBlockedUser() {
        UserInfo userInfo = DataGenerator.Registration.generateUser("blocked");
        DataGenerator.Registration.sendRequest(userInfo);
        $("[data-test-id='login'] input").setValue(userInfo.getLogin());
        $("[data-test-id='password'] input").setValue(userInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Пользователь заблокирован"));


    }

    @Test
    void shouldGerErrorIfWrongPassword() {
        UserInfo userInfo= DataGenerator.Registration.generateUser("active");
        DataGenerator.Registration.sendRequest(userInfo);
        var anotherPassword = DataGenerator.generatePassword();
        $("[data-test-id='login'] input").setValue(userInfo.getLogin());
        $("[data-test-id='password'] input").setValue(anotherPassword);
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }
    @Test
    void shouldGerErrorIfWrongLogin() {
        UserInfo userInfo= DataGenerator.Registration.generateUser("active");
        DataGenerator.Registration.sendRequest(userInfo);
        var anotherLogin = DataGenerator.generateLogin();
        $("[data-test-id='login'] input").setValue(anotherLogin);
        $("[data-test-id='password'] input").setValue(userInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }
    @Test
    void shouldNotLoginUnRegisteredUser() {
        var unregisteredUser = DataGenerator.Registration.generateUser("active");
        $("[data-test-id='login'] input").setValue(unregisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(unregisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"));

    }

}
