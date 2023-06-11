package com.rachev.ethereumfetcher.scheduler;

import com.rachev.ethereumfetcher.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObsoleteTokenSweepScheduler {

    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 1 1 * * ?")
    public void sweepObsoleteTokens() {
        tokenRepository.deleteByExpiredTrueAndRevokedTrue();
    }
}
