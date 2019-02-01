import com.epam.brest.courses.CalculatorImpl;
import org.opentest4j.AssertionFailedError;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;


public class CalculatorImplTest {


    @BeforeAll
    static void beforeAll() {
        System.out.println("@BeforeEach");
    }

    @Test
    public void Calc() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl();
        BigDecimal actual = calculator.Calculation(BigDecimal.valueOf(5.4), BigDecimal.valueOf(5.6));
        Assertions.assertEquals(BigDecimal.valueOf(59.4), actual);
    }

    @AfterAll
   static void afterAll() {
        System.out.println("@AfterAll");
    }

}

