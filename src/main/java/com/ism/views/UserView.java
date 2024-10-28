package com.ism.views;

import java.util.List;
import java.util.Scanner;

import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Client;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.services.list.ClientService;
import com.ism.data.services.list.UserService;

public class UserView extends ViewImpl<User>  {

    private ClientService clientService;
    private UserService userService;
    private ClientView clientView;

    public UserView(Scanner scanner, ClientService clientService, UserService userService, ClientView clientView) {
        super(scanner);
        this.clientService = clientService;
        this.userService = userService;
        this.clientView = clientView;
    }

    @Override
    public User create() {
        User user=new User();
        System.out.println("1-pour un client existant");
        System.out.println("2-nouveau User ");

        if (choiceToContinue()==1) {
            Client clientChoisi=clientView.askClient();
            System.out.println("Client récupéré: " + clientChoisi);
            if (clientChoisi==null) {
                return null;
            }else{
                if (clientChoisi.getUser()==null) {
                    user=createUser();
                    clientChoisi.setUser(user);
                    user.setClient(clientChoisi);
                }else{
                    System.out.println("ce client a deja un compte");
                }  
            } 
            return user;

        } else {
            Client cl= clientView.create();
            clientService.save(cl);
            user=createUserWithChoice();
            cl.setUser(user);
            user.setClient(cl);
            return user; 
        }
    }

    private UserRole saisieRoleCompte() {
        int choix;
        do {
            System.out.println("veuillez selectionner le type d'étudiant");
            for (UserRole roleCompte : UserRole.values()) {
                System.out.println((roleCompte.ordinal() + 1) + "-" + roleCompte.name());
            }
            choix = scanner.nextInt();
        } while (choix <= 0 || choix > UserRole.values().length);
        return UserRole.values()[choix - 1];
    }
    
    public void listerParRole(){
        List<User> user = userService.getByUserRole(choiceUserRole());
        // User user= userService.selectByRole(choiceUserRole());
        if (user==null) {
            System.out.println("il n'y a pas de user avec ce role !!! ");
        } else {
            System.out.println(user);
        }
    }

    public void listerParActif(){
        List<User> user = userService.getByUserEtat();
        if (user!=null) {
            System.out.println(user);
        } else {
            System.out.println("il n'y a pas de user actif");
        }
    }

    public void listerLesUtulisateur(){
        if(userService.show().isEmpty()){
            System.out.println("il n'y a pas de user");
            return;
        }else{
            System.out.println("1-lister par role ");
            System.out.println("2-lister les user actif ");
            if (choiceToContinue()==1) {
                listerParRole();
            } else {
                listerParActif();
            }
        }
       
    }

    private UserRole choiceUserRole() {
        int choix;
        do {
            System.out.println("veuillez selectionner le role du user");
            for (UserRole userRole : UserRole.values()) {
                System.out.println((userRole.ordinal() + 1) + "-" + userRole.name());
            }
            choix = scanner.nextInt();
        } while (choix <= 0 || choix > UserRole.values().length);
        return UserRole.values()[choix - 1];
    }

    public User createUser(){
        User user =new User();
        user.isActif();
        scanner.nextLine();
        user.setEmail(saisieEmail());
        user.setLogin(saisieLogin());
        user.setPassword(saisiePassWord()); 
        user.setUserRole(UserRole.Client);
        return user;
    }

    public User createUserWithChoice(){
        User user =new User();
        user.isActif();
        scanner.nextLine();
        user.setEmail(saisieEmail());
        user.setLogin(saisieLogin());
        user.setPassword(saisiePassWord()); 
        System.out.println("voulez vous lui ajouter un role");
        if (askToContinue()==1) {
            user.setUserRole(saisieRoleCompte());
        }else{
            return user;
        }
        return user;
    }

   
    public String saisieLogin() {
        String login = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.println("Veuillez saisir le login du user :");
            login = scanner.nextLine().trim();
    
            if (login.isEmpty()) {
                System.out.println("Erreur : le login ne doit pas être vide.");
            } else if (login.length() < 3) {
                System.out.println("Erreur : le login doit contenir au moins 3 caractères.");
            } else if (!login.matches("[a-zA-Z0-9_]+")) {
                System.out.println("Erreur : Des lettres des chiffres et/ou des underscore seulement sont acceptés.");
            } else {
                User user=userService.getByLogin(login);
                    if (user!=null) {
                        System.out.println("Erreur : l'email a deja un compte .");
                    }else{
                        isValid = true;
                    }           
            }
        }
        return login;
    }

    public String saisieEmail() {
        String email = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.println("Veuillez saisir le email du user :");
            email = scanner.nextLine().trim();
    
            if (email.isEmpty()) {
                System.out.println("Erreur : l'email ne doit pas être vide.");
            } else if (email.length() < 5) {
                System.out.println("Erreur : l'email doit contenir au moins 5 caractères.");
            } else if (!email.matches("[a-zA-Z0-9_.@]+")) {
                System.out.println("Erreur : votre email est invalide il manque @  .");
            } else {
                User user=userService.getByEmail(email);
                if (user != null) {
                    System.out.println("Erreur : l'email a deja un compte .");
                }else{
                    isValid = true;
                }
            }
        }
        return email;
    }

    public String saisiePassWord() {
        String psw = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.println("Veuillez saisir votre password ");
            psw = scanner.nextLine().trim();
    
            if (psw.isEmpty()) {
                System.out.println("Erreur : le password ne doit pas être vide.");
            } else if (psw.length() < 2) {
                System.out.println("Erreur : le password doit contenir au moins 2 caractères.");
            } else {
                isValid = true;
            }
        }
        return psw;
    }
}
