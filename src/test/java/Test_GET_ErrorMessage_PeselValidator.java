
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.get;

public class Test_GET_ErrorMessage_PeselValidator {

    private static ArrayList<String> initializeArray(String characterProvider) {
        ArrayList<String> specialChars = new ArrayList<>();
        for (char character : characterProvider.toCharArray()) {
            specialChars.add(Character.toString(character));
        }
        return specialChars;
    }

    @Test
    public static void invalidPeselINVCErrorTest() {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=10710241412");
        ArrayList<String> errorCode = response.path("errors.errorCode");
        Assert.assertEquals(errorCode.get(0), "INVC", "The error code does not match");
    }

    @Test
    public static void invalidCharactersPeselNBRQErrorTest() {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=107102414w2");
        ArrayList<String> errorCode = response.path("errors.errorCode");
        Assert.assertEquals(errorCode.get(0), "NBRQ", "The error code does not match");
    }

    @Test
    public static void invalidSumPeselINVCErrorTest() {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=95130167627");
        ArrayList<String> errorCode = response.path("errors.errorCode");
        Assert.assertEquals(errorCode.get(0), "INVC", "The error code does not match");
    }

    @Test
    public static void invalidMonthErrCodeTest() {
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=80150280110");
        ArrayList<String> errorCode = response.path("errors.errorCode");
        Assert.assertEquals(errorCode.get(0), "INVY", "The error code does not match");
        Assert.assertEquals(errorCode.get(1), "INVM", "The error code does not match");
        Assert.assertEquals(errorCode.get(2), "INVD", "The error code does not match");

    }
}
