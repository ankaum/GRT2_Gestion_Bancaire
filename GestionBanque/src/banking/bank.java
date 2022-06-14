package banking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class bank {
    public static void main(String args[]) //methode principale
            throws IOException
    {

        BufferedReader sc = new BufferedReader( new InputStreamReader(System.in));
        String name = "";
        int pass_code;
        int ac_no;
        int ch;

        while (true) {
            System.out.println("\n             ->|| Bienvenue ||<- \n"
                    + "PROJET REALISE PAR ANASS ZOUAIGA ET ABOUBACAR SYLLA\n");
            System.out.println("1)Creer un compte");
            System.out.println("2)Se connecter");

            try {
                System.out.print("\n Entrez votre choix: "); //input d'utilisateur
                ch = Integer.parseInt(sc.readLine());

                switch (ch) {
                    case 1:
                        try {
                            System.out.print(
                                    "Entrez votre nom: \n");
                            name = sc.readLine();
                            System.out.print("Entrez votre mdp: \n");
                            pass_code = Integer.parseInt(sc.readLine());

                            if (bankManagement.createAccount(
                                    name, pass_code)) {
                                System.out.println("COMPTE CREE AVEC SUCCES!\n");
                            }
                            else {
                                System.out.println(
                                        "ERREUR LORS DE LA CREATION DU COMPTE\n");
                            }
                        }
                        catch (Exception e) {
                            System.out.println(
                                    " ENTREZ DONNEES VALIDES\n");
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("Entrez nom:");
                            name = sc.readLine();
                            System.out.print("Entrez mdp:");
                            pass_code = Integer.parseInt(sc.readLine());
                            if (bankManagement.loginAccount(name, pass_code)) {
                                System.out.println("SUCCES!\n");
                            }
                            else {
                                System.out.println("ERREUR\n");
                            }
                        }
                        catch (Exception e) {
                            System.out.println(" ERREUR, ENTREZ DONEES VALIDES!\n");
                        }
                        break;
                    default:
                        System.out.println("ENTREE INVALIDE!\n");
                }
                if (ch == 5) {
                    System.out.println(
                            "FIN DU PRG\n BONNE JOURNEE :)");
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Entrez un choix valide!");
            }
        }
        sc.close();
    }
}
