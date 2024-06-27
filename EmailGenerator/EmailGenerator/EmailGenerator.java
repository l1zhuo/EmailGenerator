package EmailGenerator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;


public class EmailGenerator {

    public boolean isMail  =false;
    private boolean overdue = false;
    private String author ="";
    private String customer="";
    private int numInvoices = 0;
    private int numPackingSlips = 0;
    private int numPhotos = 0;
    private int numBoxes = 0;
    private JFrame mainMenu;
    private JFrame resultFrame;

    private String paymentStatusString = "Meanwhile, I have attached the payment status for your review."
                                            + "Should you have any recent payment plans, please let me know.";
    private String mailTemplateString = String.format("%s\n\n%s\n%s\n%s\n%s\n\n%s\n%s",
                                    "Hi %s,",
                                    "Hope you are doing well",
                                    "Thank you for your confirmation. I'd like to confirm that your order has been shipped out, believe you will receive it soon",
                                    "Attached please find %d invoice(s) with tracking number, and %d photo(s) of %d package(s) for your review.",
                                    "%s",
                                    "Thank you for your support.",
                                    "Best regards,\n%s.\n"+java.time.LocalDate.now());
    private String freightTemplateString = String.format("%s\n\n%s\n%s\n%s\n%s\n\n%s\n%s",
                                    "Hi %s,",
                                    "Hope you are doing well",
                                    "Thank you for your confirmation. I'd like to confirm that your order has been shipped out, believe you will receive it soon",
                                    "Attached please find %d invoice(s), %d packing slip(s), and %d photo(s) of %d pallet(s) for your review.",
                                    "%s",
                                    "Thank you for your support.",
                                    "Best regards,\n%s.\n"+java.time.LocalDate.now());
    
    
     /**
     * set up the main menu
     */
    public void mainMenuSetUp(){
        mainMenu = new JFrame();
        JPanel allPanel = new JPanel(new GridLayout(15,20));
        //save button
        JButton saveButton = new JButton("Save and Preview");
        JCheckBox methodBox = new JCheckBox("Shipped by Mail?");
        JCheckBox overdueBox = new JCheckBox("Send payment status?");
        //set up the labels
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
        mainMenu.setTitle("Email Generator");
        mainMenu.setSize(500,300);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setLayout(new BorderLayout());
        
        allPanel.add(methodBox);
        allPanel.add(overdueBox);
        allPanel.add(authorLabel);
        allPanel.add(authorField);
        allPanel.add(customerLabel);
        allPanel.add(customerField);
        allPanel.add(numInvoiceLabel);
        allPanel.add(numInvoiceField);
        allPanel.add(numPackingSlipsLabel);
        allPanel.add(numPSField);
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
                isMail = methodBox.isSelected();
                overdue = overdueBox.isSelected();
                String temp=paymentStatusString;
                if(!overdue){
                    temp="";
                }else{
                    temp=paymentStatusString;
                }
                author = authorField.getText();
                customer = customerField.getText();
                numBoxes = Integer.parseInt(numBoxesField.getText());
                numInvoices = Integer.parseInt(numInvoiceField.getText());
                numPhotos = Integer.parseInt(numPhotosField.getText());
                numPackingSlips = Integer.parseInt(numPSField.getText());
                String result = "";
                if(isMail){
                    result = String.format(mailTemplateString,customer,
                                                numInvoices,numPhotos,numBoxes,
                                                temp,author);
                }else{
                    result = String.format(freightTemplateString,customer,
                                                numInvoices,numPackingSlips,
                                                numPhotos,numBoxes,
                                                temp,author);
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
