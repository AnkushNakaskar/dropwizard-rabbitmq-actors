package io.appform.dropwizard.actors.observers;

import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author ankush.nakaskar
 * An Observer that ingest tracing in MDC.
 */
@Slf4j
public class MDCTracingRMQObserver extends RMQObserver {

    public static final String TRACE_ID = "TRACE_ID";

    public MDCTracingRMQObserver() {
        super(null);
    }

    @Override
    public <T> T executePublish(final PublishObserverContext context,
                                final Function<PublishObserverContext, T> function) {
        String traceId = MDC.get(TRACE_ID);
        if (StringUtils.isNotBlank(traceId)) {
            context.getHeaders()
                    .put(TRACE_ID, traceId);
        }
        return proceedPublish(context, function);
    }

    @Override
    public <T> T executeConsume(final ConsumeObserverContext context,
                                final Function<ConsumeObserverContext, T> function) {
        if (context.getHeaders() != null && context.getHeaders()
                .containsKey(TRACE_ID)) {
            MDC.put(TRACE_ID, String.valueOf(context.getHeaders()
                    .get(TRACE_ID)));
        }
        return proceedConsume(context, function);
    }
}
