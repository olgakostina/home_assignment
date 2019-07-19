import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;

public class CardDetailsServiceTests {
        String loginName;
        String password;
        String pan;

    @Test(description = "Get details of card with ACTIVE status.")
    public void getActiveCardDetail(){
        loginName = "johnsmith1980";
        password = "Johnny_1992";
        pan = "1111222233334444";
        RestAssured.baseURI=EndPoints.baseUrl;
        given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                when().
                get(EndPoints.cardDetail, loginName, pan).
                then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("user.login-name", equalTo(loginName),
                        "user.number", equalTo(pan),
                        "user.status", equalTo("ACTIVE")).
                extract();
    }

    @Test(description = "Get details of card with LOCKED status.")
    public void getLockedCardDetail(){
        loginName = "johansmith1990";
        password = "Johan_1990";
        pan = "1111222233335555";
        RestAssured.baseURI=EndPoints.baseUrl;
        ValidatableResponse res= given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                when().
                get(EndPoints.cardDetail, loginName, pan).
                then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("user.login-name", equalTo(loginName),
                        "user.number", equalTo(pan),
                        "user.status", equalTo("LOCKED"));
    }

    @Test(description = "404 Card not found.")
    public void getCardIsNotFound(){
        String loginName = "johnsmith1980";
        String password = "Johny_1992";
        pan = "1111222233339999";
        String errorKey = "404";
        String errorText = "Card not found";

        RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
        ValidatableResponse res= given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                when().
                get("/user/{login-name}/card/{pan}", loginName, pan).
                then().
                statusCode(404).and().contentType(ContentType.JSON).and().
                body("error-response.error.error-key", equalTo(errorKey),
                        "error-response.error.error-message", equalTo(errorText));
    }
}
