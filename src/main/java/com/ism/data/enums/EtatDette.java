package com.ism.data.enums;

public enum EtatDette {
    EnCours,Accepter,Refuser;

    
   public static EtatDette getEtatDette(String value) {
    for (EtatDette etatDette : EtatDette.values()) {
       if (etatDette.name().compareToIgnoreCase(value) == 0) {
          return etatDette;
       }
    }
    return null;
}

public static EtatDette getEtatDetteId (int id) {
    for (EtatDette etatDette : EtatDette.values()) {
       if (etatDette.ordinal() == (id - 1)) {
          return etatDette;
       }
    }
    return null;
}

public static int getEtatDetteIdAsInt(EtatDette value) {
    if (value != null) {
       return value.ordinal() + 1; 
    } else {
       throw new IllegalArgumentException("etatDette cannot be null");
    }
}
}
