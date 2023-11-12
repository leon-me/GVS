package uulm.in.sheet2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class SubController implements Runnable {

    private String pubHostName;
    private int pubPort;
    ZContext context;

    public SubController(String pubHostName, int pubPort, ZContext context) {
        this.pubHostName = pubHostName;
        this.pubPort = pubPort;
        this.context = context;
    }

    @Override
    public void run() {
        // Client-Side SUB

        ZMQ.Socket subSocket = context.createSocket(SocketType.SUB);
        subSocket.connect("tcp://" + pubHostName + ":" + pubPort);
        subSocket.subscribe(ZMQ.SUBSCRIPTION_ALL);

        // PUSH (sender)
        ZMQ.Socket sender = context.createSocket(SocketType.PUSH);
        sender.bind("tcp://*:5557");

        while (true) {
            String subMessage = subSocket.recvStr(0);
            System.out.println("Received task from server: " + subMessage);
            sender.send(subMessage);
        }
    }
}
