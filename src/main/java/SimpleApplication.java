import controllers.*;
import dao.JooqConfig;
import dao.ReceiptDao;
import dao.TagDao;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.session.SessionHandler;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;


public class SimpleApplication extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new SimpleApplication().run(args);
    }

    private static void enableSessionSupport(Environment env) {
        env.servlets().setSessionHandler(new SessionHandler());
    }

    @Override
    public void run(Configuration cfg, Environment env) {
        // Create any global resources you need here
        org.jooq.Configuration jooqConfig = JooqConfig.setupJooq();
        ReceiptDao receiptDao = new ReceiptDao(jooqConfig);
        TagDao tagDao = new TagDao(jooqConfig);

        // Register all Controllers below.  Don't forget
        // you need class and method @Path annotations!
        env.jersey().register(new HelloWorldController());
        env.jersey().register(new StaticHtmlController());
        env.jersey().register(new ReceiptImageController());

        env.jersey().register(new ReceiptController(receiptDao,tagDao));
        env.jersey().register(new TagController(tagDao));

        SimpleApplication.enableSessionSupport(env);
    }
}
