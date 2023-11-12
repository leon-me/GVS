package uulm.in.sheet2;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.math.BigInteger;

import static uulm.in.sheet2.Fermat.fermatFactorization;


public class Worker implements Runnable {

    int pullPort = 5557;
    int pushPort = 5558;

    ZContext context;

    public Worker(ZContext context) {
        this.context = context;
    }

    @Override
    public void run() {

        ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
        receiver.connect("tcp://localhost:" + pullPort);

        ZMQ.Socket sender = context.createSocket(SocketType.PUSH);
        sender.connect("tcp://*:" + pushPort);

        while (true) {
            String task = receiver.recvStr(0);
            System.out.println("Worker starting with task: " + task);
            BigInteger[] resultingArray = fermatFactorization(new BigInteger(task));
            String result = "";
            for (int j = 0; j < resultingArray.length; j++) {
                result += resultingArray[j] + ":";
            }
            result = result.substring(0, result.length() - 1);
            sender.send(task + ":" + result, 0);
            System.out.println("Worker done with task: " + task);
        }
    }

}
