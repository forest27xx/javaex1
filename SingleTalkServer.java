import java.net.*;
import java.io.*;

public class SingleTalkServer
{
    public static void main(String[] args) throws IOException
	{

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();  //程序将在此等候客户端的连接
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
	System.out.println("Accept OK!");

        //打开输入/输出流
	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);  //auto flush
        BufferedReader in = new BufferedReader(
				new InputStreamReader(
				clientSocket.getInputStream()));

	//从标准输入流（键盘）中获取信息
        BufferedReader sin = new BufferedReader( new InputStreamReader( System.in ) );

	boolean  sinbye = false;
	boolean  inbye = false;
	String sinputLine, inputLine;
        
        inputLine = in.readLine();
	System.out.println( "from Client: " + inputLine );

	System.out.print("Server input:");
	sinputLine = sin.readLine();

        while( true )
	{
		if( sinbye == false )
		{
			out.println(sinputLine);
			out.flush();
			//System.out.println("Server: " + sinputLine);

			if (sinputLine.equals("Bye."))
				sinbye = true;
		}

		if( inbye == false )
		{
			inputLine = in.readLine();
			System.out.println( "from Client: " + inputLine );

			if (inputLine.equals("Bye."))
				inbye = true;
		}

		if( sinbye == false )
		{
			System.out.print("Server input:");
			sinputLine = sin.readLine();
		}

		if( sinbye == true && inbye == true )
			break;
        }

        out.close();
        in.close();
	sin.close();

        clientSocket.close();
        serverSocket.close();
    }
}
