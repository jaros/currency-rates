package com.german.downloader.message;

import java.util.Collection;

public interface MessageRepository {

    Collection<Message> findAll();

    Message save(Message message);

    Message findMessage(Long id);

    void deleteMessage(Long id);

}
