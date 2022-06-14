package banking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class bankManagement {
    private static final int NULL = 0;
    static Connection con = connection.getConnection();
    static String sql = "";
    public static boolean createAccount(String name, int passCode) // fct de creation de compte
    {
        try {
            if (name == "" || passCode == NULL) {
                System.out.println("Champs requis!");
                return false;
            }
            Statement st = con.createStatement();
            sql = "INSERT INTO customer(cname,balance,pass_code) values('"
                    + name + "',1000," + passCode + ")";

            // Execution
            if (st.executeUpdate(sql) == 1) {
                System.out.println("Felicitations " + name
                        + ", vous pouvez se connecter maintenant");
                return true;
            }
            // return
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Nom d'utilisateur non disponible");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean loginAccount(String name, int passCode) // login
    {
        try {
            // validation
            if (name == "" || passCode == NULL) {
                System.out.println("Champs requis");
                return false;
            }
            // query
            sql = "select * from customer where cname='"
                    + name + "' and pass_code=" + passCode;
            PreparedStatement st
                    = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            // Execution
            BufferedReader sc = new BufferedReader(
                    new InputStreamReader(System.in));

            if (rs.next()) {
                //Apres menu du login

                int ch = 5;
                int amt = 0;
                int senderAc = rs.getInt("ac_no");
                ;
                int receiveAc;
                while (true) {
                    try {
                        System.out.println(
                                "Bienvenue, "
                                        + rs.getString("cname"));
                        System.out.println(
                                "1)Transferer de l'argent");
                        System.out.println("2)Consulter votre solde");
                        System.out.println("3)Se deconnecter");

                        System.out.print("Entrez votre choix:");
                        ch = Integer.parseInt(
                                sc.readLine());
                        if (ch == 1) {
                            System.out.print(
                                    "Entrez num de destinataire:");
                            receiveAc = Integer.parseInt(
                                    sc.readLine());
                            System.out.print(
                                    "Entrez montant:");
                            amt = Integer.parseInt(
                                    sc.readLine());

                            if (bankManagement
                                    .transferMoney(
                                            senderAc, receiveAc,
                                            amt)) {
                                System.out.println(
                                        "Transfert fait avec succes!\n");
                            }
                            else {
                                System.out.println(
                                        "Erreur!\n");
                            }
                        }
                        else if (ch == 2) {

                            bankManagement.getBalance(
                                    senderAc);
                        }
                        else if (ch == 3) {
                            break;
                        }
                        else {
                            System.out.println(
                                    "Entrez un choix valide\n");
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                return false;
            }
            // return
            return true;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Utilisateur non disponible");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void getBalance(int acNo) // Retourner solde
    {
        try {

            // query
            sql = "select * from customer where ac_no="
                    + acNo;
            PreparedStatement st
                    = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery(sql);
            System.out.println(
                    "-----------------------------------------------------------");
            System.out.printf("%12s %10s %10s\n",
                    "No compte", "Nom",
                    "Solde");

            // Execution

            while (rs.next()) {
                System.out.printf("%12d %10s %10d.00\n",
                        rs.getInt("ac_no"),
                        rs.getString("cname"),
                        rs.getInt("balance"));
            }
            System.out.println(
                    "-----------------------------------------------------------\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean transferMoney(int sender_ac, int reveiver_ac, int amount) throws SQLException // Transfert de fonds
    {
        // validation
        if (reveiver_ac == NULL || amount == NULL) {
            System.out.println("Champ requis!");
            return false;
        }
        try {
            con.setAutoCommit(false);
            sql = "select * from customer where ac_no="
                    + sender_ac;
            PreparedStatement ps
                    = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getInt("balance") < amount) {
                    System.out.println(
                            "Fonds insuffisants!");
                    return false;
                }
            }

            Statement st = con.createStatement();

            // debit
            con.setSavepoint();

            sql = "update customer set balance=balance-"
                    + amount + " where ac_no=" + sender_ac;
            if (st.executeUpdate(sql) == 1) {
                System.out.println("Montant debite!");
            }

            // credit
            sql = "update customer set balance=balance+"
                    + amount + " where ac_no=" + reveiver_ac;
            st.executeUpdate(sql);

            con.commit();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        return false;
    }
}
