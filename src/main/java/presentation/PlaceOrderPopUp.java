package presentation;

import businessLayer.ClientManipulation;
import businessLayer.OrderManipulation;
import businessLayer.ProductManipulation;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlaceOrderPopUp extends JFrame {
    private MainFrame frame;
     String previewOrders;
     ArrayList<Integer> IDs=new ArrayList<Integer>();

    /**
     * Ask for user input.Then generate new order and custom
     * update tables and pdf file (bill).
     * @param frame
     * @param cM
     * @param pM
     * @param oM
     */
    public PlaceOrderPopUp(MainFrame frame,ClientManipulation cM,ProductManipulation pM,OrderManipulation oM) {

        for(int i=0;i<oM.getList().size();i++){
            IDs.add(oM.getList().get(i).getId());
        }

        this.frame=frame;
        JPanel vertLane=new JPanel();
        vertLane.setLayout(new BoxLayout(vertLane,BoxLayout.Y_AXIS));
        JPanel line1=new JPanel();
        JPanel line2=new JPanel();
        JPanel line3=new JPanel();
        JPanel line4=new JPanel();
        String[] names=new String[cM.getList().size()];
        for(int i=0;i<cM.getList().size();i++){
            names[i]=cM.getList().get(i).getName();
        }
        JComboBox jcb=new JComboBox(names);
        line1.add(new JLabel("Select a client : "));
        line1.add(jcb);
        line2.add(new JLabel("Select products : "));

        JPanel scrollContent=new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent,BoxLayout.Y_AXIS));
        int size=pM.getList().size();
        JPanel[] panels=new JPanel[size];
        final JCheckBox[] checkBoxes=new JCheckBox[size];
        JSpinner[] spinners=new JSpinner[size];
        for(int i=0;i<pM.getList().size();i++){
            panels[i]=new JPanel();
            panels[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
            checkBoxes[i]=new JCheckBox();
            panels[i].add(checkBoxes[i]);
            panels[i].add(new JLabel(pM.getList().get(i).getName()));
            spinners[i]=new JSpinner();
            spinners[i].setEnabled(false);
            panels[i].add(spinners[i]);
            scrollContent.add(panels[i]);
            checkBoxes[i].addActionListener(new CheckListener(spinners[i],checkBoxes[i]));
        }
        JScrollPane scrollPane=new JScrollPane(scrollContent);
        line2.add(scrollPane);
        line3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton button=new JButton("Add order");
        button.addActionListener(new ButtonListener(jcb,checkBoxes,spinners,cM,pM,oM));
        JButton button2=new JButton("Generate PDF");
        button2.addActionListener(new GeneratePDFListener());
        line4.add(button);
        line4.add(Box.createRigidArea(new Dimension(10,0)));
        line4.add(button2);
        vertLane.add(line1);
        vertLane.add(line2);
        vertLane.add(line3);
        vertLane.add(line4);

        JPanel content=new JPanel();
        content.add(vertLane);
        this.setTitle("Place some orders");
        this.setContentPane(content);
        this.setMinimumSize(new Dimension(300,300));
        this.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2-this.getSize().width), (dim.height/2-this.getSize().height));
        this.pack();
    }
    class CheckListener implements ActionListener{
        private JSpinner spinner;
        private JCheckBox checkBox;
        CheckListener(JSpinner spinner,JCheckBox checkBox){
            this.spinner=spinner;
            this.checkBox=checkBox;
        }
        public void actionPerformed(ActionEvent e) {
            if(checkBox.isSelected()){
                spinner.setEnabled(true);
            }else{
                spinner.setEnabled(false);
            }
        }
    }
    public class GeneratePDFListener implements  ActionListener{
        public void actionPerformed(ActionEvent e) {
            new PDFGenerator(previewOrders);
        }
    }
    public class ButtonListener implements ActionListener{
        private JComboBox selected;
        private JCheckBox[] checkBoxes;
        private JSpinner[] spinners;
        private ClientManipulation cM;
        private ProductManipulation pM;
        private OrderManipulation oM;
        ButtonListener(JComboBox selected,JCheckBox[] checkBoxes,JSpinner[] spinners,ClientManipulation cM,ProductManipulation pM,OrderManipulation oM){
            this.selected=selected;
            this.spinners=spinners;
            this.checkBoxes=checkBoxes;
            this.cM=cM;
            this.pM=pM;
            this.oM=oM;
        }

        /**<p>private int getNextId()</p>
         * Gets the next unique id.
         * @return int - the next valid id
         */
        private int getNextId(){
            int j=1;
            loop:while(true){
                boolean set=true;
                for(int i=0;i<IDs.size();i++){
                    if(j==IDs.get(i)){
                        set=false;
                        break ;
                    }
                }
                if(set==true){
                    IDs.add(j);
                    break loop;
                }
                else{
                    j++;
                }
            }
            return j;
        }
        public void actionPerformed(ActionEvent e) {
            for(int i=0;i<checkBoxes.length;i++){
                int quantity=Integer.parseInt(spinners[i].getValue().toString());
                if(quantity<0 || quantity>pM.getList().get(i).getQuantity()){
                    JOptionPane.showMessageDialog(null,"Under stock for product : "+pM.getList().get(i).getName()+" ! ");
                    return;
                }
            }
            Client selectedClient=cM.getList().get(selected.getSelectedIndex());
            previewOrders=""+selectedClient.getName()+" ordered :\n";
            for(int i=0;i<checkBoxes.length;i++){
                if(checkBoxes[i].isSelected()) {
                    int orderId=getNextId();
                    int clientId=selectedClient.getId();
                    Product selectedProduct=pM.getList().get(i);
                    int productId=selectedProduct.getId();
                    Order newOrder=new Order(orderId,clientId,productId);
                    oM.add(newOrder);
                    int quantity=Integer.parseInt(spinners[i].getValue().toString());
                    int newQ=pM.getList().get(i).getQuantity()-quantity;
                    pM.update(pM.getList().get(i),"quantity",newQ);
                    previewOrders+="       --- "+selectedProduct.getName()+" in quantity of "+spinners[i].getValue()+"\n";
                }
            }
        }
    }
}
