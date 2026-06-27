package com.utn.prog3.tp_Final.views.pagos;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.utn.prog3.tp_Final.Dto.PagosDTO;
import com.utn.prog3.tp_Final.Dto.Pagos_detalleDTO;
import com.utn.prog3.tp_Final.Dto.TercerosDTO;
import com.utn.prog3.tp_Final.Service.iservices.IPagosService;
import com.utn.prog3.tp_Final.Service.iservices.ITercerosService;
import com.utn.prog3.tp_Final.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "pagos", layout = MainLayout.class)
public class PagosView extends VerticalLayout {

    private final IPagosService pagosService;
    private final ITercerosService tercerosService;
    
    private final Grid<PagosDTO> grillaPrincipal = new Grid<>(PagosDTO.class, false);
    private final ComboBox<PagosDTO> comboSelectorRapido = new ComboBox<>("Buscador Rápido de Pagos");

    public PagosView(IPagosService pagosService, ITercerosService tercerosService) {
        this.pagosService = pagosService;
        this.tercerosService = tercerosService;

        setSizeFull();

        H2 titulo = new H2("Gestión de Pagos");
        
        Button botonNuevo = new Button("Registrar Nuevo Pago", VaadinIcon.PLUS.create());
        botonNuevo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonNuevo.addClickListener(e -> abrirModalPago(new PagosDTO(), false));

        // Combo para selección rápida
        comboSelectorRapido.setWidth("350px");
        comboSelectorRapido.setPlaceholder("Seleccione un pago...");
        comboSelectorRapido.setItemLabelGenerator(p -> "ID: " + p.getId() + " - " + (p.getTercero() != null ? p.getTercero().getNombre() : "Sin Tercero") + " ($" + p.getMonto_pago() + ")");
        comboSelectorRapido.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                abrirModalPago(e.getValue(), true);
                comboSelectorRapido.clear();
            }
        });

        HorizontalLayout barraSuperior = new HorizontalLayout(botonNuevo, comboSelectorRapido);
        barraSuperior.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        configurarGrillaPrincipal();
        actualizarGrillaPrincipal();

        add(titulo, barraSuperior, grillaPrincipal);
    }

    private void configurarGrillaPrincipal() {
        grillaPrincipal.addColumn(PagosDTO::getId).setHeader("ID Pago").setWidth("80px").setFlexGrow(0);
        grillaPrincipal.addColumn(p -> p.getTercero() != null ? p.getTercero().getNombre() : "Desconocido").setHeader("Tercero / Cliente");
        grillaPrincipal.addColumn(PagosDTO::getFecha_pago).setHeader("Fecha");
        grillaPrincipal.addColumn(PagosDTO::getModo_pago).setHeader("Modo Pago");

        // Botones de acción
        grillaPrincipal.addComponentColumn(pago -> {
            Button btnEditar = new Button(VaadinIcon.EDIT.create());
            btnEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            btnEditar.addClickListener(e -> abrirModalPago(pago, false));

            Button btnEliminar = new Button(VaadinIcon.TRASH.create());
            btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            btnEliminar.addClickListener(e -> {
                try {
                    pagosService.eliminar(pago.getId());
                    actualizarGrillaPrincipal();
                    Notification.show("Pago eliminado correctamente").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } catch (Exception ex) {
                    Notification.show("Error al eliminar: " + ex.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });

            return new HorizontalLayout(btnEditar, btnEliminar);
        }).setHeader("Acciones").setAutoWidth(true).setFlexGrow(0);

        // Doble clic para detalle (Solo Lectura)
        grillaPrincipal.addItemDoubleClickListener(e -> abrirModalPago(e.getItem(), true));
    }

    private void actualizarGrillaPrincipal() {
        // ASUMO QUE TU MÉTODO ES listarTodos(), cambialo si en el Service se llama distinto
        List<PagosDTO> lista = this.pagosService.listarTodas();
        grillaPrincipal.setItems(lista);
        comboSelectorRapido.setItems(lista);
    }

    private void abrirModalPago(PagosDTO pago, boolean soloLectura) {
        Dialog modal = new Dialog();
        modal.setWidth("900px");
        modal.setHeaderTitle(soloLectura ? "Detalle de Pago" : "Registrar / Editar Pago");

        // ==========================================
        // 1. CABECERA DEL PAGO
        // ==========================================
        ComboBox<TercerosDTO> comboTercero = new ComboBox<>("Tercero / Cliente");
        comboTercero.setItems(tercerosService.listarTerceros()); // Llenamos con los terceros de la BD
        comboTercero.setItemLabelGenerator(TercerosDTO::getNombre);
        
        DatePicker campoFecha = new DatePicker("Fecha del Pago");
        NumberField campoMonto = new NumberField("Monto Total ($)");
        
        ComboBox<String> comboModoPago = new ComboBox<>("Modo de Pago");
        comboModoPago.setItems("Efectivo", "Transferencia", "Cheque", "Tarjeta", "Otro");

        Binder<PagosDTO> binderCabecera = new Binder<>(PagosDTO.class);
        binderCabecera.forField(comboTercero).asRequired("Seleccione un tercero").bind(PagosDTO::getTercero, PagosDTO::setTercero);
        binderCabecera.forField(campoMonto).asRequired("Ingrese el monto").bind(PagosDTO::getMonto_pago, PagosDTO::setMonto_pago);
        binderCabecera.forField(comboModoPago).asRequired("Seleccione modo de pago").bind(PagosDTO::getModo_pago, PagosDTO::setModo_pago);
        
        // Conversor de LocalDate (Vaadin) a Date (Tu DTO)
        binderCabecera.forField(campoFecha)
            .withConverter(
                d -> d != null ? Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null,
                d -> d != null ? d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null
            ).asRequired("Seleccione una fecha").bind(PagosDTO::getFecha_pago, PagosDTO::setFecha_pago);

        // Usamos readBean (Cero Magia) para no modificar el objeto en vivo
        binderCabecera.readBean(pago);

        FormLayout layoutCabecera = new FormLayout(comboTercero, campoFecha, campoMonto, comboModoPago);

        // ==========================================
        // 2. DETALLE DE INSTRUMENTOS (Clonado temporal)
        // ==========================================
        List<Pagos_detalleDTO> listaDetallesRam = new ArrayList<>();
        if (pago.getDetalles() != null) {
            // Hacemos una copia para no alterar la lista conectada a Hibernate
            for (Pagos_detalleDTO d : pago.getDetalles()) {
                Pagos_detalleDTO clon = new Pagos_detalleDTO();
                clon.setId(d.getId());
                clon.setInstrument_Number(d.getInstrument_Number());
                clon.setInstrument_Date(d.getInstrument_Date());
                clon.setBanco(d.getBanco());
                clon.setPago_Realizado(d.getPago_Realizado());
                listaDetallesRam.add(clon);
            }
        }

        Grid<Pagos_detalleDTO> grillaDetalles = new Grid<>(Pagos_detalleDTO.class, false);
        grillaDetalles.addColumn(Pagos_detalleDTO::getBanco).setHeader("Banco").setFlexGrow(1);
        grillaDetalles.addColumn(Pagos_detalleDTO::getInstrument_Number).setHeader("Nº Instrumento");
        grillaDetalles.addColumn(Pagos_detalleDTO::getInstrument_Date).setHeader("Fecha Inst.");
        grillaDetalles.addColumn(d -> d.getPago_Realizado() != null && d.getPago_Realizado() ? "Sí" : "No").setHeader("¿Realizado?");
        grillaDetalles.setItems(listaDetallesRam);
        grillaDetalles.setHeight("200px");

        // Inputs para agregar nuevos detalles a la RAM
        TextField inputBanco = new TextField("Banco");
        TextField inputNroInst = new TextField("Nº Instrumento");
        DatePicker inputFechaInst = new DatePicker("Fecha");
        Checkbox inputRealizado = new Checkbox("¿Realizado?");
        
        Button btnAddDetalle = new Button("Añadir Detalle", VaadinIcon.PLUS.create());
        btnAddDetalle.addClickListener(e -> {
            if (!inputBanco.isEmpty() && !inputNroInst.isEmpty() && inputFechaInst.getValue() != null) {
                Pagos_detalleDTO nuevoDetalle = new Pagos_detalleDTO();
                nuevoDetalle.setBanco(inputBanco.getValue());
                nuevoDetalle.setInstrument_Number(inputNroInst.getValue());
                nuevoDetalle.setPago_Realizado(inputRealizado.getValue());
                
                // Conversión manual de fecha para el detalle
                nuevoDetalle.setInstrument_Date(Date.from(inputFechaInst.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                
                listaDetallesRam.add(nuevoDetalle);
                grillaDetalles.getDataProvider().refreshAll(); // Refresca la grilla
                
                // Limpiamos cajas
                inputBanco.clear(); inputNroInst.clear(); inputFechaInst.clear(); inputRealizado.clear();
            } else {
                Notification.show("Complete Banco, Nº y Fecha del instrumento.").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        HorizontalLayout layoutNuevoDetalle = new HorizontalLayout(inputBanco, inputNroInst, inputFechaInst, inputRealizado, btnAddDetalle);
        layoutNuevoDetalle.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        layoutNuevoDetalle.setWidthFull();

        // ==========================================
        // 3. MODO SOLO LECTURA (Congelamiento)
        // ==========================================
        if (soloLectura) {
            comboTercero.setReadOnly(true); campoFecha.setReadOnly(true);
            campoMonto.setReadOnly(true); comboModoPago.setReadOnly(true);
            layoutNuevoDetalle.setVisible(false); // Esconde la fila de añadir detalles
        }

        // Ensamblaje visual
        VerticalLayout cuerpoModal = new VerticalLayout(
            new H3("Cabecera del Pago"), layoutCabecera, new Hr(), 
            new H3("Detalles / Instrumentos"), layoutNuevoDetalle, grillaDetalles
        );
        modal.add(cuerpoModal);

        // ==========================================
        // 4. BOTONES Y GUARDADO (Buffered / Seguro)
        // ==========================================
        Button botonCerrar = new Button(soloLectura ? "Volver" : "Cancelar", e -> modal.close());
        
        Button botonGuardar = new Button("Guardar Pago", VaadinIcon.CHECK.create());
        botonGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonGuardar.addClickListener(e -> {
            try {
                if (binderCabecera.writeBeanIfValid(pago)) {
                    // Le inyectamos la lista de la memoria RAM al DTO final
                    pago.setDetalles(listaDetallesRam);
                    
                    boolean esNuevo = (pago.getId() == null || pago.getId() == 0);
                    if (esNuevo) {
                        pagosService.crear(pago);
                    } else {
                        pagosService.actualizar(pago.getId(), pago);
                    }
                    
                    actualizarGrillaPrincipal();
                    modal.close();
                    
                    Notification.show(esNuevo ? "¡Pago registrado exitosamente!" : "¡Pago editado correctamente!")
                                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } else {
                    Notification.show("Revise los datos requeridos de la cabecera.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } catch (Exception ex) {
                Notification.show("Error en el servidor: " + ex.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        if (soloLectura) botonGuardar.setVisible(false);

        modal.getFooter().add(botonCerrar, botonGuardar);
        modal.open();
    }
}