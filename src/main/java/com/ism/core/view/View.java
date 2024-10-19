package com.ism.core.view;

import java.util.List;

public interface View<T> {
    T create ();
    void afficher(List<T> liste);
}