public class Task04 {
    /*
    volatile关键字：防指指令重排，解决多线程共享变量的可见性问题
     */
     static class SingleInstance {
         //volatile保证instance在所有线程同步中
        private static volatile SingleInstance instance = null;
        private SingleInstance() {
        }
        public static SingleInstance getInstance() {
            if (instance == null) {
                synchronized (SingleInstance.class) {
                    if (instance == null) {
                        instance = new SingleInstance();
                    }
                }
            }
            return instance;
        }
    }
}
