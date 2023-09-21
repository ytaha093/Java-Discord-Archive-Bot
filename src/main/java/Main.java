import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main<E extends Comparable> {

  public static void main(String[] args) { // add fighting system

    JDABuilder jdaBuilder =
        JDABuilder.createDefault("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    JDA jda = null;
    CommandHandler fkOff = new CommandHandler();
    jdaBuilder.addEventListeners(fkOff);

    try {
      jda = jdaBuilder.build();
    } catch (LoginException e) {
      e.printStackTrace();
    }
    jda.getPresence()
        .setActivity(
            Activity.listening(
                "🤖beep boop🤖"));
    // jda.getPresence().setActivity(Activity.playing("New .stats and .levelup commands!!"));

  }
}
