package thread;

/**
 * 並行処理の例
 */
public class StartUp {

	public static void main(String[] args) {
		// Threadクラスを継承したクラスのオブジェクトを生成してstart()
		ThreadA a = new ThreadA();
		ThreadB b = new ThreadB();

		//Runnableインターフェースを実装したクラスのオブジェクトを生成して、
		//それをコンストラクタ引数に入れてThreadクラスを生成、start()
		RunnerA c = new RunnerA();
		RunnerB d = new RunnerB();

		Thread t1 = new Thread(c);
		Thread t2 = new Thread(d);

		a.start();
		b.start();

		t1.start();
		t2.start();
	}
}


class ThreadA extends Thread {
	private int i = 0;

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("A:" + i + " ");
		}
	}
}

class ThreadB extends Thread {
	private int i = 0;

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("B:" + i + " ");
		}
	}
}


class RunnerA implements Runnable {
	private int i = 0;

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("runA:" + i + " ");
		}
	}
}

class RunnerB implements Runnable {
	private int i = 0;

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("runB:" + i + " ");
		}
	}
}