package com.greasecake.kooshots.service;

import com.greasecake.kooshots.entity.log.Log;
import com.greasecake.kooshots.repository.LogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    final LogRepository repository;

    public LogService(LogRepository repository) {
        this.repository = repository;
    }

    public Page<Log> getLastLogEntries(int limit) {
        return repository.findAll(PageRequest.of(0, limit, Sort.by("datetime")));
    }
}
