import java.net.*;
import java.io.*;

public class MultiTalkServer
{
    public static void main(String[] args) throws IOException
	{
        ServerSocket serverSocket = null;
        boolean listening = true;
	int clientNumber = 0;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e)
	{
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (listening)
	{
		Socket socket;
		socket = serverSocket.accept();  //程序将在此等候客户端的连接
		clientNumber++;
		new MultiTalkServerThread(socket, clientNumber).start();
	}
        serverSocket.close();
    }
}

class MultiTalkServerThread extends Thread
{
	private Socket socket = null;
	private int clientNumber;

	public MultiTalkServerThread(Socket socket, int clientNumber)
	{
		super("MultiTalkServerThread");
		this.socket = socket;
		this.clientNumber = clientNumber;
		System.out.println("Accept Client" + clientNumber);
	}

	public void run()
	{

		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  //auto flush
			BufferedReader in = new BufferedReader(
						new InputStreamReader(
						socket.getInputStream()));

			BufferedReader sin = new BufferedReader( new InputStreamReader( System.in ) );

			String sinputLine, inputLine;
			boolean sinbye = false;
			boolean inbye = false;
			        
			inputLine = in.readLine();
			System.out.println( "from Client" + clientNumber + ": " + inputLine );

			while( true )
			{
				if(inputLine.equals("Bye."))
				{
					inbye = true;
					sinbye = true;
					sinputLine = "Bye.";
					out.println(sinputLine);
					out.flush();
					System.out.println("Server: " + "Reply to client " + clientNumber +" : " + sinputLine);
				}
				else
				{
					sinputLine = "Reply to client " + clientNumber +" : " + inputLine;
					out.println(sinputLine);
					out.flush();
					System.out.println("Server: " + sinputLine);
				}

				if( sinbye == true && inbye == true )
					break;

				if( inbye == false )
				{
					inputLine = in.readLine();
					System.out.println( "form Client " + clientNumber + ": " + inputLine );
				}
			}

			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
