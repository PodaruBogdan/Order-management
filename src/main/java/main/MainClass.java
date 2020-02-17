package main;

import businessLayer.ClientManipulation;
import businessLayer.OrderManipulation;
import businessLayer.ProductManipulation;
import dataAccessLayer.ClientDao;
import dataAccessLayer.OrderDao;
import dataAccessLayer.ProductDao;
import presentation.OperationController;

import presentation.MainFrame;


public class MainClass {
    public static void main(String[] args){

        ClientManipulation cM=new ClientManipulation(new ClientDao());
        ProductManipulation pM=new ProductManipulation(new ProductDao());
        OrderManipulation oM=new OrderManipulation(new OrderDao());
        MainFrame mainFrame=new MainFrame(cM,oM,pM);
        new OperationController(cM,oM,pM,mainFrame);
    }
}
