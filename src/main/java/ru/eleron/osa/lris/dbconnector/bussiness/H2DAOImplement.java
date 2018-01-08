package ru.eleron.osa.lris.dbconnector.bussiness;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.dbconnector.StartApp;
import ru.eleron.osa.lris.dbconnector.entity.ConnectorDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class H2DAOImplement implements H2DAO {
    @Override
    public List<ConnectorDB> getList() {

        List<ConnectorDB> list = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = StartApp.connection.createStatement();
            rs = stm.executeQuery("select * from CONNECTIONS");
            while(rs.next()){
                list.add(new ConnectorDB(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("TYPE"),
                        rs.getString("IP"),
                        rs.getString("PORT"),
                        rs.getString("NAMEDB"),
                        rs.getString("USER"),
                        rs.getString("PASSWORD")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ConnectorDB getConnector(String name) {
        return null;
    }

    @Override
    public void removeConnector(String name) {

    }

    @Override
    public void addConnector(ConnectorDB connectorDB) {
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = StartApp.connection.createStatement();
            stm.execute("insert into CONNECTIONS(NAME,TYPE,IP,PORT,NAMEDB,USER,PASSWORD) values('"+
                connectorDB.getName() +
                "','" +
                connectorDB.getTypeDB() +
                "','" +
                connectorDB.getIp() +
                "','" +
                connectorDB.getPort() +
                "','" +
                connectorDB.getNameDB() +
                "','" +
                connectorDB.getUser() +
                "','" +
                connectorDB.getPassword() +
            "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
