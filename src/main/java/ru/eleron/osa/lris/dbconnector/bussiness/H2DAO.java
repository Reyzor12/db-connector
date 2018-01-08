package ru.eleron.osa.lris.dbconnector.bussiness;

import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.dbconnector.entity.ConnectorDB;

import java.util.List;

@Component
public interface H2DAO {
    List<ConnectorDB> getList();
    ConnectorDB getConnector(String name);
    void removeConnector(String name);
    void addConnector(ConnectorDB connectorDB);
}
