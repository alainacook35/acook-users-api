package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

public class V1__create_users_table extends BaseJavaMigration {

    private static final Logger log = LoggerFactory.getLogger(V1__create_users_table.class);

    @Override
    public void migrate(Context context) throws Exception {
        String sql = "CREATE TABLE project_user (" +
                "id BIGINT AUTO_INCREMENT NOT NULL," +
                "first_name VARCHAR(255)," +
                "last_name VARCHAR(255)," +
                "email VARCHAR(255)," +
                "profession VARCHAR(255)," +
                "country VARCHAR(255)," +
                "city VARCHAR(255)," +
                "date_created DATE," +
                "PRIMARY KEY (id)" +
                ")";

        try (Statement statement = context.getConnection().createStatement()) {
            statement.execute(sql);
        }
    }
}
