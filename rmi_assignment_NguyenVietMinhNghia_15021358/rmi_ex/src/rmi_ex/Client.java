package rmi_ex;

import java.util.Scanner;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {

    private static IGetStudent iGetStudent;

    private static Student getInfo(String id) throws RemoteException {
        if (Client.iGetStudent == null) {
            final String BIND_NAME = "Server";
            try {
                Registry registry = LocateRegistry.getRegistry();
                Client.iGetStudent = (IGetStudent) registry.lookup(BIND_NAME);
            } catch (NotBoundException nbe) {
                System.err.println("Bind name " + BIND_NAME + " not found");
                nbe.printStackTrace();
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return Client.iGetStudent.getStudentFromID(id);
    }

    public static void main(String[] args) throws RemoteException {
        // lol
        // https://coderanch.com/wiki/678613/Don-close-Scanner-tied-System
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter student ID: ");
            String id = scanner.nextLine();

            Student student = getInfo(id);
            if (student == null) {
                System.out.println("The entered ID does not match any student");
            } else {
                System.out.println("The entered ID matches this student");
                System.out.println(student);
            }
        }
    }
}
