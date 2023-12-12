import apis.restFulBooker.objects.apis;
import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import apis.restFulBooker.objects.apisBooking;

import java.util.Arrays;
import java.util.List;

public class restFulAPILinearDesign {
    public SHAFT.API api;
    private apisBooking ApisBooking;

    @Test
    public void createBookingTest() {
        ApisBooking.createBooking("Marina", "Nabil", "sleep");
        ApisBooking.validateThatTheBookingIsCreated("Marina", "Nabil", "sleep");
    }


    @Test(dependsOnMethods = "deleteBookingTest")
    public void getBookingTest() {
        ApisBooking.getBookingIds();
        ApisBooking.validateThatgettingTheBooking();
    }

    @Test(dependsOnMethods = "createBookingTest")
    public void updateBookingTest() {

        ApisBooking.updateBook("James", "Brown", "Breakfast");
    }

    @Test (dependsOnMethods = "updateBookingTest")
    public void deleteBookingTest() {
        ApisBooking.deleteBooking(ApisBooking.getBookingId("James", "Brown"));
        ApisBooking.validateThatTheBookingDeleted();
    }

    /////////// Configurations \\\\\\\\\\\\\\\
    @BeforeClass
    public void beforeClass() {
        api = new SHAFT.API(apis.BASEURL);

        apis Apis = new apis(api);
        Apis.login("admin", "password123");

        ApisBooking = new apisBooking(api);


    }


}

