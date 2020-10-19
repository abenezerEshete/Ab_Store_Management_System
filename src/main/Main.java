package main;

import Utility.FileAccessControl;
import Utility.HardCodedValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class Main extends Application {
    private static final SessionFactory ourSessionFactory;

    // main class

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            String[] s = new String[]{};
            main(s);
            //   FieldValues.LogError(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {

        return ourSessionFactory.openSession();

    }

    public static Session getUpdateSession() throws HibernateException {

        Session session = ourSessionFactory.openSession();
        session.beginTransaction();

        return session;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String DB_CONNECTION_IP = HardCodedValue.DB_CONNECTION_IP;

        String dbIpAddress = "";
        Parent root;
        if (new File(DB_CONNECTION_IP).exists())
            dbIpAddress = new FileAccessControl().readfromFileStringBuffer(DB_CONNECTION_IP).toString();
        if (dbIpAddress.isEmpty())
            root = FXMLLoader.load(getClass().getResource(HardCodedValue.dbConnectionLayout));//"Login.fxml"));
        else
            root = FXMLLoader.load(getClass().getResource(HardCodedValue.loginLayout));//"Login.fxml"));

        primaryStage.setTitle("AB Task Management System");

        String smallImage = new File(HardCodedValue.SMALL_LOGO_IMAGE).toURI().toString();

        String largeImage = new File(HardCodedValue.LARGE_LOGO_IMAGE).toURI().toString();

        primaryStage.getIcons().addAll(new Image(largeImage), new Image(smallImage));

        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(500);

        primaryStage.setMaximized(true);

        primaryStage.setScene(new Scene(root));
        primaryStage.setFullScreenExitHint("");

        primaryStage.setFullScreenExitKeyCombination(new KeyCombination() {
            @Override
            public boolean match(KeyEvent event) {
                return false;//ghhfde453super.match(event);
            }
        });
        primaryStage.show();
        System.out.println("max width==" + primaryStage.maxWidthProperty().getValue());
        System.out.println("max width===" + primaryStage.getWidth());
        System.out.println("----");


    }


}