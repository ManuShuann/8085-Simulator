/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main_project;

/**
 *
 * @author SMART
 */
public class Main_project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new front_page().setVisible(true);
            }
        });
    }
    }

