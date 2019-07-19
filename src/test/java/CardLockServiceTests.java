import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CardLockServiceTests {
    String loginName = "johnsmith1980";
    String password = "Johnny_1992";
    String pan = "1111222233334444";
    String logRefID = "A1234567891123456789212345678931";
    String lockReason = "{" +"\"locking-reason\":\"lost card\"}";

    @Test(description = "Lock card with ACTIVE status")
    public void lockOneRequest(){

        RestAssured.baseURI=EndPoints.baseUrl;

        given().auth().basic(loginName, password).
            relaxedHTTPSValidation().
            body(lockReason).
        when().
            post(EndPoints.cardLock,loginName,pan,logRefID).
        then().statusCode(200).and().contentType(ContentType.JSON).extract();;

        given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                when().
                get(EndPoints.cardDetail, loginName, pan).
                then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("card.holder-name", equalTo("John Smith"),
                        "card.pan", equalTo(pan),
                        "card.status", equalTo("LOCKED")).extract();;
    }

    @Test(description = "send second identical request like in Test #1 to lock card")
    public void lockTwoIdenticalRequests(){

        RestAssured.baseURI=EndPoints.baseUrl;

        given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                body(lockReason).
                when().
                post(EndPoints.cardLock,loginName,pan,logRefID).
                then().statusCode(200).and().contentType(ContentType.JSON).extract();;

        given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                when().
                get(EndPoints.cardDetail, loginName, pan).
                then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("card.holder-name", equalTo("John Smith"),
                        "card.pan", equalTo(pan),
                        "card.status", equalTo("LOCKED")).extract();
    }

    @Test(description = "Lock already locked card")
    public void lockAlreadyLockedCardRequest(){
        logRefID = "B1234567891123456789212345678931";
        RestAssured.baseURI=EndPoints.baseUrl;

        given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                body(lockReason).
                when().
                post(EndPoints.cardLock,loginName,pan,logRefID).
                then().statusCode(403).and().contentType(ContentType.JSON).and().
                body("error-response.error-code", CoreMatchers.equalTo(403),
                        "error-response.error.error-key", CoreMatchers.equalTo("CARD_ALREADY_LOCKED"),
                        "error-response.error.error-message", CoreMatchers.equalTo("Card is already in LOCKED state")).
                extract();


        given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                when().
                get(EndPoints.cardDetail, loginName, pan).
                then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("card.status", equalTo("LOCKED")).
        extract();
    }


    @Test(description = "Lock user with not-unique transaction number")
    public void lockNotUniqueTransactionRequest() throws IOException {

        GregorianCalendar calendar = new GregorianCalendar(1992,
                Calendar.SEPTEMBER, 24);
        ConvertorUser convertorUser = new ConvertorUser();

        User user = new User("MR", "Olga", "Smith", calendar.getTime(),
                "+491234567896", "olga@def.co.de", "olgasmith1992", "Olga_1992"  );

        logRefID = "A1234567891123456789212345678931";

        RestAssured.baseURI = EndPoints.baseUrl;
        given().
                contentType(ContentType.JSON).
                body(convertorUser.objectToJSON(user)).
                relaxedHTTPSValidation().
                when().
                post(EndPoints.users).
                then().
                statusCode(200).and().contentType(ContentType.JSON);


        given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                body(lockReason).
                when().
                post(EndPoints.cardLock,loginName,pan,logRefID).
                then().statusCode(403).and().contentType(ContentType.JSON).and().
                body("error-response.error-code", CoreMatchers.equalTo(403),
                        "error-response.error.error-key", CoreMatchers.equalTo("TRANSACTION_ALREADY_USED"),
                        "error-response.error.error-message", CoreMatchers.equalTo("Transaction with the same " + logRefID + " already exists on the server")).
                extract();


        given().auth().basic(loginName, password).
                relaxedHTTPSValidation().
                when().
                get(EndPoints.cardDetail, loginName, pan).
                then().
                statusCode(200).and().contentType(ContentType.JSON).and().
                body("card.status", equalTo("ACTIVE")).
                extract();
    }
}
