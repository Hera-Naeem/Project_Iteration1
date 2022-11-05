package swing;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.*;

public class CustomerGUI (){
  JFrame GUIFrame;
  JPanel GUIPanel;
  JTextField chooseScanType;
  JLabel checkoutItem;
  JButton scanBarcode, enterPLU, searchItem, weighItem;
  
  public CustomerGUI{
    GUIFrame = new JFrame("SELF-CHECKOUT STATION);
    GUIPanel = new JPanel();
    GUIPanel.setLayout(new GridLayout(0,2));
    // Adding widgets to the panel and frame
    addWidgets();
    
    //Adding the panel to the frame
    GUIFrame.getContentPane().add(GUIPanel, BorderLayout.CENTER);
    
    //Setting the GUI to stay open until it is exited
    GUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
    GUIFrame.pack();
    GUIFrame.setVisible(true);
    
  }

//Creating the widgets that will be added to the frame and panel
  private void addWidgets() {
    chooseScanType = new JTextField();
    scanBarcode = new JButton("Scan Barcode");
    weighItem = new JButton("Weigh Item");
    enterPLU = new JButton("Enter PLU");
    searchItem = new JButton("Search Item");
    
    // Integrate Barcode Scanner/Listener here?
    //scanBarcode.addbarcodeListener(e ->{
    //String item = (String)checkoutMethod("apple");

    GUIPanel.add(chooseScanType);
    GUIPanel.add(scanBarcode);
    GUIPanel.add(weighItem);
    GUIPanel.add(enterPLU);
    GUIPanel.add(searchItem);
    }
    
    private String checkoutMethod(String item) {
      return "Place item in bagging area";
    }

    public static void main(String[] args) {
      CustomerGUI customerStation = new CustomerGUI();
    }
}
  
