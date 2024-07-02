package EmailGenerator;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Flow;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;


public class EmailGenerator {

    //public boolean isMail  =false;
    private boolean overdue = false;
    private boolean cc = false;
    private String[] methodStrings = {"Mail","Freight","Delivery"};
    private String author ="";
    private String customer="";
    private int numInvoices = 0;
    private int numPackingSlips = 0;
    private int numPhotos = 0;
    private int numBoxes = 0;
    private int numBOL = 0;
    private JFrame mainMenu;
    private JFrame resultFrame;
    

    private String paymentStatusString = "\nMeanwhile, I have attached the payment status for your review."
                                            + " Should you have any recent payment plans, please let me know.";
    private String reciptString = " payment receipt, ";
    private String mailTemplateString = String.format("%s\n\n%s\n%s.%s\n%s\n%s",
                                    "Hi %s,",
                                    "Thank you for your confirmation. I'd like to confirm that your order has been shipped out.",
                                    "Attached please find %d invoice(s) with tracking number,%sand %d photo(s) of %d package(s) for your reference.",
                                    "%s",
                                    "Thank you for your support.\n",
                                    "Best regards,\n%s.\n"+java.time.LocalDate.now());
    private String freightTemplateString = String.format("%s\n\n%s\n%s.%s\n%s\n%s",
                                    "Hi %s,",
                                    "Thank you for your confirmation. I'd like to confirm that your order has been shipped out.",
                                    "Attached please find %d invoice(s), %d packing slip(s), %d BOL(s),%sand %d photo(s) of %d pallet(s) for your reference",
                                    "%s",
                                    "Thank you for your support.\n",
                                    "Best regards,\n%s.\n"+java.time.LocalDate.now());
    
    private String truckTemplateString = String.format("%s\n\n%s\n%s.%s\n%s\n%s",
                                    "Hi %s,",
                                    "Thank you for your confirmation. I'd like to confirm that your order has been delivered by our truck today.",
                                    "Attached please find %d invoice(s) for your reference",
                                    "%s",
                                    "Thank you for your support.\n",
                                    "Best regards,\n%s.\n"+java.time.LocalDate.now());
    
