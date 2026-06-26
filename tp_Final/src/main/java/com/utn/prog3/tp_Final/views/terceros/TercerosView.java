package com.utn.prog3.tp_Final.views.terceros;

import java.util.List;

import com.utn.prog3.tp_Final.Dto.TercerosDTO;
import com.utn.prog3.tp_Final.Service.iservices.ITercerosService;
import com.utn.prog3.tp_Final.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification; // <-- Corregido el import
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "terceros", layout = MainLayout.class)
public class TercerosView extends VerticalLayout {

    private final ITercerosService tercerosService;
    private final Grid<TercerosDTO> grilla = new Grid<>(TercerosDTO.class, false);
    
    private final ComboBox<TercerosDTO> comboSelectorRapido = new ComboBox<>("Ver Información Rápida");

    public TercerosView(ITercerosService tercerosService) {
        this.tercerosService = tercerosService;

        setSizeFull();

        H2 titulo = new H2("Gestión de Terceros");
        
        Button botonNuevo = new Button("Nuevo Tercero");
        botonNuevo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonNuevo.addClickListener(e -> abrirModalNuevo());

      
        comboSelectorRapido.setWidth("300px");
        comboSelectorRapido.setPlaceholder("Seleccione un tercero...");
        
        comboSelectorRapido.setItemLabelGenerator(TercerosDTO::getNombre);
        
        comboSelectorRapido.addValueChangeListener(evento -> {
            TercerosDTO seleccionado = evento.getValue();
            if (seleccionado != null) {
                abrirModalFormulario(seleccionado, true);
                
                comboSelectorRapido.clear(); 
            }
        });

        HorizontalLayout barraSuperior = new HorizontalLayout(botonNuevo, comboSelectorRapido);
        barraSuperior.setDefaultVerticalComponentAlignment(Alignment.BASELINE); 

        configurarGrilla();
        
        grilla.addItemDoubleClickListener(evento -> {
            TercerosDTO terceroSeleccionado = evento.getItem();
            abrirModalFormulario(terceroSeleccionado, true);
        });
        
        actualizarDatos(); 

        add(titulo, barraSuperior, grilla);
    }

    private void configurarGrilla() {
        grilla.addColumn(TercerosDTO::getId).setHeader("ID").setWidth("80px").setFlexGrow(0).setTextAlign(com.vaadin.flow.component.grid.ColumnTextAlign.CENTER);
        grilla.addColumn(TercerosDTO::getNombre).setHeader("Nombre / Razón Social");
        
        grilla.addComponentColumn(tercero -> {
            Button btnEditar = new Button(VaadinIcon.EDIT.create());
            btnEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            btnEditar.addClickListener(e -> abrirModalFormulario(tercero, false));
            
            Button btnEliminar = new Button(VaadinIcon.TRASH.create());
            btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            btnEliminar.addClickListener(e -> {
                this.tercerosService.eliminar(tercero.getId());
                actualizarDatos(); 
                Notification.show("Tercero eliminado").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            });
            
            return new HorizontalLayout(btnEditar, btnEliminar);
        }).setHeader("Acciones").setAutoWidth(true).setFlexGrow(0); 
    }

    private void actualizarDatos() {
        List<TercerosDTO> listaTerceros = this.tercerosService.listarTerceros();
        grilla.setItems(listaTerceros);
        comboSelectorRapido.setItems(listaTerceros);
    }

    private void abrirModalNuevo() {
        abrirModalFormulario(new TercerosDTO(), false);
    }

