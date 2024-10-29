package com.mycompany.app.rh;

import controller.FuncionarioController;
import domain.Funcionario;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class AppFuncionarios {

    public static void main(String[] args) {
        FuncionarioController funcionarioController = new FuncionarioController();
        JFrame frame = new JFrame("Gestión de Funcionarios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setEditable(false);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton crearButton = new JButton("Crear Funcionario");
        JButton obtenerButton = new JButton("Obtener Funcionarios");
        JButton actualizarButton = new JButton("Actualizar Funcionario");
        JButton eliminarButton = new JButton("Eliminar Funcionario");

        crearButton.addActionListener((ActionEvent e) -> {
            mostrarVentanaCrearFuncionario(funcionarioController, outputArea);
        });

        obtenerButton.addActionListener((ActionEvent e) -> {
            mostrarFuncionarios(funcionarioController, outputArea);
        });

        actualizarButton.addActionListener((ActionEvent e) -> {
            mostrarVentanaActualizarFuncionario(funcionarioController, outputArea);
        });

        eliminarButton.addActionListener((ActionEvent e) -> {
            mostrarVentanaEliminarFuncionario(funcionarioController, outputArea);
        });

        buttonPanel.add(crearButton);
        buttonPanel.add(obtenerButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(eliminarButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void mostrarVentanaCrearFuncionario(FuncionarioController funcionarioController, JTextArea outputArea) {
        // Crear la ventana
        JFrame ventanaCrear = new JFrame("Crear Funcionario");
        ventanaCrear.setSize(400, 300);
        ventanaCrear.setLayout(new GridLayout(10, 2)); // Se ajusta al número de campos de entrada

        // Componentes para la entrada de datos
        JLabel lblIdentificacion = new JLabel("Identificación:");
        JTextField txtIdentificacion = new JTextField();

        JLabel lblNombres = new JLabel("Nombres:");
        JTextField txtNombres = new JTextField();

        JLabel lblApellidos = new JLabel("Apellidos:");
        JTextField txtApellidos = new JTextField();

        JLabel lblEstadoCivil = new JLabel("Estado Civil:");
        JTextField txtEstadoCivil = new JTextField();

        JLabel lblSexo = new JLabel("Sexo:");
        JTextField txtSexo = new JTextField();

        JLabel lblDireccion = new JLabel("Dirección:");
        JTextField txtDireccion = new JTextField();

        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField();

        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento (YYYY-MM-DD):");
        JTextField txtFechaNacimiento = new JTextField();

        JLabel lblRol = new JLabel("Rol:");
        JTextField txtRol = new JTextField();

        // Botón de confirmación
        JButton btnCrear = new JButton("Crear");
        btnCrear.addActionListener((ActionEvent e) -> {
            // Obtener datos ingresados por el usuario
            String identificacion = txtIdentificacion.getText();
            String nombres = txtNombres.getText();
            String apellidos = txtApellidos.getText();
            String estadoCivil = txtEstadoCivil.getText();
            String sexo = txtSexo.getText();
            String direccion = txtDireccion.getText();
            String telefono = txtTelefono.getText();
            String fechaNacimientoStr = txtFechaNacimiento.getText();
            String rol = txtRol.getText(); // Obtener el rol ingresado

            // Validar la fecha de nacimiento
            Date fechaNacimiento = null;
            try {
                fechaNacimiento = Date.valueOf(fechaNacimientoStr);
            } catch (IllegalArgumentException ex) {
                outputArea.setText("Error: La fecha de nacimiento debe estar en formato YYYY-MM-DD");
                return; // Detener la creación del funcionario si la fecha de nacimiento no es válida
            }

            // Lógica para crear el funcionario utilizando el controlador
            try {
                // Crear un nuevo objeto Funcionario con los datos ingresados por el usuario
                Funcionario nuevoFuncionario = new Funcionario();
                nuevoFuncionario.setIdentificacion(Integer.parseInt(identificacion));
                nuevoFuncionario.setNombres(nombres);
                nuevoFuncionario.setApellidos(apellidos);
                nuevoFuncionario.setEstadoCivil(estadoCivil);
                nuevoFuncionario.setSexo(sexo);
                nuevoFuncionario.setDireccion(direccion);
                nuevoFuncionario.setTelefono(telefono);
                nuevoFuncionario.setFechaNacimiento(fechaNacimiento);
                nuevoFuncionario.setRol(rol); // Establecer el rol

                // Llamar al método crearFuncionario con el nuevo objeto Funcionario
                funcionarioController.crearFuncionario(nuevoFuncionario);

                outputArea.setText("Funcionario creado correctamente.");
            } catch (SQLException | NumberFormatException ex) {
                outputArea.setText("Error al crear el funcionario: " + ex.getMessage());
            }

            // Cerrar la ventana después de crear el funcionario
            ventanaCrear.dispose();
        });

        // Agregar componentes a la ventana
        ventanaCrear.add(lblIdentificacion);
        ventanaCrear.add(txtIdentificacion);
        ventanaCrear.add(lblNombres);
        ventanaCrear.add(txtNombres);
        ventanaCrear.add(lblApellidos);
        ventanaCrear.add(txtApellidos);
        ventanaCrear.add(lblEstadoCivil);
        ventanaCrear.add(txtEstadoCivil);
        ventanaCrear.add(lblSexo);
        ventanaCrear.add(txtSexo);
        ventanaCrear.add(lblDireccion);
        ventanaCrear.add(txtDireccion);
        ventanaCrear.add(lblTelefono);
        ventanaCrear.add(txtTelefono);
        ventanaCrear.add(lblFechaNacimiento);
        ventanaCrear.add(txtFechaNacimiento);
        ventanaCrear.add(lblRol);
        ventanaCrear.add(txtRol);
        ventanaCrear.add(btnCrear);

        // Mostrar la ventana
        ventanaCrear.setVisible(true);
    }

//
    private static void mostrarFuncionarios(FuncionarioController funcionarioController, JTextArea outputArea) {
        try {
            List<Funcionario> funcionarios = funcionarioController.obtenerFuncionarios();
            if (funcionarios.isEmpty()) {
                outputArea.setText("No hay funcionarios\n");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Funcionario funcionario : funcionarios) {
                    sb.append("ID: ").append(funcionario.getId()).append("\n");
                    sb.append("Identificación: ").append(funcionario.getIdentificacion()).append("\n");
                    sb.append("Nombres: ").append(funcionario.getNombres()).append("\n");
                    sb.append("Apellidos: ").append(funcionario.getApellidos()).append("\n");
                    sb.append("Estado Civil: ").append(funcionario.getEstadoCivil()).append("\n");
                    sb.append("Sexo: ").append(funcionario.getSexo()).append("\n");
                    sb.append("Dirección: ").append(funcionario.getDireccion()).append("\n");
                    sb.append("Teléfono: ").append(funcionario.getTelefono()).append("\n");
                    sb.append("Fecha de Nacimiento: ").append(funcionario.getFechaNacimiento()).append("\n");
                    sb.append("Rol: ").append(funcionario.getRol()).append("\n");
                    sb.append("___________________________________________\n");
                }
                outputArea.setText(sb.toString());
            }
        } catch (SQLException ex) {
            outputArea.setText("Error al obtener funcionarios: " + ex.getMessage());
        }
    }

    private static void mostrarVentanaActualizarFuncionario(FuncionarioController funcionarioController, JTextArea outputArea) {
        // Crear la ventana
        JFrame ventanaActualizar = new JFrame("Actualizar Funcionario");
        ventanaActualizar.setSize(400, 400);
        ventanaActualizar.setLayout(new GridLayout(12, 2));

        // Componentes para la entrada de datos
        JLabel lblID = new JLabel("ID del Funcionario a actualizar:");
        JTextField txtID = new JTextField();

        JLabel lblIdentificacion = new JLabel("Identificación:");
        JTextField txtIdentificacion = new JTextField();

        JLabel lblNombres = new JLabel("Nombres:");
        JTextField txtNombres = new JTextField();

        JLabel lblApellidos = new JLabel("Apellidos:");
        JTextField txtApellidos = new JTextField();

        JLabel lblEstadoCivil = new JLabel("Estado Civil:");
        JTextField txtEstadoCivil = new JTextField();

        JLabel lblSexo = new JLabel("Sexo:");
        JTextField txtSexo = new JTextField();

        JLabel lblDireccion = new JLabel("Dirección:");
        JTextField txtDireccion = new JTextField();

        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField();

        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento (YYYY-MM-DD):");
        JTextField txtFechaNacimiento = new JTextField();

        JLabel lblRol = new JLabel("Rol:");
        JTextField txtRol = new JTextField();

        // Botón de consulta
        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener((ActionEvent e) -> {
            // Obtener ID ingresado por el usuario
            int id = Integer.parseInt(txtID.getText());

            // Lógica para obtener el funcionario a actualizar
            try {
                Funcionario funcionario = funcionarioController.obtenerFuncionario(id);
                if (funcionario != null) {
                    // Mostrar los datos actuales del funcionario en los campos correspondientes
                    txtIdentificacion.setText(String.valueOf(funcionario.getIdentificacion()));
                    txtNombres.setText(funcionario.getNombres());
                    txtApellidos.setText(funcionario.getApellidos());
                    txtEstadoCivil.setText(funcionario.getEstadoCivil());
                    txtSexo.setText(funcionario.getSexo());
                    txtDireccion.setText(funcionario.getDireccion());
                    txtTelefono.setText(funcionario.getTelefono());
                    txtFechaNacimiento.setText(String.valueOf(funcionario.getFechaNacimiento()));
                    txtRol.setText(funcionario.getRol());

                    outputArea.setText(""); // Limpiar el área de salida
                } else {
                    outputArea.setText("No se encontró ningún funcionario con el ID proporcionado.");
                }
            } catch (NumberFormatException ex) {
                outputArea.setText("Por favor, ingrese una identificación válida (número entero) para el funcionario.");
            } catch (SQLException ex) {
                outputArea.setText("Error al consultar el funcionario: " + ex.getMessage());
            }
        });

        // Botón de confirmación para actualizar
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener((ActionEvent e) -> {
            // Obtener ID ingresado por el usuario
            int id = Integer.parseInt(txtID.getText());

            // Lógica para obtener el funcionario a actualizar
            try {
                // Obtener datos ingresados por el usuario
                String identificacion = txtIdentificacion.getText();
                String nombres = txtNombres.getText();
                String apellidos = txtApellidos.getText();
                String estadoCivil = txtEstadoCivil.getText();
                String sexo = txtSexo.getText();
                String direccion = txtDireccion.getText();
                String telefono = txtTelefono.getText();
                String fechaNacimientoStr = txtFechaNacimiento.getText();
                String rol = txtRol.getText(); // Obtener el rol ingresado

                // Validar la fecha de nacimiento
                Date fechaNacimiento = null;
                try {
                    fechaNacimiento = Date.valueOf(fechaNacimientoStr);
                } catch (IllegalArgumentException ex) {
                    outputArea.setText("Error: La fecha de nacimiento debe estar en formato YYYY-MM-DD");
                    return; // Detener la creación del funcionario si la fecha de nacimiento no es válida
                }

                // Crear un nuevo objeto Funcionario con los datos ingresados por el usuario
                Funcionario nuevoFuncionario = new Funcionario();
                nuevoFuncionario.setIdentificacion(Integer.parseInt(identificacion));
                nuevoFuncionario.setNombres(nombres);
                nuevoFuncionario.setApellidos(apellidos);
                nuevoFuncionario.setEstadoCivil(estadoCivil);
                nuevoFuncionario.setSexo(sexo);
                nuevoFuncionario.setDireccion(direccion);
                nuevoFuncionario.setTelefono(telefono);
                nuevoFuncionario.setFechaNacimiento(fechaNacimiento);
                nuevoFuncionario.setRol(rol); // Establecer el rol

                // Llamar al método actualizarFuncionario con el nuevo objeto Funcionario
                funcionarioController.actualizarFuncionario(id, nuevoFuncionario);

                outputArea.setText("Funcionario actualizado correctamente.");
            } catch (NumberFormatException ex) {
                outputArea.setText("Por favor, ingrese una identificación válida (número entero) para el funcionario.");
            } catch (SQLException ex) {
                outputArea.setText("Error al actualizar el funcionario: " + ex.getMessage());
            }
        });

        // Agregar componentes a la ventana
        ventanaActualizar.add(lblID);
        ventanaActualizar.add(txtID);
        ventanaActualizar.add(lblIdentificacion);
        ventanaActualizar.add(txtIdentificacion);
        ventanaActualizar.add(lblNombres);
        ventanaActualizar.add(txtNombres);
        ventanaActualizar.add(lblApellidos);
        ventanaActualizar.add(txtApellidos);
        ventanaActualizar.add(lblEstadoCivil);
        ventanaActualizar.add(txtEstadoCivil);
        ventanaActualizar.add(lblSexo);
        ventanaActualizar.add(txtSexo);
        ventanaActualizar.add(lblDireccion);
        ventanaActualizar.add(txtDireccion);
        ventanaActualizar.add(lblTelefono);
        ventanaActualizar.add(txtTelefono);
        ventanaActualizar.add(lblFechaNacimiento);
        ventanaActualizar.add(txtFechaNacimiento);
        ventanaActualizar.add(lblRol);
        ventanaActualizar.add(txtRol);
        ventanaActualizar.add(btnConsultar);
        ventanaActualizar.add(btnActualizar);

        // Mostrar la ventana
        ventanaActualizar.setVisible(true);
    }

    private static void mostrarVentanaEliminarFuncionario(FuncionarioController funcionarioController, JTextArea outputArea) {
        // Crear la ventana
        JFrame ventanaEliminar = new JFrame("Eliminar Funcionario");
        ventanaEliminar.setSize(300, 150);
        ventanaEliminar.setLayout(new GridLayout(3, 1));

        // Componentes para la entrada de datos
        JLabel lblID = new JLabel("ID del Funcionario a eliminar:");
        JTextField txtID = new JTextField();

        // Botón de confirmación
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener((ActionEvent e) -> {
            // Obtener ID ingresado por el usuario
            int id = Integer.parseInt(txtID.getText());

            // Lógica para eliminar el funcionario utilizando el controlador
            try {
                funcionarioController.eliminarFuncionario(id);
                outputArea.setText("Funcionario eliminado correctamente.");
            } catch (SQLException ex) {
                outputArea.setText("Error al eliminar el funcionario: " + ex.getMessage());
            }

            // Cerrar la ventana después de eliminar el funcionario
            ventanaEliminar.dispose();
        });

        // Agregar componentes a la ventana
        ventanaEliminar.add(lblID);
        ventanaEliminar.add(txtID);
        ventanaEliminar.add(btnEliminar);

        // Mostrar la ventana
        ventanaEliminar.setVisible(true);
    }

}
