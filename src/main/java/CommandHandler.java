import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {

  private MessageChannel copy;
  private List<Message> list;
  private MessageChannel paste;

  @Override
  public void onMessageReceived(MessageReceivedEvent e) {

    if (!e.getAuthor().isBot()) {

      Message msg = e.getMessage();
      String sMsg = msg.getContentRaw();

      if (sMsg.equals(".copy")) {
        copy(e);
      } else if (sMsg.equals(".paste")) {
        paste(e);
      }
    }
  }

  private void copy(MessageReceivedEvent e) {
    this.copy = e.getChannel();

    List<Message> rlist = copy.getHistoryFromBeginning(100).complete().getRetrievedHistory();
    this.list = new ArrayList<Message>(rlist);
    Collections.reverse(list);

    while (!rlist.isEmpty()) {
      rlist.forEach(msg -> System.out.println(msg.getContentRaw()));
      rlist = copy.getHistoryAfter(rlist.get(0), 100).complete().getRetrievedHistory();
      List<Message> temp = new ArrayList<Message>(rlist);
      Collections.reverse(temp);
      this.list.addAll(temp);
    }

    System.out.println(
        "Copying: "
            + copy.getHistoryFromBeginning(1)
                .complete()
                .getRetrievedHistory()
                .get(0)
                .getContentRaw());
    System.out.println(list.size() + " msgs copied");
  }

  private void paste(MessageReceivedEvent e) {
    System.out.println("pasting");
    this.paste = e.getChannel();

    for (Message msg : this.list) {

      List<Attachment> att = msg.getAttachments();

      if (!att.isEmpty()) {
        System.out.println(msg.getJumpUrl());
        paste
            .sendMessage(
                "FROM: "
                    + msg.getAuthor().getAsTag()
                    + " / "
                    + msg.getAuthor().getId()
                    + " on"
                    + msg.getTimeCreated()
                    + "\n"
                    + msg.getContentRaw()
                    + "\n"
                    + att.get(0).getUrl())
            .queue();
      } else {
        System.out.println(msg.getJumpUrl());
        paste
            .sendMessage(
                "FROM: "
                    + msg.getAuthor().getAsTag()
                    + " / "
                    + msg.getAuthor().getId()
                    + " on"
                    + msg.getTimeCreated()
                    + "\n"
                    + msg.getContentRaw())
            .queue();
      }
    }
    System.out.println("DONE!!!!!!");
  }
}
