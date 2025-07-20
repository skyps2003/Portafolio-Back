package IHC.Portafolio;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbPoolDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MariaDbConfig {
    @Bean
    DataSource dataSource() {
        MariaDbPoolDataSource dataSource = new MariaDbPoolDataSource();

        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/pron");
            dataSource.setUser("terat");
            dataSource.setPassword("luis12");
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("¡Conexión a MariaDB exitosa!");
        return dataSource;
    }
}