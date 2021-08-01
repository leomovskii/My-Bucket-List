package ua.leomovskii.tg.mybucketlist;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import ua.leomovskii.tg.mybucketlist.bot.TelegramBot;

public class Launcher {

	public static void main(String[] args) {
		try {
			TelegramBot bot = new TelegramBot();
			new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
		} catch (TelegramApiException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

}