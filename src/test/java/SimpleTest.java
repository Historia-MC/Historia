import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import dev.boooiil.historia.classes.HistoriaPlayer;

public class SimpleTest {
 
    @Test
    void playerHealth_CorrectlyModified() {
                
        HistoriaPlayer historiaPlayer = new HistoriaPlayer(UUID.fromString("a130f309-323f-4b99-87f4-18ef5d66498b"));

        assertEquals(10, historiaPlayer.getBaseHealth());

    }

}
