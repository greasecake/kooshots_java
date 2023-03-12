package com.greasecake.kooshots.service;

import com.greasecake.kooshots.entity.ConversationState;
import com.greasecake.kooshots.repository.ConversationStateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationStateService {
    final ConversationStateRepository repository;

    public ConversationStateService(ConversationStateRepository repository) {
        this.repository = repository;
    }

    public void resetState(long chatId) {
        repository.deleteByChatId(chatId);
    }

    public String getState(long chatId) {
        return Optional.ofNullable(repository.findByChatId(chatId))
                .map(ConversationState::getState)
                .orElse(null);
    }

    public void setState(long chatId, String state) {
        if (state == null) {
            resetState(chatId);
        }
        ConversationState entry = repository.findByChatId(chatId);
        entry = entry == null ? new ConversationState() : entry;
        entry.setState(state);
        entry.setChatId(chatId);
        repository.save(entry);
    }

    public boolean isState(long chatId, String state) {
        return Optional.ofNullable(repository.findByChatId(chatId))
                .map(ConversationState::getState)
                .map(s -> s.equals(state))
                .orElse(false);
    }

    public boolean hasState(long chatId) {
        return repository.existsByChatId(chatId);
    }
}
