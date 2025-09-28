package dev.boooiil.historia.core.database.sql.tables;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.configuration.specific.TemperatureConfig;
import dev.boooiil.historia.core.database.sql.DatabaseExecutor;
import dev.boooiil.historia.core.database.sql.DatabaseFields;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.player.culture.Cultures;
import dev.boooiil.historia.core.proficiency.Proficiency;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public class HistoriaTable {

    private static final DatabaseExecutor HISTORIA_EXECUTOR = HistoriaCore.Companion.getDatabaseExecutor();

    public static final DatabaseFields<Void, UUID> TABLE =
            DatabaseFields.of(
                    "table",
                    null,
                    null,
                    obj -> {
                        String string = "CREATE TABLE IF NOT EXISTS " +
                                "historia(uuid varchar(36), " +
                                "username varchar(16), " +
                                "proficiency varchar(10), " +
                                "culture varchar(17), " +
                                //"level int, " +
                                "experience int, " +
                                "temperature float, " +
                                "login bigint, " +
                                "logout bigint, " +
                                "playtime bigint, " +
                                "PRIMARY KEY (uuid))";

                        HISTORIA_EXECUTOR.executor(string);
                    }
            );

    public static final DatabaseFields<UUID, String> UUID =
            DatabaseFields.of(
                    "uuid",
                    null,
                    username -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "uuid",
                                    username,
                                    result -> {
                                        String sUUID = HISTORIA_EXECUTOR.getResult(result, "uuid", String.class);

                                        if (sUUID == null) return null;
                                        return java.util.UUID.fromString(sUUID);
                                    }
                            ),
                    map -> {
                        if (map == null || !map.containsKey("uuid") || !map.containsKey("username")) {
                            return;
                        }

                        String username = map.get("username").toString();
                        String uuid = map.get("uuid").toString();

                        String string = "INSERT INTO historia VALUES ('" +
                                uuid + "', " +            // uuid
                                "'" + username + "', " + // username
                                "'none', " + // proficiency
                                "'none', " + // culture
                                // "0, " + // level
                                "0, " + // experience
                                TemperatureConfig.INITIAL_CONSTANT_TEMP + ", " + // temperature
                                System.currentTimeMillis() + ", " + // login
                                "0, " + // logout
                                "0)"; // playtime

                        HISTORIA_EXECUTOR.executor(string);

                    }
            );

    public static final DatabaseFields<HistoriaPlayer, UUID> PLAYER =
            DatabaseFields.of(
                    "player",
                    (v, u) -> {
                        UUID uuid = v.getUUID();
                        String username = v.getUsername();
                        String proficiency = v.getProficiency().getName().getKey().toLowerCase();
                        String culture = v.getCulture().name().toLowerCase();
                        //int level = historiaPlayer.getLevel();
                        int experience = v.getCurrentExperience();
                        double temperature = v.getCurrentTemperature();

                        String query = "UPDATE historia " +
                                "SET proficiency = '" + proficiency + "', " +
                                "culture = '" + culture + "', " +
                                "username = '" + username + "', " +
                                //"level = '" + level + "', " +
                                "experience = " + experience + ", " +
                                "temperature = " + temperature + " " +
                                "WHERE uuid = '" + uuid + "' AND " +
                                "(proficiency != '" + proficiency + "' OR " +
                                "culture != '" + culture + "' OR " +
                                "username != '" + username + "' OR " +
                                //"level != '" + level + "' OR " +
                                "temperature != " + temperature + " OR " +
                                "experience != " + experience + ")";

                        HISTORIA_EXECUTOR.updateExecutor(query, 1);
                    },
                    uuid -> {
                        String string = "SELECT * FROM historia WHERE uuid = '" + uuid + "'";

                        return HISTORIA_EXECUTOR.queryExecutor(string, result -> {

                            if (!HISTORIA_EXECUTOR.nextResult(result)) {
                                return null;
                            }

                            String username = HISTORIA_EXECUTOR.getResult(result, "username", String.class);
                            Proficiency.ProficiencyName proficiencyName = Proficiency.ProficiencyName.Companion
                                    .fromString(HISTORIA_EXECUTOR.getResult(result, "proficiency", String.class));
                            Cultures culture = Cultures.getCulture(HISTORIA_EXECUTOR.getResult(result, "culture", String.class));
                            //int level = getResult(result, "level", Integer.class);
                            int experience = HISTORIA_EXECUTOR.getResult(result, "experience", Integer.class);
                            double temperature = HISTORIA_EXECUTOR.getResult(result, "temperature", Double.class);
                            long login = HISTORIA_EXECUTOR.getResult(result, "login", Long.class);
                            long logout = HISTORIA_EXECUTOR.getResult(result, "logout", Long.class);
                            long playtime = HISTORIA_EXECUTOR.getResult(result, "playtime", Long.class);

                            return new HistoriaPlayer(uuid, username, proficiencyName, culture, temperature, experience, login, logout,
                                    playtime);
                        });
                    },
                    HistoriaTable.UUID::insert
            );

    public static final DatabaseFields<String, UUID> USERNAME =
            DatabaseFields.of(
                    "username",
                    (v, u) -> {
                        HistoriaCore
                                .Companion
                                .getDatabaseExecutor()
                                .updateExecutor(
                                        "historia",
                                        "username", v, u);
                    },
                    uuid -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "username",
                                    uuid,
                                    result -> HISTORIA_EXECUTOR.getResult(result, "username", String.class)
                            ),
                    null
            );

    public static final DatabaseFields<String, UUID> PROFICIENCY =
            DatabaseFields.of(
                    "proficiency",
                    (v, u) -> {
                        HistoriaCore
                                .Companion
                                .getDatabaseExecutor()
                                .updateExecutor(
                                        "historia",
                                        "proficiency", v, u);
                    },
                    uuid -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "proficiency",
                                    uuid,
                                    result -> HISTORIA_EXECUTOR.getResult(result, "proficiency", String.class)
                            ),
                    null
            );

    public static final DatabaseFields<String, UUID> CULTURE =
            DatabaseFields.of(
                    "culture",
                    (v, u) -> {
                        HistoriaCore
                                .Companion
                                .getDatabaseExecutor()
                                .updateExecutor(
                                        "historia",
                                        "culture", v, u);
                    },
                    uuid -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "culture",
                                    uuid,
                                    result -> HISTORIA_EXECUTOR.getResult(result, "culture", String.class)
                            ),
                    null
            );


    public static final DatabaseFields<Integer, UUID> EXPERIENCE =
            DatabaseFields.of(
                    "experience",
                    (v, u) -> {
                        HistoriaCore
                                .Companion
                                .getDatabaseExecutor()
                                .updateExecutor(
                                        "historia",
                                        "experience", v, u);
                    },
                    uuid -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "experience",
                                    uuid,
                                    result -> HISTORIA_EXECUTOR.getResult(result, "experience", Integer.class)
                            ),
                    null
            );


    public static final DatabaseFields<Float, UUID> TEMPERATURE =
            DatabaseFields.of(
                    "temperature",
                    (v, u) -> {
                        HistoriaCore
                                .Companion
                                .getDatabaseExecutor()
                                .updateExecutor(
                                        "historia",
                                        "temperature", v, u);
                    },
                    uuid -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "temperature",
                                    uuid,
                                    result -> HISTORIA_EXECUTOR.getResult(result, "temperature", Float.class)
                            ),
                    null
            );

    public static final DatabaseFields<Long, UUID> LOGIN =
            DatabaseFields.of(
                    "login",
                    (v, u) -> {
                        HistoriaCore
                                .Companion
                                .getDatabaseExecutor()
                                .updateExecutor(
                                        "historia",
                                        "login", v, u);
                    },
                    uuid -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "login",
                                    uuid,
                                    result -> HISTORIA_EXECUTOR.getResult(result, "login", Long.class)
                            ),
                    null
            );

    public static final DatabaseFields<Long, UUID> LOGOUT =
            DatabaseFields.of(
                    "logout",
                    (v, u) -> {
                        HistoriaCore
                                .Companion
                                .getDatabaseExecutor()
                                .updateExecutor(
                                        "historia",
                                        "logout", v, u);
                    },
                    uuid -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "logout",
                                    uuid,
                                    result -> HISTORIA_EXECUTOR.getResult(result, "logout", Long.class)
                            ),
                    null
            );

    public static final DatabaseFields<Long, UUID> PLAYTIME =
            DatabaseFields.of(
                    "playtime",
                    (v, u) -> {
                        HistoriaCore
                                .Companion
                                .getDatabaseExecutor()
                                .updateExecutor(
                                        "historia",
                                        "playtime", v, u);
                    },
                    uuid -> HistoriaCore
                            .Companion
                            .getDatabaseExecutor()
                            .queryExecutor(
                                    "historia",
                                    "playtime",
                                    uuid,
                                    result -> HISTORIA_EXECUTOR.getResult(result, "playtime", Long.class)
                            ),
                    null
            );
}
