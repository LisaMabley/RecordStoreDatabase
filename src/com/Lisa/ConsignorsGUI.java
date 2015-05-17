package com.Lisa;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by lisa on 5/6/15.
 */

public class ConsignorsGUI extends JPanel {

    private JPanel consignorsPanel;
    private JButton payOtherAmountButton;
    private JButton payInFullButton;
    private JList<Consignor> consignorJList;
    private JSpinner payAmountSpinner;
    private JTextArea consignorDetailsTextArea;
    private JTextArea onTheFirstOfTextArea;
    private JButton findConsignorsToPay;
    private JButton findConsignorsToNotify;
    private JButton findInactiveConsignorsButton;
    private JTextArea everyMondayNotifyAllTextArea;
    private JButton quitProgramButton;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JComboBox searchPurposeComboBox;
    private boolean detailDisplayModeNotify;

    private static DefaultListModel<Consignor> consignorListModel = new DefaultListModel<Consignor>();

    // Constructor
    public ConsignorsGUI() {

        consignorJList.setModel(consignorListModel);
        consignorJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        findConsignorsToNotify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detailDisplayModeNotify = true;
                payInFullButton.setEnabled(false);
                displayConsignorsToNotify();
            }
        });

        findConsignorsToPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detailDisplayModeNotify = false;
                displayConsignorsToPay();
            }
        });

        consignorJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!consignorJList.isSelectionEmpty()) {
                    Consignor selectedConsignor = consignorJList.getSelectedValue();
                    if (detailDisplayModeNotify) {
                        consignorDetailsTextArea.setText(selectedConsignor.getConsignorNotificationDetails());
                    } else {
                        consignorDetailsTextArea.setText(selectedConsignor.getConsignorPaymentDetails());
                        payInFullButton.setEnabled(true);
                    }
                }
            }
        });

        payInFullButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Consignor selectedConsignor = consignorJList.getSelectedValue();
                RecordStoreController.requestPayConsignorInFull(selectedConsignor);
                consignorDetailsTextArea.setText("");
                consignorListModel.removeElement(selectedConsignor);
            }
        });

        quitProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataModel.closeDbConnections();
                System.exit(0);
            }
        });
    }

    private void displayConsignorsToPay() {
        reset();
        ArrayList<Consignor> consignorsToPay = RecordStoreController.requestConsignorsToPay();
        for (Consignor consignor : consignorsToPay) {
            consignorListModel.addElement(consignor);
        }
    }

    private void displayConsignorsToNotify() {
        reset();
        ArrayList<Consignor> consignorsToNotify = RecordStoreController.requestConsignorsToNotify();
        for (Consignor consignor : consignorsToNotify) {
            consignorListModel.addElement(consignor);
        }
    }

    public void reset() {
        consignorDetailsTextArea.setText("");
        consignorListModel.removeAllElements();
    }

    public JPanel getPanel() {
        return consignorsPanel;
    }
}
