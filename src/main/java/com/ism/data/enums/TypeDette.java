package com.ism.data.enums;

public enum TypeDette {
   Solde,nonSolde;

   public static TypeDette getTypeDette(String value) {
         for (TypeDette typeDette : TypeDette.values()) {
            if (typeDette.name().compareToIgnoreCase(value) == 0) {
               return typeDette;
            }
         }
         return null;
   }

   public static TypeDette getTypeDetteId (int id) {
         for (TypeDette typeDette : TypeDette.values()) {
            if (typeDette.ordinal() == (id - 1)) {
               return typeDette;
            }
         }
         return null;
   }

   public static int getTypeDetteIdAsInt(TypeDette value) {
         if (value != null) {
            return value.ordinal() + 1; // Ou une autre logique pour obtenir l'ID
         } else {
            throw new IllegalArgumentException("TypeDette cannot be null");
         }
   }
}
