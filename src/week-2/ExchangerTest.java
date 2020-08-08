import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
/*
————————————————————————————————————————————————————————————————————————————————
Exchanger:两个线程之间数据交换，Java并发包中的一个工具类。
通过exchange方法相互交换数据，如果第一个执行到exchange方法，会等待第二个线程
执行exchange，当两个线程都到达时，会进行数据交换。
Exchanger内部用了内部类Participant，这个内部类继承ThreadLocal,故本质就是ThreadLocal.
————————————————————————————————————————————————————————————————————————————————
 */
public class ExchangerTest {
    static class Producer extends Thread {
        private Exchanger<Integer> exchanger;
        private static int data1 = 0;

        Producer(String name, Exchanger<Integer> exchanger) {
            super("Producer-" + name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {

            try {
                data1 = 111;
                System.out.println(getName() + " 交换前:" + data1);
                data1 = exchanger.exchange(data1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + " 交换后:" + data1);

        }
    }

    static class Consumer extends Thread {
        private Exchanger<Integer> exchanger;
        private static int data2 = 0;

        Consumer(String name, Exchanger<Integer> exchanger) {
            super("Consumer-" + name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {

            try {
                TimeUnit.SECONDS.sleep(1);
                data2 = 222;
                System.out.println(getName() + " 交换前:" + data2);
                data2 = exchanger.exchange(data2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + " 交换后:" + data2);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        new Producer("", exchanger).start();
        new Consumer("", exchanger).start();
        TimeUnit.SECONDS.sleep(5);
    }
}