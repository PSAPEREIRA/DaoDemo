package application;

import model.Dao.DaoFactory;
import model.Dao.DepartmentDao;
import model.Dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(" === TEST1 : seller findById ===");

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println(" \n === TEST2 : seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> sellerList = sellerDao.findByDepartment(department);

        for (Seller objSeller : sellerList) {
            System.out.println(objSeller);
        }

        System.out.println(" \n === TEST3 : seller findAll ===");
        List<Seller> sellerListAll = sellerDao.findAll();

        for (Seller objSeller : sellerListAll) {
            System.out.println(objSeller);
        }

        System.out.println(" \n === TEST4 : INSERT seller ===");

        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);

        sellerDao.insert(newSeller);

        System.out.println("Inserted! New id = " + newSeller.getId());


        System.out.println(" \n === TEST5 : seller Update ===");
        Seller sellerToUpdate = sellerDao.findById(1);
        sellerToUpdate.setName("Martha Wayne");
        sellerDao.update(sellerToUpdate);
        System.out.println("Update completed");

        System.out.println(" \n === TEST6 : DELETE seller  ===");
        Seller toBeDeletedSeller = new Seller(null, "DeletedGuy", "deletedGmail@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(toBeDeletedSeller);
        System.out.println("Inserted! New id = " + toBeDeletedSeller.getId());

        int toBeDeletedId = toBeDeletedSeller.getId();
        sellerDao.deleteById(toBeDeletedId);
        System.out.println("Delete executed");


        Scanner sc = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 7: findById =======");
        Department dep = departmentDao.findById(1);
        System.out.println(dep);

        System.out.println("\n=== TEST 8: findAll =======");
        List<Department> list = departmentDao.findAll();
        for (Department d : list) {
            System.out.println(d);
        }

        System.out.println("\n=== TEST 9: insert =======");
        Department newDepartment = new Department(null, "Music");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id: " + newDepartment.getId());

        System.out.println("\n=== TEST 10: update =======");
        Department dep2 = departmentDao.findById(1);
        dep2.setName("Food");
        departmentDao.update(dep2);
        System.out.println("Update completed");

        System.out.println("\n=== TEST 11: delete =======");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();

    }
}

