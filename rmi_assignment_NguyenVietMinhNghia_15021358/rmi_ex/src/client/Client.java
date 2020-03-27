package client;

import java.util.Scanner;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {

    private static IGetStudentInfo iGetStudent;

    private static IStudentInfo getInfo(String id) throws RemoteException {
        if (Client.iGetStudent == null) {
            final String BIND_NAME = "Server";
            try {
                Registry registry = LocateRegistry.getRegistry();
                Client.iGetStudent = (IGetStudentInfo) registry.lookup(BIND_NAME);
            } catch (NotBoundException nbe) {
                System.err.println("Bind name " + BIND_NAME + " not found");
                nbe.printStackTrace();
                System.exit(1);
            }
        }

        return Client.iGetStudent.getStudentFromID(id);
    }

    private static String displayInfo(IStudentInfo info) {
        return info == null ?
            null :
            new StringBuilder()
                .append("\n\tID:    ").append(info.getId())
                .append("\n\tName:  ").append(info.getName())
                .append("\n\tClass: ").append(info.getClassName())
                .append("\n\tPhone: ").append(info.getPhone())
                .append("\n\tEmail: ").append(info.getEmail())
                .append("\n\tAddr:  ").append(info.getAddress())
                .toString();
    }

    public static void main(String[] args) throws RemoteException {
        // lol
        // https://coderanch.com/wiki/678613/Don-close-Scanner-tied-System
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter student ID: ");
            String id = scanner.nextLine();

            IStudentInfo info = getInfo(id);
            String info_str = displayInfo(info);

            if (info_str == null) {
                System.out.println("The entered ID does not match any student");
            } else {
                System.out.println("The entered ID matches this student:");
                System.out.println(info_str);
            }
        }
    }

}
