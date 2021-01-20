package dev.codecrumbs.logcaptor;

import lombok.Value;

@Value
public class Widget {
    String name;

    public static Widget of(String name) {
        return new Widget(name);
    }
}
