package uulm.in.sheet2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import zmq.socket.reqrep.Req;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) {
        // Task 1
        // requestClient("gvs.lxd-vs.uni-ulm.de", 27347);

        // Task 2
        factorialChallenge("gvs.lxd-vs.uni-ulm.de", 27378, "gvs.lxd-vs.uni-ulm.de", 27379, 2);
    }

    static void factorialChallenge(String pubHostName, int pubPort, String respHostName, int respPort, int noOfWorkers) {

        ExecutorService controllerService = Executors.newFixedThreadPool(2);
        ExecutorService workerService = Executors.newFixedThreadPool(noOfWorkers);

        ZContext context = new ZContext();
        SubController subC = new SubController(pubHostName, pubPort, context);
        ReqController reqC = new ReqController(respHostName, respPort, context);

        controllerService.execute(subC);
        controllerService.execute(reqC);

        for (int i = 0; i < noOfWorkers; i++) {
            Worker w = new Worker(context);
            workerService.execute(w);
        }
    }


    static void requestClient(String hostName, int port) {
        // Client-Side
        ZContext context = new ZContext();
        ZMQ.Socket requestSocket = context.createSocket(SocketType.REQ);
        requestSocket.connect("tcp://" + hostName + ":" + port);

        // Send a request
        String requestMessage = "Hello, Server!";
        requestSocket.send(requestMessage, 0);

        // Receive the response
        String response = requestSocket.recvStr(0);

        System.out.println("Received response: " + response);
    }
}