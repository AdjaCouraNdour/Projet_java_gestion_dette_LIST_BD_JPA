package com.ism.data.enums;

public enum EtatArticle {
    Disponible,Indisponible;

    public static EtatArticle getEtatArticle(String value) {
        for (EtatArticle etatArticle : EtatArticle.values()) {
            if (etatArticle.name().compareToIgnoreCase(value) == 0) {
                return etatArticle;
            }
        }
        return null;
    }

    public static EtatArticle getEtatArticleId (int id) {
        for (EtatArticle etatArticle : EtatArticle.values()) {
            if (etatArticle.ordinal() == (id - 1)) {
                return etatArticle;
            }
        }
        return null;
    }

    public static int getEtatArticleIdAsInt(EtatArticle etat) {
        if (etat != null) {
            return etat.ordinal() + 1;
        } else {
            throw new IllegalArgumentException("etatArticle cannot be null");
        }
    }
}
