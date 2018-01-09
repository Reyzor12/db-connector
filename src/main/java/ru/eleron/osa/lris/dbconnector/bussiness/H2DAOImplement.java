package ru.eleron.osa.lris.dbconnector.bussiness;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.dbconnector.StartApp;
import ru.eleron.osa.lris.dbconnector.entity.ConnectorDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class H2DAOImplement implements H2DAO {
    @Override
    public List<ConnectorDB> getList() {

        List<ConnectorDB> list = new ArrayList<>();
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = StartApp.connection.createStatement();
            rs = stm.executeQuery("select * from CONNECTIONS");
            while(rs.next()){
                ConnectorDB c = new ConnectorDB(
                        rs.getString("NAME"),
                        rs.getInt("TYPE"),
                        rs.getString("IP"),
                        rs.getString("PORT"),
                        rs.getString("NAMEDB"),
                        rs.getString("USER"),
                        rs.getString("PASSWORD"));
                c.setId(rs.getInt("ID"));
                list.add(c);
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
        PreparedStatement stm = null;
        try{
            stm = StartApp.connection.prepareStatement("DELETE FROM CONNECTIONS WHERE NAME = ?");
            stm.setString(1,name);
            stm.executeUpdate();
            stm.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
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

    @Override
    public void updateConnector(ConnectorDB connectorDB) {
        if(connectorDB == null) return;
        PreparedStatement ps = null;
        try{
            ps = StartApp.connection.prepareStatement(
                    "UPDATE CONNECTIONS SET (NAME,TYPE,IP,PORT,NAMEDB,USER,PASSWORD) = (?,?,?,?,?,?,?) WHERE ID = ?"
            );
            ps.setString(1,connectorDB.getName());
            ps.setInt(2,connectorDB.getTypeDB());
            ps.setString(3,connectorDB.getIp());
            ps.setString(4,connectorDB.getPort());
            ps.setString(5,connectorDB.getNameDB());
            ps.setString(6,connectorDB.getUser());
            ps.setString(7,connectorDB.getPassword());
            ps.setInt(8,connectorDB.getId());
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
