package presentation;

import businessLayer.ClientManipulation;
import businessLayer.OrderManipulation;
import businessLayer.ProductManipulation;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private TablePanel clientsPanel ;
    private TablePanel ordersPanel;
    private TablePanel productsPanel;

    /** <p>public MainFrame(ClientManipulation cM, OrderManipulation oM, ProductManipulation pM)</p>
     * Sets the main JFrame of the application , the first frame to be seen.
     * JFrame used to link all tables into a panel.
     * @param cM - contains clients table
     * @param oM - contains orders table
     * @param pM - contains products table
     */
    public MainFrame(ClientManipulation cM, OrderManipulation oM, ProductManipulation pM){

        JTable table1=cM.getTable();
        JTable table2=pM.getTable();
        JTable table3=oM.getTable();


        clientsPanel=new TablePanel(table1,"Clients",false);
        productsPanel= new TablePanel(table2,"Products",false);
        ordersPanel= new TablePanel(table3,"Orders",true);

        JPanel content=new JPanel();
        content.setLayout(new GridLayout(2,1));

        JPanel upperBar=new JPanel();
        JPanel lowerBar=new JPanel();

        lowerBar.setLayout(new BoxLayout(lowerBar,BoxLayout.Y_AXIS));
        upperBar.setLayout(new GridLayout(1,2));

        JScrollPane scrollPane1=new JScrollPane(clientsPanel);
        scrollPane1.setWheelScrollingEnabled(true);
        JScrollPane scrollPane2=new JScrollPane(productsPanel);
        scrollPane2.setWheelScrollingEnabled(true);
        JScrollPane scrollPane3=new JScrollPane(ordersPanel);
        scrollPane3.setWheelScrollingEnabled(true);

        upperBar.add(scrollPane1);
        upperBar.add(scrollPane2);
        lowerBar.add(scrollPane3);
        content.add(upperBar);
        content.add(lowerBar);

        this.setTitle("Order management app");
        this.setContentPane(content);
        this.setMinimumSize(new Dimension(300,300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2-this.getSize().width), (dim.height/2-this.getSize().height));
        this.pack();
    }

    public TablePanel getClientsPanel() {
        return clientsPanel;
    }

    public TablePanel getOrdersPanel() {
        return ordersPanel;
    }

    public TablePanel getProductsPanel() {
        return productsPanel;
    }
}
