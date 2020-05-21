package server;


class Student implements IStudentInfo {

    private static final long serialVersionUID = 1L;

    private String name;
    private String id;
    private String className;
    private String phone;
    private String email;
    private String address;


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
    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

}
