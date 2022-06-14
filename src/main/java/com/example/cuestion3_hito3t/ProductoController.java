package com.example.cuestion3_hito3t;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ProductoController implements Initializable {
    @FXML
    private TableView table;
    @FXML
    private TableColumn tbcl_id;
    @FXML
    private TableColumn tbcl_nombre;
    @FXML
    private TableColumn tbcl_fecha;
    @FXML
    private TableColumn tbcl_unidades;
    @FXML
    private TableColumn tbcl_precio;
    @FXML
    private TableColumn tbcl_disponible;
    @FXML
    private TextField et_nombre;
    @FXML
    private ChoiceBox<Integer> combo_unidades;
    @FXML
    private ChoiceBox<Integer> combo_update;
    @FXML
    private ChoiceBox<Integer> combo_delete;
    @FXML
    private CheckBox cb_disponible;
    @FXML
    private TextField et_precio;

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> nums=FXCollections.observableArrayList();
        for(int i=0;i<11;i++){
            nums.add(i);
        }
        combo_unidades.setItems(nums);

        selectAll();
    }
    public void setCombos(ObservableList<Producto> productos){
        ObservableList<Integer> idprod=FXCollections.observableArrayList();
        for( Producto producto : productos){
            idprod.add(producto.getIdproducto());
        }
        combo_update.setItems(idprod);
        combo_delete.setItems(idprod);
    }
    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://uuxixr9ynererkgc:yvTeXgtZ9Px70bJCFeFj@bxm2upcugunkf2j84i1w-mysql.services.clever-cloud.com:3306/bxm2upcugunkf2j84i1w");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAll(){
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        try {
            connect();
            ps= con.prepareStatement("select * from productos");
            rs= ps.executeQuery();

            ResultSetMetaData rsmd=rs.getMetaData();
            int c= rsmd.getColumnCount();

            while (rs.next())
            {
                Producto producto = new Producto();
                producto.setIdproducto(rs.getInt("idproducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setFecha_envasado(rs.getString("fechaEnvasado"));
                producto.setUnidades(rs.getInt("unidades"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setDisponible(rs.getBoolean("disponible"));

                productos.add(producto);
            }
            setCombos(productos);

            table.setItems(productos);
            tbcl_id.setCellValueFactory(new PropertyValueFactory<>("idproducto"));
            tbcl_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tbcl_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_envasado"));
            tbcl_unidades.setCellValueFactory(new PropertyValueFactory<>("unidades"));
            tbcl_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            tbcl_disponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        }


        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }// cierra fun




    public void add(){
        if (!et_nombre.getText().equals("") && combo_unidades.getValue()!=null && !et_precio.getText().equals("")){

            String name=et_nombre.getText();
            int unidades=combo_unidades.getValue();
            String preciotext;
            if (et_precio.getText().contains(",")){
                preciotext=et_precio.getText().replace(",",".");;
            }
            else{
                preciotext=et_precio.getText();
            }

            Double precio=Double.parseDouble(preciotext);
            boolean disponible=cb_disponible.isSelected();
            //System.out.println(name+" "+unidades+" "+precio+" "+disponible);

                try {
                    connect();

                    SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
                    String fecha=formatFecha.format(new Date());
                    ps= con.prepareStatement("INSERT INTO `productos` (`idproducto`, `nombre`, `fechaEnvasado`, `unidades`, `precio`, `disponible`) VALUES (null,?,?,?,?,?)");
                    ps.setString(1,name);
                    ps.setString(2,fecha);
                    ps.setInt(3,unidades);
                    ps.setDouble(4,precio);
                    ps.setBoolean(5,disponible);
                    ps.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Producto añadido");


                    alert.setHeaderText("Producto añadido");
                    alert.setContentText("¡Actualizando tienda!");

                    alert.showAndWait();
                    selectAll();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

        }

        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("¡ERROR!");

            alert.setHeaderText("Introduzca los datos de nuevo");
            alert.setContentText("¡Uno o más campos sin datos!");
            alert.showAndWait();
        }

    } // fin add

    public void delete(){
        if (combo_delete.getValue()!=null){
            int id=combo_delete.getValue();
            connect();
            try {
                ps=con.prepareStatement(" DELETE FROM `productos` WHERE `productos`.`idproducto` = ?");
                ps.setInt(1,id);
                ps.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Producto eliminado");

                alert.setHeaderText("Producto eliminado");
                alert.setContentText("¡Actualizando tienda!");

                alert.showAndWait();
                selectAll();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("¡ERROR!");

            alert.setHeaderText("Intentalo de nuevo");
            alert.setContentText("¡Debes seleccionar un id de producto a eliminar!");
            alert.showAndWait();
        }

    }// fin delete

    public void update(){

        if (!et_nombre.getText().equals("") && combo_unidades.getValue()!=null && !et_precio.getText().equals("")) {
            String name = et_nombre.getText();
            int unidades = combo_unidades.getValue();
            String preciotext;
            if (et_precio.getText().contains(",")) {
                preciotext = et_precio.getText().replace(",", ".");
                ;
            } else {
                preciotext = et_precio.getText();
            }

            Double precio = Double.parseDouble(preciotext);
            boolean disponible = cb_disponible.isSelected();
            //System.out.println(name + " " + unidades + " " + precio + " " + disponible);

            int id=combo_update.getValue();

            connect();

            Date fecha=new Date();
            try {
                ps= con.prepareStatement("UPDATE `productos` SET `nombre` = ?, `fechaEnvasado` = ?, `unidades` = ?, `precio` = ?, `disponible` = ? WHERE `productos`.`idproducto` = ?;");
                ps.setString(1,name);
                ps.setString(2,fecha.toString());
                ps.setInt(3,unidades);
                ps.setDouble(4,precio);
                ps.setBoolean(5,disponible);
                ps.setInt(6,id);
                ps.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Producto actualizado");


                alert.setHeaderText("Producto actualizado");
                alert.setContentText("¡Actualizando tienda!");

                alert.showAndWait();
                selectAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("¡ERROR!");

            alert.setHeaderText("Intentalo de nuevo");
            alert.setContentText("- ¡Uno o más campos sin datos!\n- ¡Debes seleccionar un id de producto a actualizar!");
            alert.showAndWait();
        }

    } // fin update


}
