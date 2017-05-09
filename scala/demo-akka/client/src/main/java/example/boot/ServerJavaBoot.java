package example.boot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import demo.actor.SayHello;
import scala.concurrent.duration.FiniteDuration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static demo.actor.ActorConstants.CLIENT_ACTOR_SYSTEM;
import static demo.actor.ActorConstants.HELLO_ACTOR;

public class ServerJavaBoot {

    public static void main(String[] args) {
        try {
            final ActorSystem system = ActorSystem.create(CLIENT_ACTOR_SYSTEM(), ConfigFactory.parseFile(new File("conf/client.conf")));
            final ActorRef actor = system.actorOf(Props.create(SayHello.class), HELLO_ACTOR());

            system.scheduler().schedule(FiniteDuration.apply(1, TimeUnit.SECONDS), FiniteDuration.apply(2, TimeUnit.SECONDS), () -> {
                actor.tell("Hello world!", ActorRef.noSender());
            }, system.dispatcher());

            system.scheduler().scheduleOnce(FiniteDuration.apply(20, TimeUnit.SECONDS), () -> {
                system.terminate();
            }, system.dispatcher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
