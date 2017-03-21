import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

public class WrkrRunnable implements Runnable{
	protected Socket clntSocket = null;
	protected String txtFrmSrvr = null;
	private long startTime;
	private long endTime;

	public WrkrRunnable(Socket clntSocket, String txtFrmSrvr)
	{
		startTime = System.currentTimeMillis();
		this.clntSocket = clntSocket;
		this.txtFrmSrvr   = txtFrmSrvr;
	}
	public void run()
	{
		try
        {
			InputStream inputstrm  = clntSocket.getInputStream();
			OutputStream outputstrm = clntSocket.getOutputStream();

			endTime = System.currentTimeMillis();

			outputstrm.write(("OK\n\nWrkrRunnable: " + this.txtFrmSrvr + " - " + (endTime - startTime)).getBytes());
			outputstrm.close();
			inputstrm.close();
			System.out.println("Your request has processed in time : " + (endTime - startTime));
		}
		catch (IOException e)
        {
			e.printStackTrace();
		}
	}
}
