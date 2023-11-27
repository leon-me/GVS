package uulm.in.sheet3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

public class CashedRMIClient implements Subscriber {
    private HashMap<String, String> localkvStore;
    public SubscribeKVStore subscribeKVStore;

    private String name;
    public CashedRMIClient(String name){
        super();
        this.localkvStore = new HashMap<>();
        this.name = name;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1338);
            subscribeKVStore = (SubscribeKVStore) registry.lookup("SubscribeKVStore");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void updateEntry(String key, String value) throws RemoteException {
        if(localkvStore.containsKey(key)){
            localkvStore.replace(key, value);
        } else {
            throw new RemoteException("key not found");
        }
    }

    @Override
    public void removeEntry(String key) throws RemoteException {
        if(localkvStore.containsKey(key)){
            localkvStore.remove(key);
        } else {
            throw new RemoteException("key not found");
        }
    }

    public void write(String key, String value) throws RemoteException{
        if(localkvStore.containsKey(key)){
            localkvStore.replace(key, value);
        } else {
            localkvStore.put(key, value);
        }
        subscribeKVStore.writeRemote(key, value);
    }

    public void remove(String key) throws RemoteException{
        if(localkvStore.containsKey(key)){
            localkvStore.remove(key);
        } else {
            throw new RemoteException("Key not found");
        }
        subscribeKVStore.removeRemote(key);
    }

    public String read(String key) throws RemoteException {
        if(localkvStore.containsKey(key)){
            System.out.println(key + ", gelesen von " + name);
            return localkvStore.get(key);
        }
        try {
            //subscribeKVStore.subscribe(key, this);
            localkvStore.put(key, subscribeKVStore.readRemote(key));
            return subscribeKVStore.readRemote(key);
        } catch (RemoteException e){
            return null;
        }
    }
}
