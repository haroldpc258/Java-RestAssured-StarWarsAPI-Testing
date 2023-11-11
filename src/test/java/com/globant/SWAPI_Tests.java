package com.globant;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class SWAPI_Tests {

    private Response response;

    @BeforeClass
    public void beforeClass() {
        RestAssured.baseURI = "https://swapi.dev/api/";
    }

    @Test
    public void testSecondPerson() {

         response = given()
                .when()
                .get("people/2/");

        //response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath js = response.jsonPath();
        Assert.assertEquals(js.getString("skin_color"), "gold");
        Assert.assertEquals(js.getList("films").size(), 6);
    }

    @Test(dependsOnMethods = "testSecondPerson")
    public void testSecondFilm() {

        JsonPath js = response.jsonPath();
        String secondFilmPath = js.getList("films").get(1).toString();

        response = given()
                .when()
                .get(secondFilmPath);

        js = response.jsonPath();

        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(js.get("release_date"));
        Assert.assertTrue(matcher.matches());

        Assert.assertFalse(js.getList("characters").isEmpty());
        Assert.assertFalse(js.getList("planets").isEmpty());
        Assert.assertFalse(js.getList("starships").isEmpty());
        Assert.assertFalse(js.getList("vehicles").isEmpty());
        Assert.assertFalse(js.getList("species").isEmpty());
    }

    @Test(dependsOnMethods = "testSecondFilm")
    public void testFirstPlanet() {

        JsonPath js = response.jsonPath();

        String firstPlanetPath = js.getList("planets").getFirst().toString();

        response = given()
                .when()
                .get(firstPlanetPath);

        js = response.jsonPath();

        Assert.assertEquals(js.getString("gravity"), "1.1 standard");
        Assert.assertEquals(js.getString("terrain"), "tundra, ice caves, mountain ranges");
    }

    @Test(dependsOnMethods = "testFirstPlanet")
    public void testSamePlanetRequest() {
        JsonPath js = response.jsonPath();

        Response secondResponse = given()
                .when()
                .get(js.getString("url"));

        Assert.assertEquals(response.asString(), secondResponse.asString());
    }


    @Test
    public void testSeventhMovie() {

        Response response = given()
                .when()
                .get("films/7/");

        Assert.assertEquals(response.getStatusCode(), 404);
    }
}