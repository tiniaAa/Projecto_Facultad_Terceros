package com.utn.prog3.tp_Final.views.facturas;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.utn.prog3.tp_Final.Dto.FacturasDTO;
import com.utn.prog3.tp_Final.Dto.Facturas_ItemsDTO;
import com.utn.prog3.tp_Final.Dto.TercerosDTO;
import com.utn.prog3.tp_Final.Service.iservices.IFacturarsService;
import com.utn.prog3.tp_Final.Service.iservices.ITercerosService;
import com.utn.prog3.tp_Final.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
// IMPORTS AGREGADOS PARA LAS NOTIFICACIONES:
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "facturas", layout = MainLayout.class)
public class FacturasView extends VerticalLayout {

    private final IFacturarsService facturasService;
    private final ITercerosService tercerosService;
    private final Grid<FacturasDTO> grillaPrincipal = new Grid<>(FacturasDTO.class, false);
    private final ComboBox<FacturasDTO> comboSelectorRapido = new ComboBox<>("Buscador Rápido");

    public FacturasView(IFacturarsService facturasService, ITercerosService tercerosService) {
        this.facturasService = facturasService;
        this.tercerosService = tercerosService;

        setSizeFull();

        H2 titulo = new H2("Gestión de Facturas");
        
        Button botonNueva = new Button("Emitir Nueva Factura", VaadinIcon.PLUS.create());
        botonNueva.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonNueva.addClickListener(e -> abrirModalFactura(new FacturasDTO(), false));

        // Combo para selección rápida
        comboSelectorRapido.setWidth("300px");
        comboSelectorRapido.setPlaceholder("Seleccione una factura...");
        comboSelectorRapido.setItemLabelGenerator(f -> "Nº " + f.getNumero() + " - " + f.getTercero().getNombre());
        comboSelectorRapido.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                abrirModalFactura(e.getValue(), true);
                comboSelectorRapido.clear();
            }
        });

        HorizontalLayout barraSuperior = new HorizontalLayout(botonNueva, comboSelectorRapido);
        barraSuperior.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        configurarGrillaPrincipal();
        actualizarGrillaPrincipal();

        add(titulo, barraSuperior, grillaPrincipal);
    }

    private void configurarGrillaPrincipal() {
        grillaPrincipal.addColumn(FacturasDTO::getNumero).setHeader("Número");
        grillaPrincipal.addColumn(f -> f.getTercero().getNombre()).setHeader("Cliente");
        grillaPrincipal.addColumn(FacturasDTO::getFecha_factura).setHeader("Fecha");

        // Botones de acción
        grillaPrincipal.addComponentColumn(factura -> {
            Button btnEditar = new Button(VaadinIcon.EDIT.create());
            btnEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            btnEditar.addClickListener(e -> abrirModalFactura(factura, false));

            Button btnEliminar = new Button(VaadinIcon.TRASH.create());
            btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            btnEliminar.addClickListener(e -> {
                facturasService.eliminar(factura.getId());
                actualizarGrillaPrincipal();
                
                // --- NOTIFICACIÓN DE ELIMINACIÓN ---
                Notification.show("Factura eliminada correctamente")
                            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            });

            return new HorizontalLayout(btnEditar, btnEliminar);
        }).setHeader("Acciones").setAutoWidth(true).setFlexGrow(0);

        // Doble clic para detalle
        grillaPrincipal.addItemDoubleClickListener(e -> abrirModalFactura(e.getItem(), true));
    }

    private void actualizarGrillaPrincipal() {
        List<FacturasDTO> lista = this.facturasService.listarTodas();
        grillaPrincipal.setItems(lista);
        comboSelectorRapido.setItems(lista);
    }

    private void abrirModalFactura(FacturasDTO factura, boolean soloLectura) {
        Dialog modal = new Dialog();
        modal.setWidth("900px");
        modal.setHeaderTitle(soloLectura ? "Detalle de Factura" : "Emitir/Editar Factura");

        // --- CABECERA ---
        IntegerField campoNumero = new IntegerField("Nº Factura");
        DatePicker campoFecha = new DatePicker("Fecha");
        ComboBox<TercerosDTO> comboTercero = new ComboBox<>("Cliente");
        comboTercero.setItems(tercerosService.listarTerceros());
        comboTercero.setItemLabelGenerator(TercerosDTO::getNombre);

        Binder<FacturasDTO> binder = new Binder<>(FacturasDTO.class);
        binder.forField(campoNumero).bind(FacturasDTO::getNumero, FacturasDTO::setNumero);
        binder.forField(comboTercero).bind(FacturasDTO::getTercero, FacturasDTO::setTercero);
        binder.forField(campoFecha).withConverter(
            d -> d != null ? java.util.Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null,
            d -> d != null ? d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null
        ).bind(FacturasDTO::getFecha_factura, FacturasDTO::setFecha_factura);
        binder.setBean(factura);

        // --- ÍTEMS (Cero Magia: Lista local temporal) ---
        List<Facturas_ItemsDTO> listaItems = (factura.getItems() != null) ? new ArrayList<>(factura.getItems()) : new ArrayList<>();
        Grid<Facturas_ItemsDTO> grillaItems = new Grid<>(Facturas_ItemsDTO.class, false);
        grillaItems.addColumn(Facturas_ItemsDTO::getDetalle).setHeader("Detalle");
        grillaItems.addColumn(Facturas_ItemsDTO::getCantidad).setHeader("Cant.");
        grillaItems.addColumn(Facturas_ItemsDTO::getMonto).setHeader("Precio");
        grillaItems.setItems(listaItems);
        grillaItems.setHeight("200px");

        // Inputs para añadir
        TextField inDetalle = new TextField("Detalle");
        NumberField inCant = new NumberField("Cant.");
        NumberField inPrecio = new NumberField("Precio");
        Button btnAdd = new Button("Añadir", e -> {
            Facturas_ItemsDTO item = new Facturas_ItemsDTO();
            item.setDetalle(inDetalle.getValue());
            item.setCantidad(inCant.getValue());
            item.setMonto(inPrecio.getValue());
            listaItems.add(item);
            grillaItems.getDataProvider().refreshAll();
        });

        if (soloLectura) {
            campoNumero.setReadOnly(true); campoFecha.setReadOnly(true);
            comboTercero.setReadOnly(true); btnAdd.setVisible(false);
        }

        modal.add(new VerticalLayout(new H3("Cabecera"), new FormLayout(campoNumero, campoFecha, comboTercero), 
                                     new Hr(), new H3("Ítems"), new HorizontalLayout(inDetalle, inCant, inPrecio, btnAdd), grillaItems));

        Button btnGuardar = new Button("Guardar", e -> {
            factura.setItems(listaItems);
            
            // --- LOGICA DE GUARDADO Y NOTIFICACIÓN ---
            boolean esNueva = (factura.getId() == 0);
            
            if (esNueva) {
                facturasService.crear(factura);
            } else {
                facturasService.actualizar(factura.getId(), factura);
            }
            
            actualizarGrillaPrincipal();
            modal.close();
            
            // Evaluamos el mensaje dependiendo si se creó o se editó
            String mensaje = esNueva ? "¡Factura creada exitosamente!" : "¡Factura editada correctamente!";
            Notification.show(mensaje).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        
        if (soloLectura) btnGuardar.setVisible(false);
        modal.getFooter().add(new Button("Cerrar", e -> modal.close()), btnGuardar);
        modal.open();
    }
}