     /**
     * set up the main menu
     */
    public void mainMenuSetUp(){
        mainMenu = new JFrame();
        //set up panels
        JPanel methodPanel = new JPanel(new FlowLayout());
        JPanel checkBoxPanel = new JPanel(new FlowLayout());
        methodPanel.setSize(15,1);
        JPanel allPanel = new JPanel(new GridLayout(10,10));
        //save button
        JButton saveButton = new JButton("Save and Preview");
        //JCheckBox methodBox = new JCheckBox("Shipped by Mail?");
        JCheckBox overdueBox = new JCheckBox("Send payment status?");
        JCheckBox ccBox = new JCheckBox("paid by Credit Card?");
        //set up the labels
        JLabel methodLabel = new JLabel("Shipped by:");
        JLabel numBOLLabel = new JLabel("Number of BOLs: ");
        JLabel authorLabel = new JLabel("Author:");
        JLabel customerLabel = new JLabel("Customer:");
        JLabel numInvoiceLabel = new JLabel("Number of Invoices: ");
        JLabel numPhotosLabel = new JLabel("Number of Photos:");
        JLabel numBoxesLabel = new JLabel ("Number of packages/pallets: ");
        JLabel numPackingSlipsLabel = new JLabel("Number of Packing slips: ");
        //set up the textfields
        JTextField authorField = new JTextField("Bill",20);
        JTextField customerField = new JTextField(20);
        JTextField numInvoiceField = new JTextField("1",20);
        JTextField numPhotosField = new JTextField("1",20);
        JTextField numBoxesField = new JTextField("1",20);
        JTextField numPSField    = new JTextField("1",20);
        JTextField numBOLField    = new JTextField("1",20);
        //set up the dropdown
        JComboBox<String> methodMenu = new JComboBox<>(methodStrings);

        mainMenu.setTitle("Email Generator");
        mainMenu.setSize(500,300);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setLayout(new BorderLayout());
        //methodPanel
        methodPanel.add(methodLabel);
        methodPanel.add(methodMenu);
        //checkbox panel
        checkBoxPanel.add(overdueBox);
        checkBoxPanel.add(ccBox);

        //allPanel
        allPanel.add(methodPanel);
        //allPanel.add(methodBox);
        allPanel.add(checkBoxPanel);
        allPanel.add(authorLabel);
        allPanel.add(authorField);
        allPanel.add(customerLabel);
        allPanel.add(customerField);
        allPanel.add(numInvoiceLabel);
        allPanel.add(numInvoiceField);
        allPanel.add(numPackingSlipsLabel);
        allPanel.add(numPSField);
        allPanel.add(numBOLLabel);
        allPanel.add(numBOLField);
        allPanel.add(numPhotosLabel);
        allPanel.add(numPhotosField);
        allPanel.add(numBoxesLabel);
        allPanel.add(numBoxesField);

        mainMenu.add(allPanel,BorderLayout.WEST);
        mainMenu.add(saveButton,BorderLayout.SOUTH);
        mainMenu.setLocation(400,400);
        mainMenu.setVisible(true);

        
        //action listeners
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //isMail = methodBox.isSelected();
                String method = String.valueOf(methodMenu.getSelectedItem());
                overdue = overdueBox.isSelected();
                cc = ccBox.isSelected();
                String ccTemp = reciptString;
                String temp=paymentStatusString;
                if(!overdue){
                    temp="";
                }else{
                    temp=paymentStatusString;
                }
                if(!cc){
                    ccTemp="";
                }else{
                    ccTemp=reciptString;
                }
                author = authorField.getText();
                customer = customerField.getText();
                numBoxes = Integer.parseInt(numBoxesField.getText());
                numInvoices = Integer.parseInt(numInvoiceField.getText());
                numPhotos = Integer.parseInt(numPhotosField.getText());
                numBOL = Integer.parseInt(numBOLField.getText());
                numPackingSlips = Integer.parseInt(numPSField.getText());
                String result = "";
                //------------------abandoned code-----------------------
                /*if(isMail){
                    result = String.format(mailTemplateString,customer,
                                                numInvoices,numPhotos,numBoxes,
                                                temp,author);
                }else{
                    result = String.format(freightTemplateString,customer,
                                                numInvoices,numPackingSlips,
                                                numPhotos,numBoxes,
                                                temp,author);
                }*/
                //------------------end of abandoned code-----------------------
                switch(method){
                    case "Mail":
                        result = String.format(mailTemplateString,customer,
                                                numInvoices,ccTemp,numPhotos,numBoxes,
                                                temp,author);
                        break;
                    case "Freight":
                        result = String.format(freightTemplateString,customer,
                                                numInvoices,numPackingSlips,numBOL,ccTemp,
                                                numPhotos,numBoxes,
                                                temp,author);
                        break;
                    case "Delivery":
                        result = String.format(truckTemplateString,customer,
                                                numInvoices,
                                                temp,author);
                        break;
                    default:
                        result = String.format(mailTemplateString,customer,
                                                numInvoices,ccTemp,numPhotos,numBoxes,
                                                temp,author);
                        break;


                }
                System.out.println(author);
                resultFrameSetUp(result);

            }
        });
        mainMenu.pack();
        mainMenu.setVisible(true);
        

    }

    public void resultFrameSetUp(String result){
        resultFrame = new JFrame();
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(500,500);
        resultFrame.setLayout(new BorderLayout());
        JTextPane emailPane = new JTextPane();
        JButton copyButton = new JButton("Copy");
        emailPane.setText(result);

        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String emailString = emailPane.getText();
                StringSelection stringSelection = new StringSelection(emailString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        resultFrame.add(emailPane,BorderLayout.WEST);
        resultFrame.add(copyButton,BorderLayout.SOUTH);
        resultFrame.pack();
        resultFrame.setVisible(true);

    }
    public static void main(String[] args){
        EmailGenerator e = new EmailGenerator();
        e.mainMenuSetUp();
        System.out.println("Worked");
    }
}
