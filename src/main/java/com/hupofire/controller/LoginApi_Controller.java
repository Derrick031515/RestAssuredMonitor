package com.hupofire.controller;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * @description;
 * @Author：Derrick
 * @Date：2021/2/28 11:44 上午
 */
public class LoginApi_Controller {
    static String code = null;
    static String msg = null;
    static String access_token = null;

    public static Object[] GetAccessToken(int id,String apiUrl,String param){
        Response response = given()
                .log().all()
                .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .request()
                .body(param)
        .when()
                .post(apiUrl)
        .then()
                .extract()
                .response();
        response.print();
        String json = response.asString();

        code = response.jsonPath().getString("code");
        msg = response.jsonPath().getString("message");
        if("200".equals(code)){
            access_token = response.jsonPath().getString("access_token");
        }

        Object[] LoginInfo = {json,code,msg,access_token};
        return LoginInfo;
    }

    /**
     * 返回access_tokin
     * @param apiUrl
     * @param param
     * @return
     */
    public static String GetAccessTokenInfo(String apiUrl,String param){
        Response response = given()
                .log().all()
                .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .request()
                .body(param)
        .when()
                .post(apiUrl)
        .then()
                .extract()
                .response();
        response.print();
        String json = response.asString();
        System.out.println("json="+json);
        code = response.jsonPath().getString("code");
        msg = response.jsonPath().getString("message");
        if("200".equals(code)){
            access_token = response.jsonPath().getString("access_token");
        } else {
            access_token = "NULL";
        }

        return access_token;
    }
}
