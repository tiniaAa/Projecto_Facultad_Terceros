package com.utn.prog3.tp_Final.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

// ¡ACÁ ESTÁ LA MAGIA!
// 1. Le decimos que esta es la ruta principal ("")
// 2. Le indicamos que NO se dibuje suelta, sino ADENTRO del MainLayout que creaste recién.
@Route(value = "", layout = MainLayout.class)
public class InicioView extends VerticalLayout {

    public InicioView() {
        // Un mensaje simple de bienvenida centrado
        H2 mensaje = new H2("¡Bienvenido al sistema administrativo!");
        
        // Configuraciones de diseño de la caja (VerticalLayout)
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(mensaje);
    }
}