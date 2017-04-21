package com.netopyr.reduxfx.driver.http.command;

import com.netopyr.reduxfx.updater.Command;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;
import javaslang.control.Try;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.URL;
import java.util.function.Function;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HttpPutCommand implements Command {

    private final URL url;
    private final Map<String, String> headers;
    private final String body;
    private final Option<Function<Try<String>, Object>> actionMapper;

    public HttpPutCommand(URL url, String body, Function<Try<String>, Object> actionMapper) {
        this(url, HashMap.empty(), body, actionMapper);
    }
    public HttpPutCommand(URL url, Map<String, String> headers, String body, Function<Try<String>, Object> actionMapper) {
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.actionMapper = Option.of(actionMapper);
    }

    public HttpPutCommand(URL url, String body) {
        this(url, HashMap.empty(), body);
    }
    public HttpPutCommand(URL url, Map<String, String> headers, String body) {
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.actionMapper = Option.none();
    }

    public URL getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public Option<Function<Try<String>, Object>> getActionMapper() {
        return actionMapper;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("url", url)
                .append("headers", headers)
                .append("body", body)
                .append("actionMapper", actionMapper)
                .toString();
    }
}
