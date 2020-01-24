import java.net.*;
import java.util.*;
import java.io.*;

class Server
{
Map<String,Socket> connectedClient=new HashMap<>();
ServerSocket ss;
ServerSocket c1;
ServerSocket c2;
Server() throws Exception
{
ss=new ServerSocket(5005);
c1=new ServerSocket(5006);
c2=new ServerSocket(5007);
startListening();
}
public void startListening()throws Exception
{
Socket s=new Socket();
while(true)
{
s=ss.accept();

    String rand = UUID.randomUUID().toString();
    String id= rand;
connectedClient.put(id,s);
new ClientHandler(this,id,s);
}

}

public void startChat(String id1,String id2)throws Exception
{
System.out.println("Chat call hua");
Socket s1=connectedClient.get(id1);
Socket s2=connectedClient.get(id2);

new DataOutputStream(s1.getOutputStream()).writeInt(5006);
new DataOutputStream(s2.getOutputStream()).writeInt(5007);

System.out.println("port bhej diye");
s1=c1.accept();
s2=c2.accept();

new S1toS2(s1,s2);
new S2toS1(s2,s1);



}

}

class S1toS2 extends Thread
{
Socket s1;
Socket s2;
S1toS2(Socket s1,Socket s2) 
{
this.s1=s1;
this.s2=s2;
start();
}


public void run()
{
try{
DataInputStream dis=new DataInputStream(s1.getInputStream());
DataOutputStream dos=new DataOutputStream(s2.getOutputStream());
while(true)
{


dos.writeUTF(dis.readUTF());


}


}catch(Exception e)
{
e.printStackTrace();
}
}

}


class S2toS1 extends Thread
{
Socket s1;
Socket s2;
S2toS1(Socket s1,Socket s2)
{
this.s1=s1;
this.s2=s2;
start();
}


public void run()
{
try{
DataInputStream dis=new DataInputStream(s1.getInputStream());
DataOutputStream dos=new DataOutputStream(s2.getOutputStream());
while(true)
{


dos.writeUTF(dis.readUTF());


}
}catch(Exception e)
{
e.printStackTrace();
}
}

}



class ClientHandler extends Thread
{
Server parent;
Socket socket;
String id;
ClientHandler(Server parent,String id,Socket socket)
{
System.out.println("connected");
this.parent=parent;
this.id=id;
this.socket=socket;
start();
}


public void run()
{
try
{
DataInputStream dis=new DataInputStream(socket.getInputStream());
DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
dos.writeUTF(id);
// will listen to request from connected user
int choice=dis.readInt();
System.out.println(choice);
if(choice==1)
{
System.out.println("Chat call hua");
String friendsId=dis.readUTF();
parent.startChat(id,friendsId);
}

System.out.println("Chat call hone se phle");


}catch(Exception e)
{
e.printStackTrace();
}

}



}


class psp
{

public static void main(String gg[])
{
try{
Server s=new Server();
}catch(Exception e)
{
e.printStackTrace();
}
}
}