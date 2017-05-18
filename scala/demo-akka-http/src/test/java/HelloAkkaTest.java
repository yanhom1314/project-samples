import akka.actor.ActorSystem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;


public class HelloAkkaTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        system.terminate();
        system.awaitTermination(Duration.create("10 seconds"));
    }

    @Test
    public void testHello() {
        System.out.println("Hello Test!");
    }
}
