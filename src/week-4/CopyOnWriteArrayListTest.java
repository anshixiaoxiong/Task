import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyOnWriteArrayListTest {

    /**
     * CopyOnWriteArrayList用来替换同步List对象
     * 当读操作比写操作更频繁时，使用CopyOnWriteArrayList
     * 迭代器返回集合当前状态的快照
     * 支持原子操作
     * 内存占用问题: 因为CopyOnWrite的写时复制机制，所以在进行写操作的时候，
     * 内存里会同时驻扎两个对象的内存，旧的对象和新写入的对象
     * 数据一致性问题: CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。
     * 所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器。
     */
    public static void useCopyOnWriteArrayList() {
        System.out.println("=== CopyOnWriteArrayList ===");
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Random random = new Random();
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList<Integer>();

        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0) {
                // 写操作
                executor.execute(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int value = random.nextInt(50);
                    System.err.println("Write " + value);
                    copyOnWriteArrayList.add(value);

                });
            } else {
                // 读操作
                executor.execute(() -> {
                    StringBuffer buffer = new StringBuffer();
                    for (Object value : copyOnWriteArrayList) {
                        buffer.append(value + " ");
                    }
                    System.out.println("Read " + buffer.toString());
                });
            }
        }

        executor.shutdown();
    }

    public static void main(String[] args) {
        useCopyOnWriteArrayList();
    }
}
