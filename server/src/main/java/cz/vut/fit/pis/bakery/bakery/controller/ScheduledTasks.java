package cz.vut.fit.pis.bakery.bakery.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cz.vut.fit.pis.bakery.bakery.repository.ProductRepository;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final int maximalProductCount = 1000;
    // Cron timer in format: second, minute, hour, day, month, year
    private static final String cronTimer = "0 0 0 * * *";

    @Autowired
    private ProductRepository productHandler;

    @Scheduled(cron = cronTimer)
    public void reportCurrentTime() {
        productHandler.resetTotalAmount(maximalProductCount);

        log.info(dateFormat.format(new Date())  + ": reseting maximal amount of product to: " + maximalProductCount);
    }
}
