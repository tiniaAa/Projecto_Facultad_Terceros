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

    public FacturasView(IFacturarsService facturasService, ITercerosService tercerosService) {
        this.facturasService = facturasService;
        this.tercerosService = tercerosService;

        setSizeFull();

        H2 titulo = new H2("Gestión de Facturas");
        
        Button botonNueva = new Button("Emitir Nueva Factura", VaadinIcon.PLUS.create());
        botonNueva.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonNueva.addClickListener(e -> abrirModalFactura(new FacturasDTO(), false));

        configurarGrillaPrincipal();
        actualizarGrillaPrincipal();

        add(titulo, botonNueva, grillaPrincipal);
    }

    private void configurarGrillaPrincipal() {
        grillaPrincipal.addColumn(FacturasDTO::getId).setHeader("ID").setWidth("80px").setFlexGrow(0);
        grillaPrincipal.addColumn(FacturasDTO::getNumero).setHeader("Número Factura");
        grillaPrincipal.addColumn(factura -> factura.getTercero() != null ? factura.getTercero().getNombre() : "Sin Tercero").setHeader("Tercero / Cliente");
        grillaPrincipal.addColumn(FacturasDTO::getFecha_factura).setHeader("Fecha");

        grillaPrincipal.addComponentColumn(factura -> {
            Button btnVer = new Button(VaadinIcon.EYE.create());
            btnVer.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            btnVer.addClickListener(e -> abrirModalFactura(factura, true));
            return btnVer;
        }).setHeader("Ver Detalle").setAutoWidth(true).setFlexGrow(0);
    }

    private void actualizarGrillaPrincipal() {
         grillaPrincipal.setItems(this.facturasService.listarTodas());
    }

    private void abrirModalFactura(FacturasDTO factura, boolean soloLectura) {
        Dialog modal = new Dialog();
        modal.setHeaderTitle(soloLectura ? "Detalle de Factura" : "Emitir Nueva Factura");
        modal.setWidth("900px"); 

        IntegerField campoNumero = new IntegerField("Nº Factura");
        DatePicker campoFecha = new DatePicker("Fecha");
        
        ComboBox<TercerosDTO> comboTercero = new ComboBox<>("Cliente / Proveedor");
        comboTercero.setItems(this.tercerosService.listarTerceros()); 
        comboTercero.setItemLabelGenerator(TercerosDTO::getNombre);

        Binder<FacturasDTO> binderCabecera = new Binder<>(FacturasDTO.class);
        binderCabecera.forField(campoNumero).asRequired("Requerido").bind(FacturasDTO::getNumero, FacturasDTO::setNumero);
        binderCabecera.forField(comboTercero).asRequired("Requerido").bind(FacturasDTO::getTercero, FacturasDTO::setTercero);
        
        
        binderCabecera.forField(campoFecha)
            .withConverter(
                localDate -> localDate != null ? java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null,
                date -> date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null
            )
            .asRequired("Requerido")
            .bind(FacturasDTO::getFecha_factura, FacturasDTO::setFecha_factura);

        binderCabecera.setBean(factura);

        FormLayout layoutCabecera = new FormLayout(campoNumero, campoFecha, comboTercero);

       
        List<Facturas_ItemsDTO> listaItemsRam = new ArrayList<>();
      
        if (factura.getItems() != null) {
            listaItemsRam.addAll(factura.getItems());
        }

        Grid<Facturas_ItemsDTO> grillaItems = new Grid<>(Facturas_ItemsDTO.class, false);
        grillaItems.addColumn(Facturas_ItemsDTO::getDetalle).setHeader("Detalle").setFlexGrow(1);
        grillaItems.addColumn(Facturas_ItemsDTO::getCantidad).setHeader("Cant.").setWidth("100px").setFlexGrow(0);
        grillaItems.addColumn(Facturas_ItemsDTO::getMonto).setHeader("Precio Unit.").setWidth("150px").setFlexGrow(0);
        // Columna calculada de subtotal
        grillaItems.addColumn(item -> (item.getCantidad() != null && item.getMonto() != null) ? item.getCantidad() * item.getMonto() : 0.0)
                   .setHeader("Subtotal").setWidth("150px").setFlexGrow(0);

        grillaItems.setItems(listaItemsRam); 
        grillaItems.setHeight("250px");

        TextField inputDetalle = new TextField("Descripción");
        inputDetalle.setWidthFull();
        NumberField inputCantidad = new NumberField("Cant.");
        inputCantidad.setWidth("100px");
        NumberField inputMonto = new NumberField("Precio Unit.");
        inputMonto.setWidth("150px");

        Button btnAgregarItem = new Button("Añadir", VaadinIcon.PLUS.create());
        btnAgregarItem.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        btnAgregarItem.addClickListener(e -> {
            if (!inputDetalle.isEmpty() && !inputCantidad.isEmpty() && !inputMonto.isEmpty()) {
                Facturas_ItemsDTO nuevoItem = new Facturas_ItemsDTO();
                nuevoItem.setDetalle(inputDetalle.getValue());
                nuevoItem.setCantidad(inputCantidad.getValue());
                nuevoItem.setMonto(inputMonto.getValue());

                listaItemsRam.add(nuevoItem); 
                grillaItems.setItems(listaItemsRam);

                inputDetalle.clear();
                inputCantidad.clear();
                inputMonto.clear();
                inputDetalle.focus();
            } else {
                Notification.show("Complete todos los campos del ítem").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        HorizontalLayout layoutNuevoItem = new HorizontalLayout(inputDetalle, inputCantidad, inputMonto, btnAgregarItem);
        layoutNuevoItem.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        layoutNuevoItem.setWidthFull();


        if (soloLectura) {
            campoNumero.setReadOnly(true);
            campoFecha.setReadOnly(true);
            comboTercero.setReadOnly(true);
            layoutNuevoItem.setVisible(false); 
        }

        VerticalLayout cuerpoModal = new VerticalLayout();
        cuerpoModal.add(
            new H3("Datos Principales"), layoutCabecera, new Hr(), 
            new H3("Detalle de Ítems"), layoutNuevoItem, grillaItems
        );
        modal.add(cuerpoModal);

        Button botonCerrar = new Button(soloLectura ? "Volver" : "Cancelar", e -> modal.close());
        Button botonGuardar = new Button("Guardar Factura Completa", VaadinIcon.CHECK.create());
        botonGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        botonGuardar.addClickListener(e -> {
            if (binderCabecera.validate().isOk()) {
                if (listaItemsRam.isEmpty()) {
                    Notification.show("La factura debe tener al menos un ítem").addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
                
                factura.setItems(listaItemsRam);
                
                this.facturasService.crear(factura);
                
                actualizarGrillaPrincipal();
                modal.close();
                Notification.show("¡Factura emitida exitosamente!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        });

        if (soloLectura) botonGuardar.setVisible(false);

        modal.getFooter().add(botonCerrar, botonGuardar);
        modal.open();
    }
}