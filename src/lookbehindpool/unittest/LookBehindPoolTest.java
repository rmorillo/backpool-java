package lookbehindpool.unittest;
import lookbehindpool.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LookBehindPoolTest {

    @Test
    public void LookBehindPool_Constructor_CreatesObject()
    {
        //Arrange
        int capacity = 10;

        //Act
        LookBehindPool lbp = new LookBehindPool(capacity, p -> 0);

        //Assert

        Assertions.assertNotNull(lbp);
        Assertions.assertEquals(capacity, lbp.capacity());
    }
}
