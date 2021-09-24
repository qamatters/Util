import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;


public class RestAssuredOAuth2 {

   static String clientID = "_cZg581bRkaS28lM0PxtbA";
    static String secret = "Cg7LoyTuCTF5YwGHuRQEI9ZloXHELFh9";
    static String accessToken;

    @Test (priority = 1)
    public void getAccessToken() {
       Response response = given()
                .auth().preemptive()
                .basic(clientID,secret)
                .param("grant_type", "client_credentials")
                .log().all()
                .post("https://zoom.us/oauth/token");
       response.prettyPrint();
       System.out.println("Response status is "  + response.statusCode());
       accessToken = response.getBody().path("access_token");
       System.out.println("Access token is "  + accessToken);
    }

    @Test (priority = 2, dependsOnMethods = "getAccessToken")
    public void listAllZoomMeetings() {
        Response response = given()
                            .contentType(ContentType.JSON)
                            .auth()
                             .oauth2(accessToken)
                               .log().all()
                .get(" https://api.zoom.us/v2/users/mathpal.deepak1@gmail.com/meetings");
        response.prettyPrint();
        System.out.println("Response status is "  + response.statusCode());
    }

}
