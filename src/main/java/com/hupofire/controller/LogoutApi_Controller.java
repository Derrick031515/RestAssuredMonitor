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
public class LogoutApi_Controller {
    public static Object[] GetLogoutInfo(int id,String access_token,String apiUrl){
        String code = "";
        String msg = "";
        Response response = given()
                    .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                    .headers("access_token",access_token)
                    .request()
                    .when()
                    .delete(apiUrl)
                    .then()
                    .extract()
                    .response();

        String json = response.asString();

        code = response.jsonPath().get("code");
        msg = response.jsonPath().get("message");
        Object[] LogoutInfo = {json,code,msg};

        return LogoutInfo;
    }



    public static void main(String[] args) {
        /*Response response = given()
//                .log().all()
                .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .headers("access_token","04c4fba92b3451b16daedbbc7dd4cec84b8a88333f3da60626c6abbfdc829a02")
                .request()
                .when()
                .get("http://127.0.0.1:9091/api/v1/menu/list")
                .then()
                .extract()
                .response();*/
//        response.print();


        Response response =  given()
                .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .headers("access_token","04c4fba92b3451b16daedbbc7dd4cec84b8a88333f3da60626c6abbfdc829a02")
                .request()
                .when()
                .delete("http://127.0.0.1:9091/api/v1/user/logout")
                .then()
                .extract()
                .response();
        String json = response.asString();
        System.out.println("==========");
        System.out.println(json);
        System.out.println("==========");
    }

}
