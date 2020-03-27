package server;

import java.io.Serializable;


/**
 * Interface to exchange student information between client and server
 * Use an interface instead of a concrete class to reduce coupling
 */
interface IStudentInfo extends Serializable {

    public String getName();
    public String getId();
    public String getClassName();
    public String getPhone();
    public String getEmail();
    public String getAddress();

}
