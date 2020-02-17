package presentation;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TablePanel extends JPanel {
    private String name;
    private JTable table;
    private JLabel add;
    private JLabel rmv;
    private JLabel order=null;
    private JLabel edit;
    private JLabel search;

    /**<p>public TablePanel(JTable table,String name,boolean setOrder)</p>
     * Sets the content of a JPanel with a JTable given as its argument , a name(for identification)
     * and a speficier for rather to render or not the order button.
     * Each table from the database can use this template for the application.
     * @param table - the table to be inserted
     * @param name - the name for identification
     * @param setOrder - (true/false) specifies the visibility of the order button
     */
    public TablePanel(JTable table,String name,boolean setOrder){
        table.getTableHeader().setBackground(Color.yellow);
        table.setBackground(Color.cyan);

        this.setBackground(Color.lightGray);
        this.name=name;
        this.table=table;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10,10,10,10));

        JPanel header=new JPanel();
        header.setBackground(Color.white);
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel title=new JLabel(name);
        title.setFont(new Font("Arial",Font.ITALIC,20));
        header.add(title);

        final JPanel body=new JPanel();
        body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
        body.add(table.getTableHeader());
        body.add(table);

        JPanel footer=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.white);

        add=new JLabel(new ImageIcon("add.jpg"));
        edit=new JLabel(new ImageIcon("edit.jpg"));
        rmv=new JLabel(new ImageIcon("rmv.jpg"));
        search=new JLabel(new ImageIcon("search.jpg"));

        footer.add(search);
        footer.add(add);
        footer.add(edit);
        footer.add(rmv);

        if(setOrder==true){
            order=new JLabel(new ImageIcon("placeOrder.jpg"));
            footer.add(order);
        }

        this.add(header);
        this.add(body);
        this.add(footer);

    }
    public void setName(String newName){
        this.name=newName;
    }

    public JTable getTable() {
        return table;
    }

    public JLabel getAdd() {
        return add;
    }

    public JLabel getRmv() {
        return rmv;
    }

    public JLabel getOrder() {
        return order;
    }

    public JLabel getEdit() {
        return edit;
    }

    public JLabel getSearch(){return search;};
}
