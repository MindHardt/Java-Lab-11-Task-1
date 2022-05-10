public class Lucky {
    static int x = 0;
    static int count = 0;

    static class LuckyThread extends Thread {

        private static final Object xLock = new Object();
        private static final Object countLock = new Object();

        @Override
        public void run() {
            int temp;
            while (true) {
                synchronized (xLock) {

                    if (x == 999_999) break;

                    x++;
                    temp = x; //Переменная temp принимает значение x чтобы безопасно предоставлять другим потокам доступ к x.
                }
                if ((temp % 10) + (temp / 10) % 10 + (temp / 100) % 10 == //Проверка "счастливого билета"
                    (temp / 1000) % 10 + (temp / 10000) % 10 + (temp / 100000) % 10) {
                    System.out.println(temp);
                    synchronized (countLock) {
                        count++;
                    }
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count); //Выводит 55251, именно столько счастливых билетов без числа 000'000
    }
}
