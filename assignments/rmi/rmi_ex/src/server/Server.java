package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


class Server implements IGetStudentInfo {

    private Map<String, Student> STUDENTS = new HashMap<>();

    private Server() throws RemoteException {
        // Init student list
        int id = 110;
        for (int i = 0; i < 10; ++i) {
            id += i;
            String sid = String.valueOf(id);
            Student student = new Student("nvmnghia" + sid, sid, "class" + sid, "0979 " + sid,
                                          "nvmnghia" + sid + "@gmail.com", "Hai Duong " + sid);
            STUDENTS.put(sid, student);
        }
    }

    @Override
    public IStudentInfo getStudentFromID(String id) throws RemoteException {
        System.out.println("Query for ID " + id);
        return this.STUDENTS.get(id);
    }

    public static void main(String[] main) {
        try {
            final int PORT = 0;    // Anonymous port? Wat bout 1099?
            IGetStudentInfo server = new Server();
            IGetStudentInfo stub = (IGetStudentInfo) UnicastRemoteObject     // Alternatively, extends with UnicastRemoteObject
                                   .exportObject(server, PORT);              // and omit this exporting line
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
