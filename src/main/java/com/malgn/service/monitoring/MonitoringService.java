package com.malgn.service.monitoring;

import com.malgn.configure.monitoring.TrafficMonitorFilter;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final SlackNotifier slackNotifier;
    private final TrafficMonitorFilter trafficMonitor;
    private final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    @EventListener(ApplicationReadyEvent.class)
    public void onStart() {
        log.info("시스템 시작: 서버 기동 완료 및 서비스 시작");
        slackNotifier.send("[서버 시작됨] 모든 시스템이 정상적으로 기동되었으며 서비스를 시작합니다.");
    }

    @PreDestroy
    public void onStop() {
        log.warn("시스템 종료: 서버가 정상 종료 프로세스를 시작합니다.");
        slackNotifier.send("[서버 종료 중] 서버 종료가 감지되어 서비스를 중단합니다.");
    }

    @Scheduled(fixedRate = 60000)
    public void monitorResources() {
        double cpuLoad = osBean.getCpuLoad() * 100;
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        double memoryUsage = (double) (totalMemory - freeMemory) / totalMemory * 100;

        long requestCount = trafficMonitor.getAndResetCount();
        
        if (cpuLoad > 80.0) {
            String message = String.format("[CPU 경고] 높은 CPU 사용률 감지: %.2f%%", cpuLoad);
            log.error(message);
            slackNotifier.send(message);
        }

        if (memoryUsage > 80.0) {
            String message = String.format("[메모리 경고] 높은 메모리 사용률 감지: %.2f%%", memoryUsage);
            log.error(message);
            slackNotifier.send(message);
        }

        if (requestCount > 100) {
            String message = String.format("[트래픽 과부하] 1분간 %d건의 과도한 요청이 감지되었습니다!", requestCount);
            log.warn(message);
            slackNotifier.send(message);
        }
    }
}
