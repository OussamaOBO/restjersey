package model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;


/**
 *
 * @author João Sávio C. Longo - joaosavio@gmail.com
 */
public class HibernateUtil {

    private static SessionFactory factory;

    static {
        try {
            Configuration conf = new AnnotationConfiguration();
            conf.configure("hibernate.cfg.xml");
            factory = conf.buildSessionFactory();
            geraTabelas(conf);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    /*
    static {
    Configuration conf = new AnnotationConfiguration()
    .addAnnotatedClass(Usuario.class)
    .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
    .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/gia")
    .setProperty("hibernate.connection.username", "postgres")
    .setProperty("hibernate.connection.password", "");
    factory = conf.buildSessionFactory();
    geraTabelas(conf);
    }*/

    public static Session getSession() {
        return factory.openSession();
    }

    public static void geraTabelas(Configuration conf) {
        SchemaUpdate su = new SchemaUpdate(conf);
        su.execute(true, true);

        /*
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario.setUsuario("adm");
        usuario.setSenha("B09C600FDDC573F117449B3723F23D64");
        usuario.setEmail("joaosavio@gmail.com");
        try {
        usuarioDAO.adiciona(usuario);
        } catch (Exception e) {
        //não manipular
        }
         */
    }
}
