
import com.epam.brest.courses.CalculatorImpl;
import com.epam.brest.courses.CalculatorTarif;
import org.opentest4j.AssertionFailedError;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;


public class CalculatorImplTest {


    @BeforeAll
    static void beforeAll() {
        System.out.println("@BeforeEach");
    }

    @Test
    public void Tarif()  throws Exception {
        BigDecimal actual = CalculatorTarif.calculateTarif(BigDecimal.valueOf(4.4), "distance");
        Assertions.assertEquals(BigDecimal.valueOf(20.0), actual);
    }

    @Test
    public void IncorrectTarif()  throws Exception {
        BigDecimal actual = CalculatorTarif.calculateTarif(BigDecimal.valueOf(4.4), "distance");
        Assertions.assertThrows(AssertionFailedError.class, () -> {
            Assertions.assertEquals(BigDecimal.valueOf(20.6), actual);
    });
    }
    @Test
    public void Calc() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl();
        BigDecimal actual = calculator.calculate(BigDecimal.valueOf(5.4), BigDecimal.valueOf(5.6), BigDecimal.valueOf(8.7));
        Assertions.assertEquals(BigDecimal.valueOf(263.09), actual);
    }

    @Test
    public void IncorrectCalc() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl();
        BigDecimal actual = calculator.calculate(BigDecimal.valueOf(5.4), BigDecimal.valueOf(5.6), BigDecimal.valueOf(8.7));
        Assertions.assertThrows(AssertionFailedError.class, () -> {
            Assertions.assertEquals(BigDecimal.valueOf (200.5), actual);
        });
    }

    @AfterAll
   static void afterAll() {
        System.out.println("@AfterAll");
    }

}

