package com.ism.core.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public abstract class ViewImpl<T> implements View<T>  {

    protected Scanner scanner;

    public ViewImpl(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public void afficher(List<T> datas) {
       for (T data : datas) {
        System.err.println(data);
       }
    }
    public int askToContinue() {
      int choix;
      do {
          System.out.println("1- Oui ");
          System.out.println("2- Non");
          choix = scanner.nextInt();
      } while (choix < 1 || choix > 2);
      return choix ;

  }
  public int choiceToContinue() {
    int choix;
    do {
        choix = scanner.nextInt();
    } while (choix < 1 || choix > 2);
    return choix ;

}

  public static LocalDate formatDate(String date){
   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  return LocalDate.parse(date,formatter);
  } 
  public static LocalTime formatHeure(String heure){
   DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("HH:mm");
  return  LocalTime.parse(heure,formatter);
}
}
