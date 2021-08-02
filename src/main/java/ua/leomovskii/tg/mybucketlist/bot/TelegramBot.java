package ua.leomovskii.tg.mybucketlist.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class TelegramBot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			login(update.getMessage().getFrom());

			if (update.getMessage().hasText())
				if (update.getMessage().getText().charAt(0) == '/')
					CommandFilter.run(this, update.getMessage());
				else
					MessageFilter.run(this, update.getMessage());
		} else if (update.hasCallbackQuery())
			CallbackFilter.run(this, update.getCallbackQuery());
	}

	private void login(User u) {
		// TODO TelegramBot -> login: if user is new - add him to db
	}

	@Override
	public String getBotUsername() {
		return System.getenv("BOT_NAME");
	}

	@Override
	public String getBotToken() {
		return System.getenv("BOT_TOKEN");
	}

}