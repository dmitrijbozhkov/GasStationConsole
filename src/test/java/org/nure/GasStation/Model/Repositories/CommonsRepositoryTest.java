package org.nure.GasStation.Model.Repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonsRepositoryTest {

    @Autowired
    private CommonsRepository repo;

    @Test
    public void testRandomStringGenerationLength() {
        int idLength = 5;
        String generatedId = repo.generateStringId(idLength);
        assertEquals(generatedId.length(), idLength);
    }
}
