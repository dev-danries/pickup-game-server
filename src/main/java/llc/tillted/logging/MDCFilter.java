package llc.tillted.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Order(1)
@Component
public class MDCFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger("EVENT");
    private final String X_REQUEST_ID = "X-Request-ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        long startTime = Instant.now().toEpochMilli();

        try {
            addXRequestId(req);

            LOGGER.info("Path: {}, Method: {}, Query: {}", req.getRequestURI(), req.getMethod(),
                    req.getQueryString());

            res.setHeader(X_REQUEST_ID, MDC.get(X_REQUEST_ID));

            chain.doFilter(request, response);
        } finally {
            LOGGER.info("Status Code: {}, Path: {}, Method: {}, Query: {}, Time Taken: {}ms",
                    res.getStatus(), req.getRequestURI(), req.getMethod(), req.getQueryString(),
                    (Instant.now().toEpochMilli() - startTime));
            MDC.clear();
        }
    }

    private void addXRequestId(HttpServletRequest request) {
        String xRequestId = request.getHeader(X_REQUEST_ID);
        if (xRequestId == null) {
            MDC.put(X_REQUEST_ID, UUID.randomUUID().toString());
        } else {
            MDC.put(X_REQUEST_ID, xRequestId);
        }
    }

}
