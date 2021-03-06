package com.qthegamep.gnikcom_bot.command;

import com.qthegamep.gnikcom_bot.util.CommandTextsUtil;
import com.qthegamep.gnikcom_bot.util.ApplicationUtil;

import lombok.val;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public class InviteCommand extends BaseCommand implements Command {

    @Override
    public SendMessage buildResponse(Update update) {
        enableNotification();
        val message = update.getMessage();
        setChatId(message);
        setText(message);
        return response;
    }

    private void setText(Message message) {
        val text = getText(message);
        response.setText(text);
    }

    private String getText(Message message) {
        val newChatMembers = message.getNewChatMembers();
        if (containsThisBot(newChatMembers)) {
            val text = CommandTextsUtil.getRandomTextFrom(CommandTextsUtil.INVITE_BOT_COMMAND_TEXTS);
            return formatToItalicText(text);
        }
        if (isOneNewChatMember(newChatMembers)) {
            val text = CommandTextsUtil.getRandomTextFrom(CommandTextsUtil.INVITE_USER_COMMAND_TEXTS);
            return formatToItalicText(text);
        } else {
            val text = CommandTextsUtil.getRandomTextFrom(CommandTextsUtil.INVITE_USERS_COMMAND_TEXTS);
            return formatToItalicText(text);
        }
    }

    private boolean containsThisBot(List<User> newChatMembers) {
        val botUserName = System.getProperty(ApplicationUtil.TELEGRAM_BOT_USERNAME);
        return newChatMembers.stream()
                .map(User::getUserName)
                .anyMatch(botUserName::equals);
    }

    private boolean isOneNewChatMember(List<User> newChatMembers) {
        return newChatMembers.size() == 1;
    }
}
