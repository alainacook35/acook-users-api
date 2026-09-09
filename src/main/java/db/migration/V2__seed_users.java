package db.migration;

import com.acook.magmutualusersapi.entity.CsvUser;
import com.acook.magmutualusersapi.service.UserCsvReaderService;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class V2__seed_users extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        UserCsvReaderService csvReader = new UserCsvReaderService("UserInformation.csv");
        List<CsvUser> users = csvReader.readUsers();
        String sql = "INSERT INTO project_user " +
                "(id, first_name, last_name, email, profession, country, city, date_created) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        long maxId = 0L;
        try (PreparedStatement statement = context.getConnection().prepareStatement(sql)) {
            for (CsvUser user : users) {
                statement.setLong(1, user.getId());
                statement.setString(2, user.getFirstName());
                statement.setString(3, user.getLastName());
                statement.setString(4, user.getEmail());
                statement.setString(5, user.getProfession());
                statement.setString(6, user.getCountry());
                statement.setString(7, user.getCity());
                statement.setDate(8, Date.valueOf(user.getDateCreated()));

                statement.addBatch();

                if (user.getId() > maxId) {
                    maxId = user.getId();
                }
            }

            statement.executeBatch();
        }

        // Reset autoincrement start based on largest id in csv dataset
        try (Statement maxIDStatement = context.getConnection().createStatement()) {
            maxIDStatement.execute(String.format("ALTER TABLE project_user ALTER COLUMN id RESTART WITH %s", maxId + 1));
        }
    }
}
