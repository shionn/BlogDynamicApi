package blog.dynamic;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.stereotype.Component;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Component
public class DiscordBotNotify implements EventListener {
	private static final long CANAL = 1123512494468644984L;

	public void notifyComment(String author, String content, String page) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					JDA jda = buildBot();
//					jda.getTextChannels().stream().forEach(System.out::println);
					TextChannel channel = jda.getTextChannelById(CANAL);
					channel.sendMessage("**" + author + "** dans _" + page + "_\n\n" + content).queue();
					jda.shutdown();
					jda.awaitShutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private JDA buildBot() throws IOException, InterruptedException {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("token.properties"));
		return JDABuilder
				.create(properties.getProperty("token"), Arrays.asList(GatewayIntent.MESSAGE_CONTENT))
				.addEventListeners(this)
				.build()
				.awaitReady()
		;
	}

	@Override
	public void onEvent(GenericEvent event) {
		// nothing to do
	}

}
