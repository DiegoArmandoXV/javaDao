package data;

import com.mycompany.app.rhIU.util.ConnectionUtil;
import domain.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    private static final String GET_FUNCIONARIOS = "SELECT * FROM funcionarios";
    private static final String CREATE_FUNCIONARIO = "INSERT INTO funcionarios (numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_FUNCIONARIO_BY_ID = "SELECT * FROM funcionarios WHERE id_funcionario = ?";
    private static final String UPDATE_FUNCIONARIO = "UPDATE funcionarios SET numero_identificacion = ?, nombres = ?, apellidos = ?, estado_civil = ?, sexo = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, rol = ? WHERE id_funcionario = ?";
    private static final String DELETE_FUNCIONARIO = "DELETE FROM funcionarios WHERE id_funcionario = ?";

    public List<Funcionario> obtenerFuncionarios() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIOS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id_funcionario"));
                funcionario.setIdentificacion(resultSet.getInt("numero_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setEstadoCivil(resultSet.getString("estado_civil"));
                funcionario.setSexo(resultSet.getString("sexo"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setTelefono(resultSet.getString("telefono"));
                funcionario.setFechaNacimiento(resultSet.getDate("fecha_nacimiento"));
                funcionario.setRol(resultSet.getString("rol"));
                funcionarios.add(funcionario);
            }

            return funcionarios;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public void crearFuncionario(Funcionario funcionario) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareCall(CREATE_FUNCIONARIO);
            preparedStatement.setInt(1, funcionario.getIdentificacion());
            preparedStatement.setString(2, funcionario.getNombres());
            preparedStatement.setString(3, funcionario.getApellidos());
            preparedStatement.setString(4, funcionario.getEstadoCivil());
            preparedStatement.setString(5, funcionario.getSexo());
            preparedStatement.setString(6, funcionario.getDireccion());
            preparedStatement.setString(7, funcionario.getTelefono());
            preparedStatement.setDate(8, funcionario.getFechaNacimiento());
            preparedStatement.setString(9, funcionario.getRol());

            preparedStatement.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public Funcionario obtenerFuncionario(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Funcionario funcionario = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIO_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id_funcionario"));
                funcionario.setIdentificacion(resultSet.getInt("numero_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setEstadoCivil(resultSet.getString("estado_civil"));
                funcionario.setSexo(resultSet.getString("sexo"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setTelefono(resultSet.getString("telefono"));
                funcionario.setFechaNacimiento(resultSet.getDate("fecha_nacimiento"));
                funcionario.setRol(resultSet.getString("rol"));
            }

            return funcionario;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public void actualizarFuncionario(int id, Funcionario funcionario) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_FUNCIONARIO);

            preparedStatement.setInt(1, funcionario.getIdentificacion());
            preparedStatement.setString(2, funcionario.getNombres());
            preparedStatement.setString(3, funcionario.getApellidos());
            preparedStatement.setString(4, funcionario.getEstadoCivil());
            preparedStatement.setString(5, funcionario.getSexo());
            preparedStatement.setString(6, funcionario.getDireccion());
            preparedStatement.setString(7, funcionario.getTelefono());
            preparedStatement.setDate(8, funcionario.getFechaNacimiento());
            preparedStatement.setString(9, funcionario.getRol());
            preparedStatement.setInt(10, id); 

            preparedStatement.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void eliminarFuncionario(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_FUNCIONARIO);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
