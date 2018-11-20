import ideaeclipse.AsyncUtility.Async;
import ideaeclipse.AsyncUtility.AsyncList;
import ideaeclipse.AsyncUtility.ForEachList;

import java.util.Optional;

public class AsyncTest {
    private AsyncTest() {
        Async.addJob(object -> {
            System.out.println("Ran");
            return Optional.empty();
        }, 2000);

        AsyncList<Integer> list = new ForEachList<>();
        list.add(o -> {
            System.out.println("Starting 1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 1");
            return Optional.empty();
        });
        list.add(o -> {
            System.out.println("Starting 2");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 2");
            return Optional.of(2);
        });
        list.add(o -> {
            System.out.println("Starting 3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 3");
            return Optional.of(3);
        });
        System.out.println(list.execute());

    }

    public static void main(String[] args) {
        new AsyncTest();
    }
}
