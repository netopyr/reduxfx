package com.netopyr.reduxfx.timer;

import com.netopyr.reduxfx.timer.command.StartTimerCommand;
import com.netopyr.reduxfx.timer.command.StopTimerCommand;
import com.netopyr.reduxfx.updater.Command;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import rx.Observable;
import rx.Observer;

public class TimerDriver<T> {

    private final Observer<T> actionObserver;

    private Map<Object, Animation> timers = HashMap.empty();

    public TimerDriver(Observable<Command> commandObservable, Observer<T> actionObserver) {
        this.actionObserver = actionObserver;
        commandObservable
                .filter(command -> command instanceof StartTimerCommand)
                .map(command -> (StartTimerCommand) command)
                .forEach(this::startTimer);

        commandObservable
                .filter(command -> command instanceof StopTimerCommand)
                .map(command -> (StopTimerCommand) command)
                .forEach(this::stopTimer);
    }

    private void startTimer(StartTimerCommand<T> command) {
        final Timeline timeline = new Timeline(
                new KeyFrame(command.getInterval(), e -> actionObserver.onNext(command.getMapper().apply(System.nanoTime() / 1000000)))
        );
        timeline.setDelay(command.getDelay() != null? command.getDelay() : Duration.ZERO);
        timeline.setCycleCount(Animation.INDEFINITE);
        timers = timers.put(command.getKey(), timeline);
//        timeline.play();
    }

    private void stopTimer(StopTimerCommand command) {
        timers = timers.remove(command.getKey());
    }
}
