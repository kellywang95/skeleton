package dao;

import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

public class JooqConfig {
    public static org.jooq.Configuration setupJooq() {
        final String jdbcUrl = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT from 'classpath:schema.sql'";
        JdbcConnectionPool cp = JdbcConnectionPool.create(jdbcUrl, "sa", "sa");
        org.jooq.Configuration jooqConfig = new DefaultConfiguration();
        jooqConfig.set(SQLDialect.MYSQL);
        jooqConfig.set(cp);
        return jooqConfig;
    }
}
