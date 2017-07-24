package simulator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void cleanUpStreams () {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    void testMainOptimalWithThreeFramesAndSilberschatzExample () {
        Main.main(new String[]{"/home/luiz/GHP/memory-pagination-simulator/traces/silberschatz.txt", "optimal", "3"});
        assertEquals("Method: Optimal\n" +
                "Number of frames: 3\n" +
                "Frames/Pages size: 144 addresses.\n" +
                "\tOffset: 12 bits\n" +
                "\tAddress: 20 bits\n\n" +
                "\uD83C\uDF21️ Initializing memory...\n" +
                "Page fault #1 at address 7 in position 0\n" +
                "{7,Ø,Ø}\n" +
                "\n" +
                "Page fault #2 at address 0 in position 1\n" +
                "{7,0,Ø}\n" +
                "\n" +
                "Page fault #3 at address 1 in position 2\n" +
                "{7,0,1}\n" +
                "\n" +
                "Page fault #4 at address 2 in position 0\n" +
                "{2,0,1}\n" +
                "\n" +
                "Page fault #5 at address 3 in position 2\n" +
                "{2,0,3}\n" +
                "\n" +
                "Page fault #6 at address 4 in position 1\n" +
                "{2,4,3}\n" +
                "\n" +
                "Page fault #7 at address 0 in position 1\n" +
                "{2,0,3}\n" +
                "\n" +
                "Page fault #8 at address 1 in position 2\n" +
                "{2,0,1}\n" +
                "\n" +
                "Page fault #9 at address 7 in position 0\n" +
                "{7,0,1}\n\n", outContent.toString());
    }

    @Test
    void testMainFifoWithThreeFramesAndSilberschatzExample () {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        Main.main(new String[]{"/home/luiz/GHP/memory-pagination-simulator/traces/silberschatz.txt", "fifo", "3"});
        assertEquals("Method: FIFO\n" +
                "Number of frames: 3\n" +
                "Frames/Pages size: 144 addresses.\n" +
                "\tOffset: 12 bits\n" +
                "\tAddress: 20 bits\n\n" +
                "\uD83C\uDF21️ Initializing memory...\n" +
                "Page fault #1 at address 7\n" +
                "{7,Ø,Ø}\n" +
                "\n" +
                "Page fault #2 at address 0\n" +
                "{7,0,Ø}\n" +
                "\n" +
                "Page fault #3 at address 1\n" +
                "{7,0,1}\n" +
                "\n" +
                "Page fault #4 at address 2\n" +
                "{2,0,1}\n" +
                "\n" +
                "Page fault #5 at address 3\n" +
                "{2,3,1}\n" +
                "\n" +
                "Page fault #6 at address 0\n" +
                "{2,3,0}\n" +
                "\n" +
                "Page fault #7 at address 4\n" +
                "{4,3,0}\n" +
                "\n" +
                "Page fault #8 at address 2\n" +
                "{4,2,0}\n" +
                "\n" +
                "Page fault #9 at address 3\n" +
                "{4,2,3}\n" +
                "\n" +
                "Page fault #10 at address 0\n" +
                "{0,2,3}\n" +
                "\n" +
                "Page fault #11 at address 1\n" +
                "{0,1,3}\n" +
                "\n" +
                "Page fault #12 at address 2\n" +
                "{0,1,2}\n" +
                "\n" +
                "Page fault #13 at address 7\n" +
                "{7,1,2}\n" +
                "\n" +
                "Page fault #14 at address 0\n" +
                "{7,0,2}\n" +
                "\n" +
                "Page fault #15 at address 1\n" +
                "{7,0,1}\n" +
                "\n", outContent.toString());
    }

}