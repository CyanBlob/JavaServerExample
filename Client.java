import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client{
	public static String response;
	public static Socket socket;
	public static String serverIP = "127.0.0.1";
	public static int serverPort = 9191;

	public static void main(String [] args) throws IOException{
		ServerMultithreaded newServer = new ServerMultithreaded(serverPort);
		new Thread(newServer).start();

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				newServer.stop();
				System.out.println("Stopping newServer");
			}
		});

		while (true)
		{
			try
			{
				socket = new Socket(serverIP, serverPort);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			try
			{
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while ((response = input.readLine()) != null)
				{
					System.out.println(response);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			socket.close();
		}
	}
}
