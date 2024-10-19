package com.ism.views;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Client;
import com.ism.data.services.list.ClientService;

public class ClientView extends ViewImpl<Client> {

    private ClientService clientService;

    public ClientView(Scanner scanner, ClientService clientService) {
        super(scanner);
        this.clientService = clientService;
    }

    @Override
    public Client create() {
        Client cl=new Client();
        scanner.nextLine();
        System.out.println("entre le prenom du client");
        cl.setPrenom(saisieChamp());
        System.out.println("entre le nom du client");
        cl.setNom(saisieChamp());
    
    
        cl.setTelephone(saisieTelephone());

        System.out.println("entre l'address du client");
        cl.setAddress(saisieChamp());
        return cl;
    }

    public Client askClient() {
        // Vérifier s'il existe des clients
        if (clientService.show().isEmpty()) {
            System.out.println("Il n'y a pas encore de client. Veuillez en créer un d'abord.");
            return null;
        } else {
            Client cl = null;
            do {
                System.out.println(clientService.show());
                System.out.println("Veuillez choisir le client en entrant son ID (ou entrez 0 pour revenir à la liste) :");
    
                if (scanner.hasNextInt()) {
                    int clientId = scanner.nextInt();
                    if (clientId == 0) {
                        System.out.println("Demande annulée");
                        return null;
                    }
                    cl = clientService.getById(clientId);

                    if (cl == null) {
                        System.out.println("Ce client n'existe pas. Veuillez entrer un ID valide.");
                    }
                } else {
                    System.out.println("Veuillez entrer un ID valide.");
                    scanner.next(); 
                }
    
            } while (cl == null);
    
            return cl;
        }
    }
   
   public String saisieTelephone() {
        String numero = "";
        boolean isValide = false;
        while (!isValide) {
            System.out.println("Veuillez saisir le telephone du client (uniquement des chiffres) :");
            numero = scanner.nextLine().trim();
            try {
                if (numero.isEmpty()) {
                    System.out.println("Erreur : le telephone ne doit pas être vide.");
                } else if (numero.length() != 9) {
                    System.out.println("Erreur : le telephone doit contenir exactement 9 chiffres.");
                } else if (!numero.matches("[0-9]+")) {
                    System.out.println("Erreur : le telephone ne doit contenir que des chiffres.");
            
                } else {
                Client client = clientService.getByNumero(numero);
                    if (client != null) {
                        System.out.println("Ce téléphone existe déjà pour le client : " + client.getNom()); // ou n'importe quelle propriété du client
                    } else {
                        isValide = true;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Veuillez saisir des caractères valides.");
                scanner.nextLine();
            }
        }
        return numero;
    } 

    public String saisieChamp() {
        String champ = "";
        boolean isValid = false;
        while (!isValid) {
            champ = scanner.nextLine().trim();
            if (champ.isEmpty()) {
                System.out.println("Erreur : ce champ ne doit pas être vide.");
            } else {
                isValid = true;
            }
        }
        return champ;
    }
}
