package rmi_ex;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


class Server implements IGetStudent {

    Map<String, Student> STUDENTS = new HashMap<>();

    protected Server() throws RemoteException {
        super();

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
    public Student getStudentFromID(String id) throws RemoteException {
        System.out.println("Query for ID " + id);
        return this.STUDENTS.get(id);
    }

    public static void main(String[] main) {
        try {
            final int PORT = 0;    // Anonymous port? Wat bout 1099?
            Server server = new Server();
            IGetStudent stub = (IGetStudent) UnicastRemoteObject     // Alternatively, extends with UnicastRemoteObject
                                .exportObject(server, PORT);         // and omit this exporting line
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
