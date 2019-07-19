import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class UserRegistrationServiceTest {
    Calendar calendar = new GregorianCalendar(1992,
            Calendar.SEPTEMBER, 24);
    ConvertorUser convertorUser = new ConvertorUser();

    @Test(description = "Create user request with all fields" )
    public void sendFullRequest() throws IOException {

        User user = new User("MR", "John", "Smith", calendar.getTime(),
                "+491234567890", "abc@def.co.de", "johnsmith1992", "Johnny_1992"  );
        RestAssured.baseURI = EndPoints.baseUrl;
        given().
                contentType(ContentType.JSON).
                body(convertorUser.objectToJSON(user)).
                relaxedHTTPSValidation().
        when().
                post(EndPoints.users).

        then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("register-user-response.login-name",equalTo(user.getLoginName()),
                        "register-user-response.login-name",equalTo("ACTIVE"),
                        "register-user-response.login-name",notNullValue()).
                extract();
    }

    @Test(description = "Create user request with only required fields" )
    public void sendRequiredRequest() throws IOException {

        User user = new User("MRS", "Johan", "Smith", calendar.getTime(),
                "+491234567895", null, null, "Johnny_1992"  );
        RestAssured.baseURI = EndPoints.baseUrl;
        given().
                contentType(ContentType.JSON).
                body(convertorUser.objectToJSON(user)).
                relaxedHTTPSValidation().
                when().
                post(EndPoints.users).

                then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("register-user-response.login-name",equalTo(user.getLoginName()),
                        "register-user-response.login-name",equalTo("ACTIVE"),
                        "register-user-response.login-name",notNullValue()).
                extract();
    }

    @Test(description = "Create user request with not-unique login-name field" )
    public void sendNotUniqueRequest() throws IOException {

        User user = new User("MS", "Jo", "Smith", calendar.getTime(),
                "+491234567891", "ghf@def.co.de", "johnsmith1992", "Johnny_1992"  );
        RestAssured.baseURI = EndPoints.baseUrl;
        given().
                contentType(ContentType.JSON).
                body(convertorUser.objectToJSON(user)).
                relaxedHTTPSValidation().
                when().
                post(EndPoints.users).

                then().
                statusCode(403).and().contentType(ContentType.JSON).and().
                body("error-response.error-code",equalTo(403),
                        "error-response.error.error-key",equalTo("USER_WITH_THIS_LOGIN_NAME_EXISTS"),
                        "error-response.error.error-message",equalTo("User with this login name already exists")).
                extract();
    }
    @Test(description = "Create user request with not-unique login-name field (difference is in whitespace at the beginning" )
    public void sendNotUniqueBeginSpaceRequest() throws IOException {

        User user = new User("MRS", "Johan", "Smith", calendar.getTime(),
                "+491234567892", "nj@def.co.de", " johnsmith1992", "Johnny_1992"  );
        RestAssured.baseURI = EndPoints.baseUrl;
        given().
                contentType(ContentType.JSON).
                body(convertorUser.objectToJSON(user)).
                relaxedHTTPSValidation().
                when().
                post(EndPoints.users).

                then().
                statusCode(403).and().contentType(ContentType.JSON).and().
                body("error-response.error-code",equalTo(403),
                        "error-response.error.error-key",equalTo("USER_WITH_THIS_LOGIN_NAME_EXISTS"),
                        "error-response.error.error-message",equalTo("User with this login name already exists")).
                extract();
    }

    @Test(description = "Create user request with not-unique login-name field (difference is in whitespace at the end" )
    public void sendNotUniqueEndSpaceRequest() throws IOException {

        User user = new User("MRS", "Joh", "Smith", calendar.getTime(),
                "+491234567893", "pl@def.co.de", "johnsmith1992 ", "Johnny_1992"  );
        RestAssured.baseURI = EndPoints.baseUrl;
        given().
                contentType(ContentType.JSON).
                body(convertorUser.objectToJSON(user)).
                relaxedHTTPSValidation().
                when().
                post(EndPoints.users).

                then().
                statusCode(403).and().contentType(ContentType.JSON).and().
                body("error-response.error-code",equalTo(403),
                        "error-response.error.error-key",equalTo("USER_WITH_THIS_LOGIN_NAME_EXISTS"),
                        "error-response.error.error-message",equalTo("User with this login name already exists")).
                extract();
    }

    @Test(description = "Create identical user request as in Test #1" )
    public void sendFullIdenticalRequest() throws IOException {

        User user = new User("MR", "John", "Smith", calendar.getTime(),
                "+491234567890", "abc@def.co.de", "johnsmith1992", "Johnny_1992"  );
        RestAssured.baseURI = EndPoints.baseUrl;
        given().
                contentType(ContentType.JSON).
                body(convertorUser.objectToJSON(user)).
                relaxedHTTPSValidation().
                when().
                post(EndPoints.users).

                then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("register-user-response.login-name",equalTo(user.getLoginName()),
                        "register-user-response.login-name",equalTo("ACTIVE"),
                        "register-user-response.login-name",notNullValue()).
                extract();
    }

}
