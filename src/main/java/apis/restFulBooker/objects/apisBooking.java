package apis.restFulBooker.objects;

import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;

public class apisBooking {
    public SHAFT.API api;
    public static final String booking_serviceName = "/booking";

    public apisBooking (SHAFT.API api)
    {
        this.api = api;
    }
    public void createBooking(String firstName, String lastName, String additionalNeeds) {
        String createBookingBody = """
                {
                    "firstname" : "{FIRST_NAME}",
                    "lastname" : "{LAST_NAME}",
                    "totalprice" : 111,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2023-01-01",
                        "checkout" : "2024-01-01"
                    },
                    "additionalneeds" : "{ADDITIONAL_NEEDS}"
                }
                """
                .replace("{FIRST_NAME}", firstName)
                .replace("{LAST_NAME}", lastName)
                .replace("{ADDITIONAL_NEEDS}", additionalNeeds);

        api.post(booking_serviceName)
                .setContentType(ContentType.JSON)
                .setRequestBody(createBookingBody)
                .setTargetStatusCode(apis.SUCCESS)
                .perform();
    }

    public String getBookingId(String firstName, String lastName) {
        api.get(booking_serviceName)
                .setUrlArguments("firstname=" + firstName + "&lastname=" + lastName)
                .perform();
        return api.getResponseJSONValue("$[0].bookingid");
    }


    public void getBookingIds()
    {

       /* List<List<Object>>getBooksIds = Arrays.asList(
                Arrays.asList("firstname", "{FIRST_NAME}"),
                Arrays.asList("lastname", "{LAST_NAME}"),
                Arrays.asList("checkin","{checkin}"),
                Arrays.asList("checkout","{checkout}")
        )

*/              api.get(booking_serviceName)
            //.setParameters(getBooksIds, RestActions.ParametersType.QUERY)
            .setContentType(ContentType.JSON)
            .setTargetStatusCode(200)
            .perform();

    }
    public void updateBook(String firstName, String lastName, String additionalNeeds)
    {
        String updateBook = """ 
                {
             "firstname" : "{FIRST_NAME}",
            "lastname" : "{LAST_NAME}",
            "totalprice" : 1000,
            "depositpaid" : true,
            "bookingdates" : {
             "checkin" : "2023-01-01",
             "checkout" : "2024-01-01"
    },
        "additionalneeds" : "{ADDITIONAL_NEEDS}"
                }
        """
                .replace("{FIRST_NAME}", firstName)
                .replace("{LAST_NAME}", lastName)
                .replace("{ADDITIONAL_NEEDS}", additionalNeeds);

        api.put(booking_serviceName +"/"+ getBookingId("Marina","Nabil"))
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .setRequestBody(updateBook)
                .setTargetStatusCode(200)
                .perform();


    }

    public void deleteBooking(String bookingId) {
        api.delete(booking_serviceName +"/"+ bookingId)
                .setContentType(ContentType.JSON)
                .setTargetStatusCode(apis.SUCCESS_DELETE)
                .perform();

    }


////////// Validations \\\\\\\\\\\\\\\\\

    public void validateThatTheBookingIsCreated(String expectedFirstName, String expectedLastName, String expectedAdditionalNeeds) {
        api.verifyThatResponse().extractedJsonValue("booking.firstname").isEqualTo(expectedFirstName).perform();
        api.verifyThatResponse().extractedJsonValue("booking.lastname").isEqualTo(expectedLastName).perform();
        api.verifyThatResponse().extractedJsonValue("booking.additionalneeds").isEqualTo(expectedAdditionalNeeds).perform();
        api.verifyThatResponse().body().contains("\"bookingid\":").perform();
    }

    public void validateThatTheBookingDeleted() {
        api.verifyThatResponse().body().contains("Created").perform();

    }

    public void validateThatgettingTheBooking(){

    }


}
