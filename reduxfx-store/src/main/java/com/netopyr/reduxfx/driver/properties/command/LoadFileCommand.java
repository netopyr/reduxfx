package com.netopyr.reduxfx.driver.properties.command;

import com.netopyr.reduxfx.updater.Command;
import io.vavr.collection.Map;
import io.vavr.control.Try;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.function.Function;

@SuppressWarnings({"WeakerAccess", "unused"})
public class LoadFileCommand implements Command {

    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Path path;
    private final Charset charset;
    private final Function<Try<Map<String, String>>, Object> actionMapper;

    public LoadFileCommand(Path path, Function<Try<Map<String, String>>, Object> actionMapper) {
        this(path, ISO_8859_1, actionMapper);
    }
    public LoadFileCommand(Path path, Charset charset, Function<Try<Map<String, String>>, Object> actionMapper) {
        this.path = path;
        this.charset = charset;
        this.actionMapper = actionMapper;
    }

    public Path getPath() {
        return path;
    }

    public Charset getCharset() {
        return charset;
    }

    public Function<Try<Map<String, String>>, Object> getActionMapper() {
        return actionMapper;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("path", path)
                .append("charset", charset)
                .append("actionMapper", actionMapper)
                .toString();
    }
}