    private void abrirModalFormulario(TercerosDTO tercero, boolean soloLectura) {
        Dialog modal = new Dialog();
        
        if (soloLectura) {
            modal.setHeaderTitle("Detalle del Tercero: " + tercero.getNombre());
        } else {
            modal.setHeaderTitle("Registrar / Editar Tercero");
        }
        
        modal.setWidth("600px");

        TextField campoNombre = new TextField("Nombre / Razón Social");
        TextField campoCuilt = new TextField("CUIL / CUIT");
        TextField campoDireccion = new TextField("Dirección");
        TextField campoLocalidad = new TextField("Localidad");
        TextField campoProvincia = new TextField("Provincia");
        TextField campoTelefonos = new TextField("Teléfonos");
        
        ComboBox<String> comboSitiva = new ComboBox<>("Situación IVA");
        comboSitiva.setItems(List.of("Responsable Inscripto", "Monotributista", "Exento", "Consumidor Final"));
        comboSitiva.setWidthFull();

        ComboBox<String> comboTipoSaldo = new ComboBox<>("Tipo Saldo");
        comboTipoSaldo.setItems(List.of("Acreedor", "Deudor", "Neutro"));
        comboTipoSaldo.setWidthFull();
        
        NumberField campoSaldoApertura = new NumberField("Saldo Apertura");
        campoSaldoApertura.setValue(0.0);

        Binder<TercerosDTO> binder = new Binder<>(TercerosDTO.class);

        binder.forField(campoNombre).asRequired("El nombre es obligatorio").bind(TercerosDTO::getNombre, TercerosDTO::setNombre);
        binder.forField(campoCuilt).asRequired("El CUIT es obligatorio").bind(TercerosDTO::getCuilt, TercerosDTO::setCuilt);
        binder.forField(campoDireccion).bind(TercerosDTO::getDireccion, TercerosDTO::setDireccion);
        binder.forField(campoLocalidad).bind(TercerosDTO::getLocalidad, TercerosDTO::setLocalidad);
        binder.forField(campoProvincia).bind(TercerosDTO::getProvincia, TercerosDTO::setProvincia);
        binder.forField(campoTelefonos).bind(TercerosDTO::getTelefonos, TercerosDTO::setTelefonos);
        binder.forField(comboSitiva).bind(TercerosDTO::getSitiva, TercerosDTO::setSitiva);
        binder.forField(comboTipoSaldo).bind(TercerosDTO::getTipo_saldo, TercerosDTO::setTipo_saldo);
        binder.forField(campoSaldoApertura).bind(TercerosDTO::getSaldo_apertura, TercerosDTO::setSaldo_apertura);

        binder.setBean(tercero);

        if (soloLectura) {
            campoNombre.setReadOnly(true);
            campoCuilt.setReadOnly(true);
            campoDireccion.setReadOnly(true);
            campoLocalidad.setReadOnly(true);
            campoProvincia.setReadOnly(true);
            campoTelefonos.setReadOnly(true);
            comboSitiva.setReadOnly(true);
            comboTipoSaldo.setReadOnly(true);
            campoSaldoApertura.setReadOnly(true);
        }

        FormLayout formulario = new FormLayout();
        formulario.setColspan(campoNombre, 2); 
        formulario.add(campoNombre, campoCuilt, campoDireccion, campoLocalidad, 
                       campoProvincia, campoTelefonos, comboSitiva, comboTipoSaldo, campoSaldoApertura);

        modal.add(formulario);

        String textoBotonCerrar = soloLectura ? "Volver" : "Cancelar";
        Button botonCancelar = new Button(textoBotonCerrar, e -> modal.close());
        
        Button botonGuardar = new Button("Guardar", e -> {
            if (binder.validate().isOk()) {
                if (tercero.getId() == null || tercero.getId() == 0) {
                    this.tercerosService.crear(tercero);
                    Notification.show("¡Tercero creado!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } else {
                    this.tercerosService.actualizar(tercero.getId(), tercero);
                    Notification.show("¡Tercero actualizado!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }
                actualizarDatos();
                modal.close();
            }
        });
        botonGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        if (soloLectura) {
            botonGuardar.setVisible(false);
        }

        modal.getFooter().add(botonCancelar, botonGuardar);
        modal.open();
    }
}