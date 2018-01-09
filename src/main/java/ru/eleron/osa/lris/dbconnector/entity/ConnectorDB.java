package ru.eleron.osa.lris.dbconnector.entity;

public class ConnectorDB {

    private Integer id;
    private String name;
    private Integer typeDB;
    private String ip;
    private String port;
    private String nameDB;
    private String user;
    private String password;

    public ConnectorDB(String name, Integer typeDB, String ip, String port, String nameDB, String user, String password){

        this.name = name;
        this.typeDB = typeDB;
        this.ip = ip;
        this.port = port;
        this.nameDB = nameDB;
        this.user = user;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeDB() {
        return typeDB;
    }

    public void setTypeDB(Integer typeDB) {
        this.typeDB = typeDB;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getNameDB() {
        return nameDB;
    }

    public void setNameDB(String nameDB) {
        this.nameDB = nameDB;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return name;
    }
}
