
public class ControlThreadLifecycle {
	public static void main(String[] args) {
		threadStateAfterRun(); 
	}
	
//	test the state of the thread after the exiting run method.  
	public static void threadStateAfterRun(){
		Thread t = new Thread(new Runnable(){
			public void run(){
				printThreadState(); 
			}
		}); 
		
		printThreadState(); 
		t.start();
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Thread state:(Thread-0|TERMINATED)
		//This means when the a thread exit run method, the state of the thread is "TERMINATED"  
		System.out.println("Thread state:("+ t.getName()+ "|" + t.getState() + ")");
	}
	
	public static void printThreadState(){
		System.out.println("Thread state:("+ Thread.currentThread().getName()+ "|" + Thread.currentThread().getState() + ")");
	}
}
