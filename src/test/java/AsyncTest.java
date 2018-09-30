import ideaeclipse.AsyncUtility.Async;

public class AsyncTest {
    private AsyncTest() {
        Async.AsyncList<Integer> list = new Async.AsyncList<>();
        list.add(() -> {
            System.out.println("Starting 1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 1");
            return 1;
        });
        list.add(() -> {
            System.out.println("Starting 2");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 2");
            return 2;
        });
        list.add(() -> {
            System.out.println("Starting 3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ending 3");
            return 3;
        });
        //should execute 2,1,3 and return 1,2,3
        /*
         * Starting 1
         * Starting 3
         * Starting 2
         * Ending 2
         * Ending 1
         * Ending 3
         * [1, 2, 3]
         */
        System.out.println(Async.execute(list));
    }

    public static void main(String[] args) {
        new AsyncTest();
    }
}
