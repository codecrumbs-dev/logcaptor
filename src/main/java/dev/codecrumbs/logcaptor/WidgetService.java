package dev.codecrumbs.logcaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WidgetService {
    private static final Logger LOG = LoggerFactory.getLogger(WidgetService.class);

    void add(Widget widget) {
        LOG.debug("Adding widget with name [{}]", widget.getName());
    }

    void remove(int id) {
        if (id < 1) {
            IllegalArgumentException ex = new IllegalArgumentException(String.format("Widget id must be greater than 0 but was [%s]", id));
            LOG.error("Error removing widget", ex);
            throw ex;
        }
        LOG.info("Removing widget with id [{}]", id);
    }
}
