package application;

import model.Dao.DaoFactory;
import model.Dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;


public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(" === TEST1 : seller findById ===");

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println(" \n === TEST2 : seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller>sellerList= sellerDao.findByDepartment(department);

        for (Seller objSeller: sellerList){
            System.out.println(objSeller);
        }

        System.out.println(" \n === TEST3 : seller findAll ===");
        List<Seller>sellerListAll = sellerDao.findAll();

        for (Seller objSeller : sellerListAll){
            System.out.println(objSeller);
        }

        System.out.println(" \n === TEST4 : seller Update ===");

        Seller newSeller= new Seller(null, "Greg", "greg@gmail.com",new Date(),4000.0,department);

        sellerDao.insert(newSeller);

        System.out.println("Inserted! New id = " + newSeller.getId());
        }
    }
