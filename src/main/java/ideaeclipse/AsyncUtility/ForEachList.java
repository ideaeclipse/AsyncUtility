package ideaeclipse.AsyncUtility;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This is a class that is a list of functions you want to execute asynchronously, works as a list
 *
 * @param <T> Return type expected
 */
public class ForEachList<T> extends AsyncList<T> {
    /**
     * Executes each code block individually doesn't wait for one to finish to execute another
     * returns the return values in the order you added them to the list
     *
     * @return returns each return of each inputted code block
     */
    @Override
    public Optional<List<T>> execute() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<T>> future = service.submit(() -> {
            List<T> returnList = new LinkedList<>();
            List<Future<Optional<T>>> eventReturnList = new LinkedList<>();
            ExecutorService subService = Executors.newFixedThreadPool(this.size(), Event.createDaemonThreadFactory("Async"));
            for (int i = 0; i < this.size(); i++) {
                Future<Optional<T>> f = subService.submit(new Event<>(this.get(i), i, Optional.empty()));
                eventReturnList.add(f);
            }
            subService.shutdown();
            for (Future<Optional<T>> future1 : eventReturnList) {
                Optional<T> temp = future1.get();
                if (temp.isPresent()) {
                    returnList.add(temp.get());
                } else {
                    returnList.add(null);
                }
            }

            return returnList;
        });
        service.shutdown();
        try {
            return Optional.of(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}