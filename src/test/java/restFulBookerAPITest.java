import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;

import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;


public class restFulBookerAPITest {
    SHAFT.API api;
    String token;
    String ID;

    @Test(priority = 1)
    public void getTokenTest(){
        String tokenBody = """
                  {
                    "username" : "admin",
                    "password" : "password123"
                }
                """;
        api.post("/auth")
                .setContentType(ContentType.JSON)
                .setRequestBody(tokenBody)
                .perform();

        token = api.getResponseJSONValue("token");
      api.assertThatResponse().body().contains("\"token\":").perform();

    }

    @Test
    public void createBookingTest() {
        String createBookingBody = """
                {
                    "firstname" : "Marina",
                    "lastname" : "Nabil",
                    "totalprice" : 111,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2023-01-01",
                        "checkout" : "2024-01-01"
                    },
                    "additionalneeds" : "Hot Chocolate"
                }
                """;
        api.post("/booking")
                .setContentType(ContentType.JSON)
                .setRequestBody(createBookingBody)
                .setTargetStatusCode(200)
                .perform();

        ID = api.getResponseJSONValue("bookingid");

        api.verifyThatResponse().extractedJsonValue("booking.lastname").isEqualTo("Nabil").perform();
        api.verifyThatResponse().extractedJsonValue("booking.additionalneeds").isEqualTo("Hot Chocolate").perform();
        api.verifyThatResponse().body().contains("\"bookingid\":").perform();
    }

    @Test(dependsOnMethods = "getTokenTest")
    public void deleteBookingTest() {

        api.delete("/booking/"+ID)
                .setContentType(ContentType.JSON)
                .addHeader("Cookie", "token=" + token)
                .setTargetStatusCode(201)
                .perform();

    }
    @Test(dependsOnMethods = "createBookingTest")
    public void getBookingIds()
    {
/*
        List<List<Object>>getBooksIds = Arrays.asList(
                Arrays.asList("firstname", "Marina"),
                Arrays.asList("lastname", "Nabil"),
                Arrays.asList("checkin","2023-01-01"),
                Arrays.asList("checkout","2024-01-01")
        );
        */
        api.get("/booking")
                //.setParameters(getBooksIds, RestActions.ParametersType.QUERY)
                .setTargetStatusCode(200)
                .perform();

    }

@Test
   public void getbook()
    {


                api.get("/booking/"+"12")
                        .addHeader("Accept", "application/json")
                        .setTargetStatusCode(200)
                        .perform();

            }
            @Test(dependsOnMethods = "getTokenTest")
            public  void updateBook()
            {
                String updateBook = """
                {
                    "firstname" : "James",
                    "lastname" : "Brown",
                    "totalprice" : 111,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2018-01-01",
                        "checkout" : "2019-01-01"
                    },
                    "additionalneeds" : "Breakfast"
                }
                """;
                api.put("/booking/"+"12")
                        .setContentType(ContentType.JSON)
                        .addHeader("Accept", "application/json")
                        .addHeader("Cookie", "token=" + token)
                        .setRequestBody(updateBook)
                        .setTargetStatusCode(200)
                        .perform();


            }

            @BeforeClass
            public void beforeClass(){

                api = new SHAFT.API("https://restful-booker.herokuapp.com");


            }

        }
