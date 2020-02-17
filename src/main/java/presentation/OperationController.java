package presentation;

import businessLayer.ClientManipulation;
import businessLayer.OrderManipulation;
import businessLayer.ProductManipulation;

public class OperationController {
    private ClientManipulation cM;
    private OrderManipulation oM;
    private ProductManipulation pM;
    private MainFrame mainFrame;
    /**<p>public OperationController(ClientManipulation cM, OrderManipulation oM, ProductManipulation pM, MainFrame mainFrame)</p>
     * Creates all table from the database schema under JTables and associates manipulations classes
     * for each table and button controllers for each operation.
     * @param cM - client manipulation class(manipulates clients from tables during operations)
     * @param oM - order manipulation class(manipulates order from tables during operations)
     * @param pM - product manipulation class(manipulates products from tables during operations)
     * @param mainFrame - mainFrame to pass the generated tables
     */
    public OperationController(ClientManipulation cM, OrderManipulation oM, ProductManipulation pM, MainFrame mainFrame) {
        this.cM = cM;
        this.oM = oM;
        this.pM = pM;
        this.mainFrame = mainFrame;

        mainFrame.getClientsPanel().getAdd().addMouseListener(new AddController(mainFrame,cM));
        mainFrame.getClientsPanel().getRmv().addMouseListener(new RemoveController(mainFrame,cM));
        mainFrame.getClientsPanel().getEdit().addMouseListener(new EditController(mainFrame,cM));
        mainFrame.getClientsPanel().getSearch().addMouseListener(new SearchController(mainFrame,cM));

        mainFrame.getProductsPanel().getAdd().addMouseListener(new AddController(mainFrame,pM));
        mainFrame.getProductsPanel().getRmv().addMouseListener(new RemoveController(mainFrame,pM));
        mainFrame.getProductsPanel().getEdit().addMouseListener(new EditController(mainFrame,pM));
        mainFrame.getProductsPanel().getSearch().addMouseListener(new SearchController(mainFrame,pM));

        mainFrame.getOrdersPanel().getAdd().addMouseListener(new AddController(mainFrame,oM));
        mainFrame.getOrdersPanel().getRmv().addMouseListener(new RemoveController(mainFrame,oM));
        mainFrame.getOrdersPanel().getEdit().addMouseListener(new EditController(mainFrame,oM));
        mainFrame.getOrdersPanel().getOrder().addMouseListener(new OrderPlacementController(mainFrame,cM,oM,pM));
        mainFrame.getOrdersPanel().getSearch().addMouseListener(new SearchController(mainFrame,oM));
    }

}
