import java.io.*;
import java.net.*;

public class SingleTalkClient
{
    public static void main(String[] args) throws IOException
	{

        Socket client = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            client = new Socket("127.0.0.1", 4444);
            out = new PrintWriter(client.getOutputStream(), true); //auto flush
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: 127.0.0.1.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 127.0.0.1.");
            System.exit(1);
        }

        //从标准输入流（键盘）中获取信息
	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        
	String fromServer, fromUser;
	boolean sbye = false;
	boolean ubye = false;

	System.out.print("Client input:");
	fromUser = stdIn.readLine();
        while( true )  
	{
		if( ubye == false )
		{
			out.println(fromUser);
			out.flush();
			//System.out.println("Client: " + fromUser);
			if (fromUser.equals("Bye."))
				ubye = true;
		}

		if( sbye == false )
		{
			fromServer = in.readLine();
			System.out.println("from Server: " + fromServer);
			if (fromServer.equals("Bye."))
				sbye = true;
		}

		if( ubye == false )
		{
			System.out.print("Client input:");
			fromUser = stdIn.readLine();
		}

		if( ubye == true && sbye == true )
			break;
        }

        out.close();
        in.close();
        stdIn.close();
        client.close();
    }
}
