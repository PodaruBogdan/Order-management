package businessLayer;

import dataAccessLayer.ProductDao;
import model.Product;
import presentation.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class ProductManipulation implements Manipulation<Product>{
    private List<Product> productList;
    private JTable table;
    private ProductDao productDao;

    public ProductManipulation(ProductDao productDao) {
        this.productDao=productDao;
        List<Product> products=productDao.projectionQuery();
        productList=products;
        JTable table= productDao.createTable(products);
        this.table=table;
        this.table.getSelectionModel().addListSelectionListener(new TableController(this.table));
    }

    public void add(Product object) {
        for(Product p:productList){
            if(p.getId()==object.getId()){
                JOptionPane.showMessageDialog(null,"ID already exists!");
                return;
            }
        }
        productList.add(object);
        productDao.add(object);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{object.getId(),object.getName(),object.getQuantity()});
    }

    public void edit(int rowIndex,int columnIndex,Object oldId) {
        String field="id";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] values={model.getValueAt(rowIndex,columnIndex)};
        Object[] columns={table.getTableHeader().getColumnModel().getColumn(columnIndex).getHeaderValue()};
        Product productToEdit=productDao.getObjectById(oldId);
        int result=productDao.update(values,columns,field,productToEdit.getId());
        if(result!=-1 ) {
            Product editedProduct=productDao.getObjectById(model.getValueAt(rowIndex,0));
            Product aux=null;
            for(Product p:productList){
                if(p.getId()==productToEdit.getId()){
                    aux=p;
                    break;
                }
            }
            productList.set(productList.indexOf(aux),editedProduct);
        }
        else
            JOptionPane.showMessageDialog(null,"Cannot update this row ! There is a dependency in Orders table!");

    }

    public void update(Product object, String field, Object value) {
            Field f=null;
            for(Field fa:object.getClass().getDeclaredFields()){
                if(fa.getName().equals(field)){
                    f=fa;
                    break;
                }
            }
            f.setAccessible(true);
        try {
            f.set(object,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Object[] values={value};
        Object[] columns={field};
        productDao.update(values,columns,"id",object.getId());
        updateTable(object.getId(),Integer.parseInt(value.toString()));
    }

    public void findId(int id) {
        boolean found=false;
        for(Product p:productList){
            if(p.getId()==id){
                found=true;
                JOptionPane.showMessageDialog(null,p.toString());
                break;
            }
        }
        if(found==false)
            JOptionPane.showMessageDialog(null,"No product with that id exists!");
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
            Product rmvProduct=productDao.getObjectById(id);
            int result=productDao.remove(rmvProduct.getId());
            if(result!=-1) {
                model.removeRow(index);
                productList.remove(rmvProduct);
            }
            else
                JOptionPane.showMessageDialog(null,"Cannot delete this row ! There is a dependency in Orders table!");
        }
    }

    public List<Product> getList() {
        return productList;
    }

    public JTable getTable() {
        return table;
    }
    public String[] getNames(){
        String[] names=new String[productList.size()];
        for(int i=0;i<productList.size();i++)
        {
            names[i]=productList.get(i).getName();
        }
        return names;
    }

    /**<p> void updateTable(int id,int value)</p>
     * Updates a table's cell which has a specified id given  as parameter.
     * @param id - the cell's id to be searched
     * @param value - the value to be updated with
     */
    void updateTable(int id,int value){
        for(int i=0;i<table.getRowCount();i++){
            if(Integer.parseInt(table.getValueAt(i,0).toString())==id){
                table.setValueAt(value,i,2);
                break;
            }
        }
    }

}
