import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FutureTest {
	//ExecutorServer add support for Future. Executor cannot support Future.
	private static ExecutorService executor = Executors.newFixedThreadPool(3);
	private static MyTaskImp myTask = new MyTaskImp(); 
	
	public static void main(String[] args) {
		//any task of you own can be warped here in the call function of Callable interface. 
		//maybe this task will take very long time, you can just hold the returned future object
		//and you can do anything you want about this future task. 
		Future<String> future = executor.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return myTask.doSomething("something");
			}
		}); 
		
		//now the future task has been submitted, I want to do something else. 
		myTask.doOtherthing("other thing"); 
		
		//now, it's time I want to check whether the future task has finished and I want to 
		//get the result now.
		String result = null; 
		try {  
			result = future.get();
		} catch (InterruptedException e) {
			//the task may be interrupted, so this exception makes sense. 
			e.printStackTrace();
		} catch (ExecutionException e) {
			// exception may happens when the future task carrying out, so this exception makes sense. 
			e.printStackTrace();
		} 
		
		System.out.println(result);
		
		//NOTE: never forget to shutdown the executorservice, or the main thread will
		//never exit. 
		executor.shutdown();
		
		//just for test
	}
}

//whatever task you want to perform
interface MyTask{
	public String doSomething(String input);
	public String doOtherthing(String input);
}

class MyTaskImp implements MyTask{

	@Override
	public String doSomething(String input) {
		//it takes 5 seconds to finish this task
		System.out.println("doing something now......");
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "doSomething" + input; 
	}

	@Override
	public String doOtherthing(String input) {
		System.out.println("doing otherthing now......");
		try {
			
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "doOtherthing" + input; 
	}
	
}