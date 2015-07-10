package exercises.ex01;

public class Task implements Runnable {

    private int num;

    public Task(int num) {
        this.num = num;
    }

    public void run() {
        System.out.println("Hello World, I am thread "+Thread.currentThread().getId()+
            " running task "+num);
        num++;
    }

}
