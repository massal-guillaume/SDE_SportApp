package SportRecap.DAO;

import SportRecap.security.JWTUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ConnectionPoolManager {

        private HikariDataSource dataSource;
        private String hostname;
        private String username;
        private String password;


        public ConnectionPoolManager() {
            init();
            setupPool();
        }

        public Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }


        private void init() {
            hostname = JWTUtil.JDBC_URL;
            username = JWTUtil.JDBC_USERNAME;
            password = JWTUtil.JDBC_PASSWORD;
        }

        private void setupPool() {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(this.hostname);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setUsername(username);
            config.setPassword(password);
            config.setMaxLifetime(600000L);
            config.setIdleTimeout(300000L);
            config.setLeakDetectionThreshold(180000);
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

