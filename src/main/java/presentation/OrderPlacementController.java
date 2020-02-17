package presentation;

import businessLayer.ClientManipulation;
import businessLayer.OrderManipulation;
import businessLayer.ProductManipulation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderPlacementController extends MouseAdapter {
    MainFrame frame;
    ClientManipulation cM;
    OrderManipulation oM;
    ProductManipulation pM;

    /**
     * Controller for opening the popup for introducing new order.
     * @param frame
     * @param cM
     * @param oM
     * @param pM
     */
    public OrderPlacementController(MainFrame frame, ClientManipulation cM, OrderManipulation oM, ProductManipulation pM){
        this.frame=frame;
        this.cM=cM;
        this.oM=oM;
        this.pM=pM;
    }
    public void mouseClicked(MouseEvent e) {
        new PlaceOrderPopUp(frame,cM,pM,oM);
    }
}
