package com.utn.prog3.tp_Final.views;

import com.utn.prog3.tp_Final.views.facturas.FacturasView;
import com.utn.prog3.tp_Final.views.facultades.FacultadesView;
import com.utn.prog3.tp_Final.views.pagos.PagosView;
import com.utn.prog3.tp_Final.views.terceros.TercerosView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MainLayout extends AppLayout {
	
	public MainLayout() {
		crearBarraSuperior();
		crearMenuLateral();
	}
	
	private void crearBarraSuperior() {
		DrawerToggle toggle = new DrawerToggle();
		
		H1 titulo = new H1("Sistema de Gestión de Terceros");
		titulo.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
		
		HorizontalLayout cabecera = new HorizontalLayout(toggle, titulo);
		cabecera.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		cabecera.setWidthFull();

		addToNavbar(cabecera);
	}
	
	private void crearMenuLateral() {
		// Creamos los botones con sus respectivos íconos
		Button btnInicio = new Button("Inicio", VaadinIcon.HOME.create());
		Button btnTerceros = new Button("Terceros", VaadinIcon.USERS.create());
		Button btnFacultades = new Button("Facultades", VaadinIcon.ACADEMY_CAP.create());
		Button btnFacturas = new Button("Facturas", VaadinIcon.FILE_TEXT_O.create());
		Button btnPagos = new Button("Pagos", VaadinIcon.MONEY_EXCHANGE.create());

		// A cada botón le damos estilo de menú y le decimos a dónde navegar (Cero Magia)
		configurarBotonMenu(btnInicio, InicioView.class);
		configurarBotonMenu(btnTerceros, TercerosView.class);
		configurarBotonMenu(btnFacultades, FacultadesView.class);
		configurarBotonMenu(btnFacturas, FacturasView.class);
		configurarBotonMenu(btnPagos, PagosView.class);

		// Metemos los botones en la caja vertical
		VerticalLayout menu = new VerticalLayout(btnInicio, btnTerceros, btnFacultades, btnFacturas, btnPagos);
		
		// Se lo agregamos al panel lateral
		addToDrawer(menu);
	}
	
	// Método auxiliar para no repetir código (Arquitectura limpia)
	private void configurarBotonMenu(Button boton, Class<? extends com.vaadin.flow.component.Component> claseDestino) {
        boton.setWidthFull(); 
        boton.addThemeVariants(ButtonVariant.LUMO_TERTIARY); 
        boton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(claseDestino)));
    }
}