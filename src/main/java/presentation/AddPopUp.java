package presentation;

import businessLayer.ClientManipulation;
import businessLayer.Manipulation;
import businessLayer.ProductManipulation;
import model.Client;
import model.Order;
import model.Product;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPopUp extends JFrame {
    /**
     * Prompt to introduce new item into tables .
     * @param mainFrame
     * @param manipulation
     */
    public AddPopUp(MainFrame mainFrame, final Manipulation manipulation){

        JPanel vertLane=new JPanel();
        vertLane.setLayout(new BoxLayout(vertLane,BoxLayout.Y_AXIS));
        final JPanel[] lines=new JPanel[manipulation.getTable().getTableHeader().getColumnModel().getColumnCount()];

        for(int i=0;i<lines.length;i++)
        {
            String string=manipulation.getTable().getColumnName(i);
            lines[i]=new JPanel();
            lines[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
            lines[i].add(new JLabel(string+" : "));
            lines[i].add(new JTextField(10));
            vertLane.add(lines[i]);
        }
        JButton button=new JButton("Add");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(manipulation instanceof ClientManipulation){
                    int id=Integer.parseInt(((JTextField)lines[0].getComponent(1)).getText());
                    String name=((JTextField)lines[1].getComponent(1)).getText();
                    Client client=new Client(id,name);
                    manipulation.add(client);
                }
                else if(manipulation instanceof ProductManipulation){
                    int id=Integer.parseInt(((JTextField)lines[0].getComponent(1)).getText());
                    String name=((JTextField)lines[1].getComponent(1)).getText();;
                    int quantity=Integer.parseInt(((JTextField)lines[2].getComponent(1)).getText());
                    Product product=new Product(id,name,quantity);
                    manipulation.add(product);
                }else{
                    int id=Integer.parseInt(((JTextField)lines[0].getComponent(1)).getText());
                    int idClient=Integer.parseInt(((JTextField)lines[1].getComponent(1)).getText());
                    int idProduct=Integer.parseInt(((JTextField)lines[2].getComponent(1)).getText());
                    Order order=new Order(id,idClient,idProduct);
                    manipulation.add(order);
                }
            }
        });
        vertLane.add(Box.createRigidArea(new Dimension(0,10)));
        vertLane.add(button);
        JPanel content=new JPanel(new GridBagLayout());
        content.add(vertLane);

        this.setTitle("Add an item");
        this.setContentPane(content);
        this.setMinimumSize(new Dimension(300,300));
        this.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2-this.getSize().width), (dim.height/2-this.getSize().height));
        this.pack();
    }
}
