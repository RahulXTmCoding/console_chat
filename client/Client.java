
import java.util.Scanner;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
public class Client
{
Socket soc;
String id;
Receiver r;
Sender s;
public Client(String server,int port)
{
try{
soc=new Socket(server,port);
DataInputStream dis=new DataInputStream(soc.getInputStream());
DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
id=dis.readUTF();
Scanner sc=new Scanner(System.in);
System.out.println("your id:"+id);
System.out.println("chat with friend:(enter your choice)");
System.out.println("1.Enter friend id:");
System.out.println("2.Share your id with friend:");
int choice=sc.nextInt();
sc.nextLine();
dos.writeInt(choice);
if(choice==1)
{
System.out.println("c-1");
dos.writeUTF(sc.nextLine());
}
System.out.println("post ke phle");
int port1=dis.readInt();
System.out.println("use baad");
Socket s1=new Socket(server,port1);
System.out.println("connected");
start(s1);
}catch(Exception e)
{
e.printStackTrace();
}
}

public void start(Socket s1)
{
r=new Receiver(s1);
s=new Sender(s1);

}

public void terminate()
{
r.terminate();
s.terminate();
try{
soc.close();
}catch(Exception e)
{}
}

}

class Receiver extends Thread
{
Socket socket;
private boolean exit=false;
Receiver(Socket c)
{

socket=c;
start();

}
public void terminate()
{
this.exit=true;
}
public void run()
{
try{
DataInputStream dis=new DataInputStream(socket.getInputStream());
while(!exit)
{
String receivedMessage=dis.readUTF();
if(receivedMessage.equals("")||receivedMessage==null) continue;
System.out.println("Received:-"+receivedMessage);
}
}
catch(Exception e)
{
terminate();
}
}

}

class Sender extends Thread
{
Socket socket;

private boolean exit=false;
Sender(Socket c)
{
socket=c;
start();

}
public void run()
{try
{
DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
while(!exit)
{
Scanner input=new Scanner(System.in);
String message=input.nextLine();


dos.writeUTF(message);

}
}
catch(Exception e)
{
terminate();
}
}
public void terminate()
{
this.exit=true;
}

}



class psp
{
public static void main(String gg[])
{
Client c=new Client(gg[0],Integer.parseInt(gg[1]));

}

}