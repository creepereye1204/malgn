package com.malgn.configure.monitoring;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TrafficMonitorFilter implements Filter {
    private final AtomicLong requestCount = new AtomicLong(0);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        requestCount.incrementAndGet();
        chain.doFilter(request, response);
    }

    public long getAndResetCount() {
        return requestCount.getAndSet(0);
    }
}
