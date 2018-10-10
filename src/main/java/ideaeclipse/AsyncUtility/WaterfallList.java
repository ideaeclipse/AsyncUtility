package ideaeclipse.AsyncUtility;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Executes each index of the parent list one by one and passes the return value to the following index
 *
 * @param <T>
 * @author Ideaeclipse
 */
public class WaterfallList<T> extends AsyncList<T> {
    @Override
    public Optional<List<Optional<T>>> execute() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Optional<T>>> future = service.submit(() -> {
            ExecutorService subService = Executors.newSingleThreadExecutor();
            Optional<T> previous = Optional.empty();
            Future<Optional<T>> tempFuture;
            for (int i = 0; i < this.size(); i++) {
                if (i == 0) {
                    tempFuture = subService.submit(new Event<>(this.get(i), i, Optional.empty()));
                } else if (i == (this.size() - 1)) {
                    tempFuture = subService.submit(new Event<>(this.get(i), i, previous));
                    subService.shutdown();
                    return Collections.singletonList(tempFuture.get());
                } else {
                    tempFuture = subService.submit(new Event<>(this.get(i), i, previous));
                }
                previous = tempFuture.get();
            }
            return Collections.singletonList(null);
        });
        service.shutdown();
        try {
            return Optional.of(future.get());
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }
}
