package swing;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class CustomerGUI{
  JFrame GUIFrame;
  JPanel GUIPanel;
  JLabel itemLabel, weighLabel, pluLabel, searchItemLabel, attendantLabel, cardPaymentLabel ;
  JLabel checkoutItem;
  JButton scanBarcode, enterPLU, searchItem, weighItem, callAttendant, payWithCard;
  
  public CustomerGUI (){
    GUIFrame = new JFrame("SELF-CHECKOUT STATION");
    GUIPanel = new JPanel();
    GUIPanel.setLayout(new GridLayout(0,2));
    // Adding widgets to the panel and frame
    addWidgets();
    
    //Adding the panel to the frame
    GUIFrame.getContentPane().add(GUIPanel, BorderLayout.CENTER);
    
    //Setting the GUI to stay open until it is exited
    GUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    GUIFrame.pack();
    GUIFrame.setVisible(true);
    
    //setting the frame/window size
    GUIFrame.setSize(700, 300);
  }

//Creating the widgets that will be added to the frame and panel
  private void addWidgets() {
    scanBarcode = new JButton("Scan Barcode");
    itemLabel = new JLabel();
    
    enterPLU = new JButton("Enter PLU");
    pluLabel = new JLabel();
    
    weighItem = new JButton("Weigh Item");
    weighLabel = new JLabel();
    
    searchItem = new JButton("Search Item");
    searchItemLabel = new JLabel();
    
    callAttendant = new JButton("Call Attendant");
    attendantLabel = new JLabel();
    
    payWithCard = new JButton("Pay with Card");
    cardPaymentLabel = new JLabel();
    
    //Updating the item's barcode status
    scanBarcode.addActionListener(e -> {
      String item = checkoutMethod("");
      itemLabel.setText(item + "Item Scanned");
    });
    
    //Updating the item's weight status
    weighItem.addActionListener(e -> {
      String item = checkoutMethod("");
      weighLabel.setText(item + "Item weighed successfully!");
    });
    
    //Updating the item's PLU status
    enterPLU.addActionListener(e -> {
      String item = checkoutMethod("");
      pluLabel.setText(item + "PLU number entered, and the item has been scanned successfully");
    });
    
    //updating the item's search status
    searchItem.addActionListener(e -> {
    	String item = checkoutMethod("");
    	searchItemLabel.setText(item + "Item found and scanned successfully!");
    });
    
    //updating the attendant's status
    callAttendant.addActionListener(e -> {
    	String item = checkoutMethod("");
    	attendantLabel.setText(item + "Attendant arriving shortly");
    });
    
    //updating the payment status
    payWithCard.addActionListener(e -> {
    	String item = checkoutMethod("");
    	cardPaymentLabel.setText(item + "Payment was successful!");
    });

    //Adding the buttons and labels to the panel
    GUIPanel.add(scanBarcode);
    GUIPanel.add(itemLabel);
    
    GUIPanel.add(enterPLU);
    GUIPanel.add(pluLabel);
    
    GUIPanel.add(weighItem);
    GUIPanel.add(weighLabel);
    
    GUIPanel.add(searchItem);
    GUIPanel.add(searchItemLabel);
    
    GUIPanel.add(callAttendant);
    GUIPanel.add(attendantLabel);
    
    GUIPanel.add(payWithCard);
    GUIPanel.add(cardPaymentLabel);
    }
    
  //this fucntion is used to print a message when a button is clicked
    private String checkoutMethod(String item) {
      return "";
    }

    public static void main(String[] args) {
      CustomerGUI customerStation = new CustomerGUI();
    }
}
