package rmi_ex;

import java.io.Serializable;


public class Student implements Serializable {
    public String name;
    public String id;
    public String className;
    public String phone;
    public String email;
    public String address;


    public Student(String name, String id, String className,
                   String phone, String email, String address) {
        this.name = name;
        this.id = id;
        this.className = className;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        b.append("ID:    ").append(this.id).append('\n');
        b.append("Name:  ").append(this.name).append('\n');
        b.append("Class: ").append(this.className).append('\n');
        b.append("Phone: ").append(this.phone).append('\n');
        b.append("Email: ").append(this.email).append('\n');
        b.append("Addr:  ").append(this.address).append('\n');

        return b.toString();
    }
}
