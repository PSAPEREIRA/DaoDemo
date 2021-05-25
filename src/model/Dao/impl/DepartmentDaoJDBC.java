package model.Dao.impl;

import db.DB;
import db.DbException;
import model.Dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO department "
                            + "(Name) "
                            + "VALUES"
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, obj.getName());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error!!!No modification was made!");
            }

        } catch (SQLException sqlException) {

            throw new DbException(sqlException.getMessage());

        } finally {

            DB.closeStatement(preparedStatement);

        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE department "
                            + "SET Name = ? "
                            + "WHERE Id = ?");

            preparedStatement.setString(1, obj.getName());
            preparedStatement.setInt(2, obj.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {

            throw new DbException(sqlException.getMessage());

        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement( "DELETE FROM department WHERE Id = ? ");

            preparedStatement.setInt(1,id);

            int rows = preparedStatement.executeUpdate();

            if(rows == 0){
                throw new DbException("There's no such entry, no modifications were made");
            }

        } catch (SQLException sqlException) {

            throw new DbException(sqlException.getMessage());

        } finally {
            DB.closeStatement(preparedStatement);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT department.* "
                            + "FROM department "
                            + "WHERE Id = ?");

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Department dep = new Department();
                dep.setId(resultSet.getInt("Id"));
                dep.setName(resultSet.getString("Name"));

                return dep;
            }

            return null;

        } catch (SQLException sqlException) {

            throw new DbException(sqlException.getMessage());

        } finally {

            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }


    @Override
    public List<Department> findAll() {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Department> departmentList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * "
                            + "FROM department "
                            + "ORDER BY Name");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Department dep = new Department();
                dep.setId(resultSet.getInt("Id"));
                dep.setName(resultSet.getString("Name"));
                departmentList.add(dep);

            }

            return departmentList;

        } catch (SQLException sqlException) {

            throw new DbException(sqlException.getMessage());

        } finally {

            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
