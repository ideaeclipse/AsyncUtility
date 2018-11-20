package ideaeclipse.AsyncUtility;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ideaeclipse.AsyncUtility.Event.createDaemonThreadFactory;

/**
 * The purpose of the class is to execute tasks on seperate threads in order to leave the main thread free
 * <p>
 * There are 2 different ways to execute tasks
 * {@link #queue(IU, String)} which just queses the inputed function on a seperate thread and retures the data on completion
 * {@link #execute(ForEachList)} this requires a list of tasks and while execute them async and return them synced up in order of execution
 *
 * @author ideaeclipse
 */
@SuppressWarnings("ALL")
public class Async {

    /**
     * This method allows for executed tasks every x milliseconds
     * @param task task you want executed
     * @param time how long between executions
     * @param <V> generic type
     */
    public static <V> void addJob(Async.IU<V> task, long time) {
        Async.blankThread(new Runnable() {
            @Override
            public void run() {
                new Job<>(task, time);
            }
        });
    }

    /**
     * @param function event that gets queued on a seperate thread
     * @param name     name that you want the thread to be called will be headed with Sync-Executor-'name'
     * @param <T>      generic type that is the expected return value from the function
     * @return return the return type specified in function
     */
    public static <T> Optional<T> queue(final IU<T> function, final String name) {

        ExecutorService service = Executors.newSingleThreadExecutor(createDaemonThreadFactory("Sync-" + name));
        Future<Optional<T>> future = service.submit(new Event<>(function, 0, Optional.empty()));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Starts a blank thead with no return
     *
     * @param runnable code to execute
     */
    public static void blankThread(final Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * Starts a blank thead with no return
     *
     * @param runnable code to execute
     */
    public static <T> void blankThread(final Async.IU<T> runnable) {
        new Event<>(runnable, 0, Optional.empty()).call();
    }

    /**
     * @param list list of {@link IU}
     * @param <T>  return value of each item in the list
     * @return list of return values
     * @see ForEachList
     */

    public interface IU<T> {
        Optional<T> update(T object);
    }
}
