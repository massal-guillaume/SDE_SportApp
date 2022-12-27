package SportRecap.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ConnectionPoolManager {

        private HikariDataSource dataSource;

        private String hostname;
        private String port;
        private String database;
        private String username;
        private String password;
        private String tablename;

        public ConnectionPoolManager() {
            init();
            setupPool();
        }

        public Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }


        private void init() {
            hostname = plugin.getConfig().getString("sql.hostname");
            port = plugin.getConfig().getString("sql.port");
            database = plugin.getConfig().getString("sql.database");
            username = plugin.getConfig().getString("sql.username");
            password = plugin.getConfig().getString("sql.password");
            tablename = plugin.getConfig().getString("sql.tablename");
        }

        public String getTablename() {
            return this.tablename;
        }

        private void setupPool() {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(
                    "jdbc:mysql://" +
                            hostname +
                            ":" +
                            port +
                            "/" +
                            database
            );
            config.setDriverClassName("com.mysql.jdbc.Driver");
            config.setUsername(username);
            config.setPassword(password);
            config.setMaxLifetime(600000L);
            config.setIdleTimeout(300000L);
            config.setLeakDetectionThreshold(300000L);
            config.setConnectionTimeout(100000L);
            dataSource = new HikariDataSource(config);
        }


        public void close(Connection conn, PreparedStatement ps, ResultSet res) {
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
            if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
            if (res != null) try { res.close(); } catch (SQLException ignored) {}
        }



        public void closePool() {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
            }
        }



    }

