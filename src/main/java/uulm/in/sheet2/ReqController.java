package uulm.in.sheet2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ReqController implements  Runnable{

    private String respHostName;
    private int respPort;

    ZContext context;

    public ReqController(String respHostName, int respPort, ZContext context) {
        this.respHostName = respHostName;
        this.respPort = respPort;
        this.context = context;
    }

    @Override
    public void run() {
        // Client-Side REQ
        ZMQ.Socket requestSocket = context.createSocket(SocketType.PUSH);
        requestSocket.connect("tcp://" + respHostName + ":" + respPort);

        // PULL (receiver)
        ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
        receiver.bind("tcp://localhost:5558");

        while(true) {
            String result = receiver.recvStr(0);
            System.out.println("Received result from worker: " + result);
            requestSocket.send(result);
            System.out.println("Result sent to server! ");
        }
    }
}
