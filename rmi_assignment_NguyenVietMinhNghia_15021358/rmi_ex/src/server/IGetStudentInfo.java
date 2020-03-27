package server;

import java.rmi.Remote;
import java.rmi.RemoteException;


interface IGetStudentInfo extends Remote {
    public IStudentInfo getStudentFromID(String id) throws RemoteException;
}
