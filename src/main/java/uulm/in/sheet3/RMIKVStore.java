package uulm.in.sheet3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RMIKVStore extends UnicastRemoteObject implements RemoteKVStore{
    private HashMap<String, String> kvStore;

    public RMIKVStore() throws RemoteException {
        super();
        this.kvStore = new HashMap<>();
        try {
            Registry registry = LocateRegistry.createRegistry(1337);
            registry.rebind("RemoteKVStore", this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readRemote(String key) throws RemoteException {
        if (kvStore.containsKey(key)) {
            return kvStore.get(key);
        } else {
            throw new RemoteException("Key not found");
        }
    }

    @Override
    public void writeRemote(String key, String value) throws RemoteException {
        if(kvStore.containsKey(key)){
            kvStore.replace(key, value);
        } else {
            kvStore.put(key, value);
        }
    }

    @Override
    public void removeRemote(String key) throws RemoteException {
        if(kvStore.containsKey(key)){
            kvStore.remove(key);
        } else {
            throw new RemoteException("Key not found");
        }
    }

    public static void main(String[] args) throws RemoteException {
        try {
            RMIKVStore server = new RMIKVStore();
        } catch (RemoteException e){
            throw new RemoteException("Unexpectd Key");
        }
    }
}
