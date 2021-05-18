package application;

import model.Dao.DaoFactory;
import model.Dao.SellerDao;
import model.entities.Seller;



public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
    }


}
