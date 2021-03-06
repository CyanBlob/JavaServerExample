import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class ServerMultithreaded implements Runnable
{
	protected int          serverPortVal   = 8080;
	protected ServerSocket serverSocketVal = null;
	protected boolean      hasStopped    = false;
	protected Thread       movingThread = null;

	public ServerMultithreaded(int port)
	{
		this.serverPortVal = port;
	}

	public void run()
	{
		synchronized(this)
		{
			this.movingThread = Thread.currentThread();
		}

		opnSvrSocket();

		while (!hasStopped())
		{
			Socket clntSocket = null;
			try
			{
				clntSocket = this.serverSocketVal.accept();
			}
			catch (IOException e)
			{
				if (hasStopped())
				{
					System.out.println("Server has stopped") ;
					return;
				}
				throw new RuntimeException(
						"Client cannot be connected - Error", e);
			}
			new Thread(new WrkrRunnable(clntSocket, "This is a multithreaded server")).start();
		}
		System.out.println("Server has stopped") ;
	}
	private synchronized boolean hasStopped()
	{
		return this.hasStopped;
	}
	public synchronized void stop()
	{
		this.hasStopped = true;
		try
		{
			this.serverSocketVal.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException("Server can not be closed - Please check error", e);
		}
	}
	private void opnSvrSocket()
	{
		try
		{
			this.serverSocketVal = new ServerSocket(this.serverPortVal);
		} 
		catch (IOException e)
		{
			throw new RuntimeException("Not able to open port " + this.serverPortVal, e);
		}
	}
}
