package swing;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.*;

public class CustomerGUI{
  JFrame GUIFrame;
  JPanel GUIPanel;
  JLabel itemLabel, weighLabel, pluLabel;
  JLabel checkoutItem;
  JButton scanBarcode, enterPLU, searchItem, weighItem, callAttendant;
  
  public CustomerGUI (){
    GUIFrame = new JFrame("SELF-CHECKOUT STATION");
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
    scanBarcode = new JButton("Scan Barcode");
    itemLabel = new JLabel();
    
    enterPLU = new JButton("Enter PLU");
    pluLabel = new JLabel();
    
    weighItem = new JButton("Weigh Item");
    weighLabel = new JLabel();
    
   //For Hera --> the same as above but for search item, call attendant and pay with card
    
    searchItem = new JButton("Search Item");
    callAttendant = new JButton("Call Attendant");
    
    
    //Updating the item's barcode status
    scanBarcode.addActionListener(e -> {
      String item = checkoutMethod("");
      itemLabel.setText(item + "Item Scanned");
    });
    
    //Updating the item's weight status
    weighItem.addActionListener(e -> {
      String item = checkoutMethod("Place item on scale");
      weighLabel.setText(item + "Item Successfully Weighed");
    });
    
    //Updating the item's PLU status
    enterPLU.addActionListener(e -> {
      String item = checkoutMethod("");
      pluLabel.setText(item + "Item Scanned");
    });
    
    //For Hera --> the same as above for search item, call attendent, pay with credit card
    
    
    //Adding the buttons and labels to the pannel
    GUIPanel.add(scanBarcode);
    GUIPanel.add(itemLabel);
    
    GUIPanel.add(enterPLU);
    GUIPanel.add(pluLabel);
    
    GUIPanel.add(weighItem);
    GUIPanel.add(weighLabel);
    
    //For Hera
    GUIPanel.add(searchItem);
    GUIPanel.add(callAttendant);
    }
    
    private String checkoutMethod(String item) {
      return "";
    }

    public static void main(String[] args) {
      CustomerGUI customerStation = new CustomerGUI();
    }
}
  
