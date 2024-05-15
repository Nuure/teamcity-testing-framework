package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class CheckedBase extends Request implements CrudInterface {

    // Все реквесты, имеющие одинаковую реализацию CRUD методов, можно создать через общий конструктор
    public CheckedBase(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
    }

    @Override
    public Object create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(endpoint.endpoint())
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.clazz());
    }

    @Override
    public Object get(String id) {
        return given()
                .spec(spec)
                .body(id)
                .post(endpoint.endpoint() + endpoint.locator() + id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.clazz());
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Object delete(String id) {
        return given()
                .spec(spec)
                .delete(endpoint.endpoint() + endpoint.locator() + id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
