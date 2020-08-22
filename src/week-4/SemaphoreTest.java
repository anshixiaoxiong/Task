import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new SecurityCheckThread(i, semaphore).start();
        }
    }

    public static class SecurityCheckThread extends Thread {
        private int seq;
        private Semaphore semaphore;

        public SecurityCheckThread(int seq, Semaphore semaphore) {
            this.seq = seq;
            this.semaphore = semaphore;
        }

        /**
         * 一个线程异常不影响其余线程
         */
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("NO." + seq + "乘客，正在检验中");
                if (seq % 2 == 0) {
                    Thread.sleep(1000);
                    System.out.println("NO." + seq + "乘客，身份可疑，不可出国！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                System.out.println("NO." + seq + "乘客已完成服务。");
            }
        }
    }


}
