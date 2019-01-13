package ideaeclipse.AsyncUtility;

import ideaeclipse.AsyncUtility.Async;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * This class is the way async is allowed to work because on execution of an asynclist
 * you start a new thread but while you loop through each index you need to start a sub thread
 * in order to execute each event at the "same time"
 *
 * @param <T> Return type you are expecting from the function
 */
class Event<T> implements Callable<Optional<T>> {
    private final Async.IU<T> event;
    private final int threadNumber;
    private final T object;

    Event(final Async.IU<T> event, final int threadNumber, final Optional<T> object) {
        this.threadNumber = threadNumber + 1;
        this.event = event;
        this.object = object.orElse(null);

    }

    /**
     * @return returns the return variable of the inputted code block {@link Async.IU}
     */
    @Override
    public Optional<T> call() {
        if (!Thread.currentThread().getName().contains("-"))
            Thread.currentThread().setName(Thread.currentThread().getName() + "-" + threadNumber);
        return event.update(this.object);
    }

    /**
     * @param threadName name of the thread
     * @return returns a threadfactory with a name set
     */
    static ThreadFactory createDaemonThreadFactory(final String threadName) {
        return (runnable) -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            if (threadName != null)
                thread.setName(threadName);
            thread.setDaemon(true);
            return thread;
        };
    }
}
