package com.greasecake.kooshots.repository;

import com.greasecake.kooshots.entity.ConversationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationStateRepository extends JpaRepository<ConversationState, Long> {
    ConversationState findByChatId(long chatId);
    void deleteByChatId(long chatId);
    boolean existsByChatId(long chatId);
}
