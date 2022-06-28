package io.davidarchanjo.config;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DebeziumConnectorConfig {

    @Bean
    public io.debezium.config.Configuration customerConnector(Environment env) throws IOException {
        var offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        var dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");
        return io.debezium.config.Configuration.create()
            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-name
            .with("name", "customer_mysql_connector")

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-connector-class
            .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")

            // https://debezium.io/documentation/reference/stable/development/engine.html#:~:text=the%20MySQL%20connector.-,offset.storage,-%3C%E2%80%A6%E2%80%8B%3E.FileOffsetBackingStore
            .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")

            // https://debezium.io/documentation/reference/stable/development/engine.html#:~:text=implement%20%3C%E2%80%A6%E2%80%8B%3E.OffsetBackingStore%20interface.-,offset.storage.file.filename,-%22%22
            .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())

            // https://debezium.io/documentation/reference/stable/development/engine.html#:~:text=upon%20time%20intervals.-,offset.flush.interval.ms,-60000
            .with("offset.flush.interval.ms", "60000")

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-database-hostname
            .with("database.hostname", env.getProperty("customer.datasource.host"))

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-database-port
            .with("database.port", env.getProperty("customer.datasource.port")) //defaults to 3306

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-database-user
            .with("database.user", env.getProperty("customer.datasource.username"))

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-database-password
            .with("database.password", env.getProperty("customer.datasource.password"))

            // https://debezium.io/documentation/reference/stable/connectors/sqlserver.html#sqlserver-property-database-dbname
            .with("database.dbname", env.getProperty("customer.datasource.database"))

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-database-include-list
            .with("database.include.list", env.getProperty("customer.datasource.database"))

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-include-schema-changes
            .with("include.schema.changes", "true")

            // allowPublicKeyRetrieval=true allows the client to automatically request the public key from the mysql server
            // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-connp-props-security.html#:~:text=5.1.31-,allowPublicKeyRetrieval,-Allows%20special%20handshake
            .with("database.allowPublicKeyRetrieval", "true")

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-database-server-id
            .with("database.server.id", "10181")

            // https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-property-database-server-name
            .with("database.server.name", "customer-mysql-db-server")

            // https://debezium.io/documentation/reference/stable/operations/debezium-server.html#debezium-source-database-history-class
            .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")

            // https://debezium.io/documentation/reference/stable/operations/debezium-server.html#debezium-source-database-history-file-filename
            .with("database.history.file.filename", dbHistoryTempFile.getAbsolutePath())
            .build();
    }
}
