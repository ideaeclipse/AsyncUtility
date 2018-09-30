# AsyncUtility
* This utility allows you to added code blocks into a list and execute them asynchronously on multiple different threads
* You can also synchronously execute code to free up the main thread
## Methods
* There are two main methods
    * Async.execute()
        * For asynchronous execution
    * Async.queue()
        * For synchronous execution
## Examples
* Synchronous execution
```java
Integer a = Async.queue(() -> {
        System.out.println("Synchronous execution");
        return 1;
    }, "TestThread");
System.out.println(a);
```
* This method returns whatever the code block you provided returns
```java
/*
* Return:
* Synchronous execution
* 1
*/
```
* Asynchronous execution
```java
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
        System.out.println(Async.execute(list));
    }
```
* This execute method executes which ever method is next in the set and starts executing it
* As you can see from the code The method with the shortest thread sleep time ends first and the longest ends last
* Even though the code blocks get execute asynchronously the return values get returned in order you added them to the list in 
```java
/*
* Result:
* Starting 1
* Starting 3
* Starting 2
* Ending 2
* Ending 1
* Ending 3
* [1, 2, 3]
*/
```
#### Projects
* If you want to see projects that incorporate this utility check out my other projects DiscordAPI, reflectionListener, and jsonUtilities