package com.utn.prog3.tp_Final.views;

import com.utn.prog3.tp_Final.views.facturas.FacturasView;
import com.utn.prog3.tp_Final.views.facultades.FacultadesView;
import com.utn.prog3.tp_Final.views.pagos.PagosView;
import com.utn.prog3.tp_Final.views.terceros.TercerosView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class InicioView extends VerticalLayout {

    public InicioView() {
        // Configuraciones de la pantalla completa
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        H2 mensaje = new H2("¡Bienvenido al Panel de Control!");
        Paragraph subtitulo = new Paragraph("Seleccione el módulo al que desea acceder:");

        // --- CREACIÓN DE BOTONES DEL DASHBOARD ---
        Button btnTerceros = new Button("Gestión de Terceros", VaadinIcon.USERS.create());
        Button btnFacultades = new Button("Gestión de Facultades", VaadinIcon.ACADEMY_CAP.create());
        Button btnFacturas = new Button("Emisión de Facturas", VaadinIcon.FILE_TEXT_O.create());
        Button btnPagos = new Button("Registro de Pagos", VaadinIcon.MONEY_EXCHANGE.create());

        // Configuramos tamaño y color de los botones
        configurarBotonDashboard(btnTerceros, TercerosView.class);
        configurarBotonDashboard(btnFacultades, FacultadesView.class);
        configurarBotonDashboard(btnFacturas, FacturasView.class);
        configurarBotonDashboard(btnPagos, PagosView.class);

        // Agrupamos los botones en dos filas para que quede estético (como una grilla 2x2)
        HorizontalLayout fila1 = new HorizontalLayout(btnTerceros, btnFacultades);
        fila1.setSpacing(true);
        
        HorizontalLayout fila2 = new HorizontalLayout(btnFacturas, btnPagos);
        fila2.setSpacing(true);

        // Agregamos todo a la pantalla principal
        add(mensaje, subtitulo, fila1, fila2);
    }
    
    // Método auxiliar para darle estética de Dashboard a los botones
    private void configurarBotonDashboard(Button boton, Class<? extends com.vaadin.flow.component.Component> claseDestino) {
        boton.setWidth("250px");
        boton.setHeight("80px");
        boton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        boton.getStyle().set("cursor", "pointer");
        
        // El enrutador ahora sí confía en el parámetro
        boton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(claseDestino)));
    }
}