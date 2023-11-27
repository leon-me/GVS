package uulm.in.sheet3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            //Verbindung zum RMI Registry, Port 1337
            Registry registry = LocateRegistry.getRegistry("localhost", 1337);

            //Suche nach dem Remote-Objekt mit dem Namen "RemoteKVStore"
            RemoteKVStore remoteKVStore = (RemoteKVStore) registry.lookup("RemoteKVStore");

            //Testen aller Funktionalitäten
            remoteKVStore.writeRemote("123", "test1");
            System.out.println("Read key 123: " + remoteKVStore.readRemote("123"));

            remoteKVStore.writeRemote("123", "test2");
            System.out.println("Updated key 123: " + remoteKVStore.readRemote("123"));

            remoteKVStore.removeRemote("123");
            System.out.println("Key deleted, expected Exception next:");
            System.out.println("After removal: " + remoteKVStore.readRemote("123"));
            System.out.println("All methods tested");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
