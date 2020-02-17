package businessLayer;

import dataAccessLayer.OrderDao;
import model.Order;
import presentation.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class OrderManipulation implements Manipulation<Order> {
    private List<Order> orderList;
    private JTable table;
    private OrderDao orderDao;
    public OrderManipulation(OrderDao orderDao)
    {
        this.orderDao=orderDao;
        List<Order> orders=orderDao.projectionQuery();
        orderList=orders;
        JTable table= orderDao.createTable(orders);
        this.table=table;
        this.table.getSelectionModel().addListSelectionListener(new TableController(this.table));
    }
    public JTable getTable(){
        return table;
    }

    public void add(Order object) {
        for(Order o:orderList){
            if(o.getId()==object.getId()){
                JOptionPane.showMessageDialog(null,"ID already exists!");
                return;
            }
        }
        orderList.add(object);
        orderDao.add(object);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{object.getId(),object.getIdClient(),object.getIdProduct()});
    }

    public void edit(int rowIndex,int columnIndex,Object oldId) {
        String field="id";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] values={model.getValueAt(rowIndex,columnIndex)};
        Object[] columns={table.getTableHeader().getColumnModel().getColumn(columnIndex).getHeaderValue()};
        Order orderToEdit=orderDao.getObjectById(oldId);
        int result=orderDao.update(values,columns,field,orderToEdit.getId());
        if(result!=-1) {
            Order editedOrder=orderDao.getObjectById(model.getValueAt(rowIndex,0));
            Order aux=null;
            for(Order o:orderList){
                if(o.getId()==orderToEdit.getId()){
                    aux=o;
                    break;
                }
            }
            orderList.set(orderList.indexOf(aux),editedOrder);
        }
        else
            JOptionPane.showMessageDialog(null,"Cannot update this row ! There is a dependency in Orders table!");

    }

    public void update(Order object, String field, Object value) {

    }

    public void findId(int id) {
        boolean found=false;
        for(Order o:orderList){
            if(o.getId()==id){
                found=true;
                JOptionPane.showMessageDialog(null,o.toString());
                break;
            }
        }
        if(found==false)
            JOptionPane.showMessageDialog(null,"No order with that id exists!");

    }

    public void remove(int index) {
        if(index==-1){
            JOptionPane.showMessageDialog(null,"Nothing was selected..");
            return;
        }
        int op=JOptionPane.showConfirmDialog(null, "Confirm removal?", "Remove item", JOptionPane.YES_NO_OPTION);
        if(op==0){
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object id=null;
            id=model.getValueAt(index,0);
            Order rmvOrder=orderDao.getObjectById(id);
            int result=orderDao.remove(rmvOrder.getId());
            if(result!=-1) {
                orderList.remove(rmvOrder);
                model.removeRow(index);
            }
            else
                JOptionPane.showMessageDialog(null,"Cannot delete this row ! There is a dependency in Orders table!");
        }
    }

    public List<Order> getList(){
        return orderList;
    }
}
