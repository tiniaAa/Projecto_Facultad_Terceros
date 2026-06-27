
package com.utn.prog3.tp_Final.views.facultades;

import java.util.List;

import com.utn.prog3.tp_Final.Dto.FacultadDTO;
import com.utn.prog3.tp_Final.Service.iservices.IFacultadService;
import com.utn.prog3.tp_Final.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "facultades", layout = MainLayout.class)
public class FacultadesView extends VerticalLayout {

    private final IFacultadService facultadService;
    private final Grid<FacultadDTO> grilla = new Grid<>(FacultadDTO.class, false);
    private final ComboBox<FacultadDTO> comboSelectorRapido = new ComboBox<>("Buscar Facultad Rápido");

    public FacultadesView(IFacultadService facultadService) {
        this.facultadService = facultadService;

        setSizeFull();

        H2 titulo = new H2("Gestión de Facultades");
        
        Button botonNuevo = new Button("Nueva Facultad");
        botonNuevo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonNuevo.addClickListener(e -> abrirModalNuevo());

        comboSelectorRapido.setWidth("300px");
        comboSelectorRapido.setPlaceholder("Seleccione...");
        comboSelectorRapido.setItemLabelGenerator(FacultadDTO::getNombre);
        
        comboSelectorRapido.addValueChangeListener(evento -> {
            FacultadDTO seleccionado = evento.getValue();
            if (seleccionado != null) {
                abrirModalFormulario(seleccionado, true);
                comboSelectorRapido.clear(); 
            }
        });

        HorizontalLayout barraSuperior = new HorizontalLayout(botonNuevo, comboSelectorRapido);
        barraSuperior.setDefaultVerticalComponentAlignment(Alignment.BASELINE); 

        configurarGrilla();
        
        grilla.addItemDoubleClickListener(evento -> {
            FacultadDTO facultadSeleccionada = evento.getItem();
            abrirModalFormulario(facultadSeleccionada, true);
        });
        
        actualizarDatos(); 

        add(titulo, barraSuperior, grilla);
    }

    private void configurarGrilla() {
        grilla.addColumn(FacultadDTO::getId).setHeader("ID").setWidth("80px").setFlexGrow(0).setTextAlign(com.vaadin.flow.component.grid.ColumnTextAlign.CENTER);
        grilla.addColumn(FacultadDTO::getNombre).setHeader("Nombre de la Facultad");
        grilla.addColumn(FacultadDTO::getCuit).setHeader("CUIT");
        grilla.addColumn(FacultadDTO::getEmail).setHeader("Email");
        
        grilla.addComponentColumn(facultad -> {
            Button btnEditar = new Button(VaadinIcon.EDIT.create());
            btnEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            btnEditar.addClickListener(e -> abrirModalFormulario(facultad, false));
            
            Button btnEliminar = new Button(VaadinIcon.TRASH.create());
            btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            btnEliminar.addClickListener(e -> {
                 try {
                    this.facultadService.eliminar(facultad.getId());
                    actualizarDatos();
                    // NOTIFICACIÓN DE ELIMINACIÓN EXITOSA
                    Notification.show("Facultad eliminada correctamente")
                                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } catch (Exception ex) {
                    Notification.show("No se puede eliminar. Revise si tiene registros asociados.")
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });
            
            return new HorizontalLayout(btnEditar, btnEliminar);
        }).setHeader("Acciones").setAutoWidth(true).setFlexGrow(0); 
    }

    private void actualizarDatos() {
        List<FacultadDTO> listaFacultades = this.facultadService.listarTodas();
        grilla.setItems(listaFacultades);
        comboSelectorRapido.setItems(listaFacultades);
    }

    private void abrirModalNuevo() {
        abrirModalFormulario(new FacultadDTO(), false);
    }

    private void abrirModalFormulario(FacultadDTO facultad, boolean soloLectura) {
        Dialog modal = new Dialog();
        
        if (soloLectura) {
            modal.setHeaderTitle("Detalle de la Facultad: " + facultad.getNombre());
        } else {
            modal.setHeaderTitle("Registrar / Editar Facultad");
        }
        modal.setWidth("600px");

        // LOS COMPONENTES VISUALES
        TextField campoNombre = new TextField("Nombre de la Facultad");
        TextField campoCuit = new TextField("CUIT");
        TextField campoDireccion = new TextField("Dirección");
        TextField campoTelefono = new TextField("Teléfono");
        TextField campoEmail = new TextField("Email");
        
        // Componente especial para Integers
        IntegerField campoSucursal = new IntegerField("Número de Sucursal");
        campoSucursal.setStepButtonsVisible(true); 
        campoSucursal.setMin(0); 

        // EL BINDER
        Binder<FacultadDTO> binder = new Binder<>(FacultadDTO.class);

        binder.forField(campoNombre).asRequired("El nombre es obligatorio").bind(FacultadDTO::getNombre, FacultadDTO::setNombre);
        binder.forField(campoCuit).asRequired("El CUIT es obligatorio").bind(FacultadDTO::getCuit, FacultadDTO::setCuit);
        binder.forField(campoDireccion).bind(FacultadDTO::getDireccion, FacultadDTO::setDireccion);
        binder.forField(campoTelefono).bind(FacultadDTO::getTelefono, FacultadDTO::setTelefono);
        binder.forField(campoEmail).bind(FacultadDTO::getEmail, FacultadDTO::setEmail);
        binder.forField(campoSucursal).bind(FacultadDTO::getSucursal, FacultadDTO::setSucursal);

        // ¡CERO MAGIA! Usamos readBean en lugar de setBean. Esto carga los datos en el formulario
        // pero NO modifica el objeto 'facultad' hasta que validemos y guardemos explícitamente.
        binder.readBean(facultad);

        if (soloLectura) {
            campoNombre.setReadOnly(true);
            campoCuit.setReadOnly(true);
            campoDireccion.setReadOnly(true);
            campoTelefono.setReadOnly(true);
            campoEmail.setReadOnly(true);
            campoSucursal.setReadOnly(true);
        }

        FormLayout formulario = new FormLayout();
        formulario.setColspan(campoNombre, 2); 
        formulario.add(campoNombre, campoCuit, campoDireccion, campoTelefono, campoEmail, campoSucursal);

        modal.add(formulario);

        String textoBotonCerrar = soloLectura ? "Volver" : "Cancelar";
        Button botonCancelar = new Button(textoBotonCerrar, e -> modal.close());
        
        Button botonGuardar = new Button("Guardar", e -> {
            try {
                // writeBeanIfValid intenta pasar los datos de las cajas de texto al objeto Java.
                // Si falta algo requerido, devuelve FALSE y no hace nada más.
                if (binder.writeBeanIfValid(facultad)) {
                    
                    if (facultad.getId() == null || facultad.getId() == 0) {
                        this.facultadService.crear(facultad);
                        Notification.show("¡Facultad creada exitosamente!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    } else {
                        this.facultadService.actualizar(facultad.getId(), facultad);
                        Notification.show("¡Facultad editada correctamente!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    }
                    
                    actualizarDatos(); 
                    modal.close();
                } else {
                    // Si la validación falla (ej. faltó el nombre), le avisamos visualmente
                    Notification.show("Por favor, revise los errores en el formulario.")
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } catch (Exception ex) {
                // Si el backend explota al actualizar, atrapamos el error para que la pantalla no se quede muda
                Notification.show("Error en el servidor al guardar: " + ex.getMessage())
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
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