package com.utn.prog3.tp_Final.views;

import com.utn.prog3.tp_Final.views.facturas.FacturasView;
import com.utn.prog3.tp_Final.views.facultades.FacultadesView;
import com.utn.prog3.tp_Final.views.terceros.TercerosView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
	public MainLayout() {
		crearBarraSuperior();
		crearMenuLateral();
	}
	
	private void crearBarraSuperior() {
		DrawerToggle toggle = new DrawerToggle();
		
		H1 titulo = new H1("Sistema de Gestion de Terceros");
		titulo.getStyle().set("font-size", "var(--lumo-font-size-l)")
        .set("margin", "0");
		
		HorizontalLayout cabecera = new HorizontalLayout(toggle, titulo);
		cabecera.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        cabecera.setWidthFull();

        addToNavbar(cabecera);
        
	}
	private void crearMenuLateral() {
        // Acá vamos a crear los links (botones) para navegar
        // (Por ahora solo ponemos el link a la pantalla de Inicio. 
        // A medida que creemos la vista de Terceros, Facturas, etc., las descomentamos acá)
        
        RouterLink linkInicio = new RouterLink("Inicio", InicioView.class);
        RouterLink linkTerceros = new RouterLink("Terceros", TercerosView.class);
        RouterLink linkFacultades = new RouterLink("Facultades", FacultadesView.class);
        RouterLink linkFacturas = new RouterLink("Facturas", FacturasView.class);
        // RouterLink linkPagos = new RouterLink("Pagos", PagosView.class);

        // Metemos los links en una caja vertical (uno abajo del otro)
        VerticalLayout menu = new VerticalLayout(linkInicio, linkTerceros, linkFacultades,linkFacturas);
        
        // Se lo agregamos al panel lateral de nuestra plantilla
        addToDrawer(menu);
    }
}
