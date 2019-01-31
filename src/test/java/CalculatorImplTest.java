import com.epam.brest.courses.CalculatorImpl;
import org.junit.Before;
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
        BigDecimal actual = calculator.Calculation(BigDecimal.valueOf(6.7), BigDecimal.valueOf(7.0));
        Assertions.assertEquals(134.0, actual);
    }

    @Test
    public void IncorrectCalc() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl();
        BigDecimal actual = calculator.Calculation(BigDecimal.valueOf(6.7), BigDecimal.valueOf(7.0));
        Assertions.assertEquals(139.0, actual);
    }


    @AfterAll
   static void afterAll() {
        System.out.println("@AfterAll");
    }

}

