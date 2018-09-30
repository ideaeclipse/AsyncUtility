package ideaeclipse.AsyncUtility;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * The purpose of the class is to execute tasks on seperate threads in order to leave the main thread free
 * <p>
 * There are 2 different ways to execute tasks
 * {@link #queue(IU, String)} which just queses the inputed function on a seperate thread and retures the data on completion
 * {@link #execute(AsyncList)} this requires a list of tasks and while execute them async and return them synced up in order of execution
 *
 * @author ideaeclipse
 */
@SuppressWarnings("ALL")
public class Async {
    /**
     * @param function event that gets queued on a seperate thread
     * @param name     name that you want the thread to be called will be headed with Sync-Executor-'name'
     * @param <T>      generic type that is the expected return value from the function
     * @return return the return type specified in function
     */
    public static <T> T queue(final IU<T> function, final String name) {
        ExecutorService service = Executors.newSingleThreadExecutor(createDaemonThreadFactory("Sync-Executor-" + name));
        Future<T> future = service.submit(new Event<>(function, 0));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param list list of {@link IU}
     * @param <T>  return value of each item in the list
     * @return list of return values
     * @see AsyncList
     */
    public static <T> List<T> execute(final AsyncList<T> list) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<T>> future = service.submit(() -> {
            List<T> returnList = new LinkedList<>();
            List<Future<T>> eventReturnList = new LinkedList<>();
            ExecutorService s = Executors.newFixedThreadPool(list.size(), createDaemonThreadFactory("Async-Executor"));
            for (int i = 0; i < list.size(); i++) {
                Future<T> f = s.submit(new Event<>(list.get(i), i));
                eventReturnList.add(f);
            }
            for (Future<T> future1 : eventReturnList) {
                returnList.add(future1.get());
            }
            return returnList;
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This class is the way async is allowed to work because on execution of an asynclist
     * you start a new thread but while you loop through each index you need to start a sub thread
     * in order to execute each event at the "same time"
     *
     * @param <T> Return type you are expecting from the function
     */
    private static class Event<T> implements Callable<T> {
        private final IU<T> event;
        private final int threadNumber;

        Event(final IU<T> event, final int threadNumber) {
            this.threadNumber = threadNumber + 1;
            this.event = event;
        }

        @Override
        public T call() {
            Thread.currentThread().setName(Thread.currentThread().getName() + "-" + threadNumber);
            return event.update();
        }
    }

    public interface IU<T> {
        T update();
    }

    /**
     * This is a class that is a list of functions you want to execute asynchronously, works as a list
     *
     * @param <T> Return type expected
     */
    public static class AsyncList<T> {
        private final List<IU<T>> list;

        public AsyncList() {
            list = new LinkedList<>();
        }

        public IU<T> get(final int index) {
            return list.get(index);
        }

        public AsyncList<T> add(final IU<T> event) {
            list.add(event);
            return this;
        }

        Integer size() {
            return list.size();
        }

    }

    /**
     * @param threadName name of the thread
     * @return returns a threadfactory with a name set
     */
    private static ThreadFactory createDaemonThreadFactory(String threadName) {
        return (runnable) -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            if (threadName != null)
                thread.setName(threadName);
            thread.setDaemon(true);
            return thread;
        };
    }

}
