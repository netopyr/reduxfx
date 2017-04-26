package com.netopyr.reduxfx.driver.http.command;

import com.netopyr.reduxfx.updater.Command;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Try;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.URL;
import java.util.function.Function;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HttpGetCommand implements Command {

    private final URL url;
    private final Map<String, String> headers;
    private final Function<Try<String>, Object> actionMapper;

    public HttpGetCommand(URL url, Function<Try<String>, Object> actionMapper) {
        this(url, HashMap.empty(), actionMapper);
    }
    public HttpGetCommand(URL url, Map<String, String> headers, Function<Try<String>, Object> actionMapper) {
        this.url = url;
        this.headers = headers;
        this.actionMapper = actionMapper;
    }

    public URL getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Function<Try<String>, Object> getActionMapper() {
        return actionMapper;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("url", url)
                .append("headers", headers)
                .append("actionMapper", actionMapper)
                .toString();
    }
}
