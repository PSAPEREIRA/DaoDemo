package model.Dao.impl;

import db.DB;
import db.DbException;
import model.Dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.* ,department.Name as 'DepName'"
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Department dep = instantiateDepartment(resultSet);

                Seller objSeller = instantiateSeller(resultSet, dep);

                return objSeller;
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
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Seller> sellerList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.* ,department.Name as 'DepName' "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE department.Id = ? "
                            + "ORDER BY Name");

            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();

            Map<Integer, Department> departmentMap = new HashMap<>();

            while (resultSet.next()) {

                Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), dep);
                }

                Seller objSeller = instantiateSeller(resultSet, dep);

                sellerList.add(objSeller);
            }

            return sellerList;

        } catch (SQLException sqlException) {

            throw new DbException(sqlException.getMessage());

        } finally {

            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department dep) throws SQLException {
        Seller objSeller = new Seller();
        objSeller.setId(resultSet.getInt("Id"));
        objSeller.setName(resultSet.getString("Name"));
        objSeller.setEmail(resultSet.getString("Email"));
        objSeller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        objSeller.setBirthDate(resultSet.getDate("BirthDate"));
        objSeller.setDepartment(dep);

        return objSeller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department dep = new Department();
        dep.setId(resultSet.getInt("DepartmentId"));
        dep.setName(resultSet.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            List<Seller> sellerList = new ArrayList<>();

            try {
                preparedStatement = connection.prepareStatement(
                        "SELECT seller.* ,department.Name as 'DepName' "
                                + "FROM seller INNER JOIN department "
                                + "ON seller.DepartmentId = department.Id "
                                + "ORDER BY Name");

                resultSet = preparedStatement.executeQuery();

                Map<Integer, Department> departmentMap = new HashMap<>();

                while (resultSet.next()) {

                    Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));

                    if (dep == null) {
                        dep = instantiateDepartment(resultSet);
                        departmentMap.put(resultSet.getInt("DepartmentId"), dep);
                    }

                    Seller objSeller = instantiateSeller(resultSet, dep);

                    sellerList.add(objSeller);
                }

                return sellerList;

            } catch (SQLException sqlException) {

                throw new DbException(sqlException.getMessage());

            } finally {

                DB.closeStatement(preparedStatement);
                DB.closeResultSet(resultSet);
            }
        }
    }

