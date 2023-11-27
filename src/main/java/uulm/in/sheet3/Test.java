package uulm.in.sheet3;

import java.rmi.RemoteException;

public class Test {
    public static void main(String [] args){
        try {
            //Starten des Servers und start von 2 Clients
            SubRMIKVStore server = new SubRMIKVStore();
            CashedRMIClient client1 = new CashedRMIClient("client1");
            CashedRMIClient client2 = new CashedRMIClient("client2");

            //Testen aller Funktionalitäten
            client1.write("key1", "value1");
            System.out.println("key1 mit value1 auf client1 erstellt");
            System.out.println(client1.read("key1"));
            System.out.println(client2.read("key1"));
            System.out.println(client2.read("key1"));

            System.out.println("value1 zu value2 auf client1 geändert");
            client1.write("key1", "value2");
            System.out.println(client2.read("key1"));

            System.out.println("value1 zu value3 auf client1 geändert, client2 subscribed jetzt aber key1");
            server.subscribe("key1", client2);
            client1.write("key1", "value3");
            System.out.println(client2.read("key1"));

            System.out.println("client2 unsubscribed key1, key1 wird von client1 deleted");
            server.unsubscribe("key1", client2);
            client1.remove("key1");
            System.out.println(client2.read("key1"));
            System.out.println(client1.read("key1"));


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
