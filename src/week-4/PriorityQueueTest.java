import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {
    public static void main(String[] args) {
//        分数升序，名字升序
        PriorityQueue<Student> priorityQueue = new PriorityQueue<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1.score == o2.score) {
                    return (o1.name).compareTo(o2.name);
                }
                return o1.score - o2.score;
            }
        });
        priorityQueue.offer(new Student("zhansan", 95));
        priorityQueue.offer(new Student("zhans", 95));
        priorityQueue.offer(new Student("bisi", 95));
        priorityQueue.offer(new Student("diaochan", 95));
        priorityQueue.offer(new Student("aubu", 95));
        priorityQueue.offer(new Student("caocao", 95));

//按排好的顺序输出
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }


//为什么转成数组输出并不是按照顺序?
        Object[] stus = priorityQueue.toArray();
        for (Object o : stus) {
            System.out.println(o.toString());
        }
//        System.out.println("*************");
//        int size = priorityQueue.size();
//        System.out.println(size);
//        for (int i=0;i<size;i++)
//        System.out.println(priorityQueue.poll());
//        System.out.println(priorityQueue.peek());//只检测对头元素
//        System.out.println(priorityQueue.element());
    }

    static class Student {
        private String name;
        private int score;

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }
    }
}
