package uulm.in.sheet3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubRMIKVStore extends UnicastRemoteObject implements SubscribeKVStore{
    private HashMap<String, String> kvStore;
    private Map<String, List<Subscriber>> subscriberMap;

    public SubRMIKVStore() throws RemoteException{
        super();
        this.kvStore = new HashMap<>();
        this.subscriberMap = new HashMap<>();
        try {
            Registry registry = LocateRegistry.createRegistry(1338);
            registry.rebind("SubscribeKVStore", this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readRemote(String key) throws RemoteException {
        if (kvStore.containsKey(key)) {
            System.out.println(key + ", gelesen vom Server");
            return kvStore.get(key);
        } else {
            throw new RemoteException("Key not found");
        }
    }

    @Override
    public void writeRemote(String key, String value) throws RemoteException {
        if(kvStore.containsKey(key)){
            kvStore.replace(key, value);
            if (subscriberMap.get(key) != null) {
                for (Subscriber sub : subscriberMap.get(key)) {
                    sub.updateEntry(key, value);
                }
            }
        } else {
            kvStore.put(key, value);
        }
    }

    @Override
    public void removeRemote(String key) throws RemoteException {
        if(kvStore.containsKey(key)){
            kvStore.remove(key);
            for(Subscriber sub: subscriberMap.get(key)){
                sub.removeEntry(key);
            }
        } else {
            throw new RemoteException("Key not found");
        }
    }

    @Override
    public void subscribe(String key, Subscriber sub) throws RemoteException {
        if (kvStore.containsKey(key)) {
            subscriberMap.computeIfAbsent(key, k -> new ArrayList<>()).add(sub);
        } else {
            throw new RemoteException("Key not found");
        }
    }

    @Override
    public void unsubscribe(String key, Subscriber sub) throws RemoteException{
        if (kvStore.containsKey(key)) {
            List<Subscriber> subscriberList = subscriberMap.get(key);
            if (subscriberList != null) {
                subscriberList.remove(sub);
            }
        } else {
            throw new RemoteException("Key not found");
        }
    }

    public static void main(String[] args) throws RemoteException {
        try {
            SubRMIKVStore server = new SubRMIKVStore();
        } catch (RemoteException e){
            throw new RemoteException("Unexpectd Key");
        }
    }
}
