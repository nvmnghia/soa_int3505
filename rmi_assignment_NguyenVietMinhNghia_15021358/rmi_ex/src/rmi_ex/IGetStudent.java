package rmi_ex;

import java.rmi.Remote;
import java.rmi.RemoteException;


interface IGetStudent extends Remote {
    public Student getStudentFromID(String id) throws RemoteException;
}
