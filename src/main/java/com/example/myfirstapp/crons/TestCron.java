package com.example.myfirstapp.crons;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TimeZone;

@Component
public class TestCron {

    @Scheduled(cron="*/5 * * * * *")
    @SchedulerLock(name = "job1",
            lockAtMostFor = "2m")
    public void demoCron() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("i am running "+ new Date());
    }

    @Scheduled(cron="*/5 * * * * *")
    @SchedulerLock(name = "job2",
            lockAtMostFor = "2m")
    public void demoCron2() {
        System.out.println("i am running again "+ new Date());
    }
}
