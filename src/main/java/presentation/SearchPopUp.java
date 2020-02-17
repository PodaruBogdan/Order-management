package presentation;

import businessLayer.Manipulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPopUp extends JFrame {
    private JTextField idField;
    private JButton button;
    private Manipulation man;

    /**<p>public SearchPopUp(final Manipulation man)</p>
     * Search popup for introducing the id of the object to be searched in the database and
     * in the tables of the main frame
     * @param man - the manipulation of the current table in use
     */
    public SearchPopUp(final Manipulation man){
        this.man=man;
        JPanel vertLane=new JPanel();
        vertLane.setLayout(new BoxLayout(vertLane,BoxLayout.Y_AXIS));
        idField=new JTextField(3);
        button=new JButton("Search");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id=Integer.parseInt(idField.getText());
                man.findId(id);
            }
        });

        JPanel line=new JPanel();
        JPanel line2=new JPanel();
        line2.add(button);
        line.add(new JLabel("Search for id : "));
        //line.add(Box.createRigidArea(new Dimension(10,0)));
        line.add(idField);
        vertLane.add(line);
        vertLane.add(line2);
        this.setContentPane(vertLane);
        this.setTitle("Search panel");
        this.setMinimumSize(new Dimension(300,300));
        this.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2-this.getSize().width), (dim.height/2-this.getSize().height));
        this.pack();
    }
}
