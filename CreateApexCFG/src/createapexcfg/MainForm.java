/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createapexcfg;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
/**
 *
 * @author AcionAnon
 */
public class MainForm extends javax.swing.JFrame {
int x = 0;
String installPath = "";
    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        
    }
    DefaultListModel<String> dlmList = new DefaultListModel<>();
    DefaultListModel<String> dlmTEMP = new DefaultListModel<>();

    private void spBuildCFG() {
        dlmList.removeAllElements();
        lstCFG.clearSelection();
        dlmList.addElement("//AutoExec Setup EXECUTE AUTOEXEC if fails to run on start");
        dlmList.addElement("bind_US_standard \"F12\" \"exec autoexec\" 0");

        if (rdbPos.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//Show Ingame POS,Speed");
            dlmList.addElement("bind_US_standard \".\" \"toggle cl_showpos 1 0\"");
        }
        if (rdbFOV.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//FOV 120 SCALE");
            dlmList.addElement("cl_fovScale \"1.7\"");
        }
        if (rdbLagFix.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//NETWORK LAG FIXES");
            dlmList.addElement("//Should almost always be 0 to reduce artificial latency (0 syncs with server)");
            dlmList.addElement("cl_interp 0");
            dlmList.addElement("// use 1 if you have no packet loss, use 2 if you have light packet loss, use 3 or 4 if you have heavy packet loss");
            dlmList.addElement("cl_interp_ratio 1");
            dlmList.addElement("// using higher values makes no sense because apex server tick is locked at 20Hz");
            dlmList.addElement("cl_updaterate 128");
        }
        if (rdbSound.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//SOUND SETTINGS");
            dlmList.addElement("miles_occlusion_server_sounds_per_frame \"20\"");
            dlmList.addElement("miles_occlusion \"0\"");
            dlmList.addElement("miles_occlusion_force \"0\"");
            dlmList.addElement("miles_occlusion_partial \"0\"");
            dlmList.addElement("snd_mixahead \"0.05\"");
            dlmList.addElement("snd_surround_speakers 8");
            dlmList.addElement("snd_headphone_pan_exponent \"8\"");
            dlmList.addElement("snd_musicvolume \"0.8\"");
            dlmList.addElement("snd_setmixer PlayerFootsteps vol 0.09");
            dlmList.addElement("snd_setmixer GlobalFootsteps vol 5.0");
            dlmList.addElement("miles_channels 8");
        }
        if (rdbScrollAtk.isSelected() && rdbStrafe.isSelected() && !rdbScroll.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//Multiple CFG's Below");
            dlmList.addElement("exec ScrollAtk.cfg");
            dlmList.addElement("exec Strafer.cfg");
            x = 1;
        } else if (rdbScrollAtk.isSelected() && rdbScroll.isSelected() && !rdbStrafe.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//Multiple CFG's Below");
            dlmList.addElement("exec ScrollAtk.cfg");
            dlmList.addElement("exec Legend.cfg");
            x = 2;
        } else if (rdbStrafe.isSelected() && rdbScroll.isSelected() && !rdbScrollAtk.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//Multiple CFG's Below");
            dlmList.addElement("exec Legend.cfg");
            dlmList.addElement("exec Strafer.cfg");
            x = 3;
        } else if (rdbStrafe.isSelected() && rdbScroll.isSelected() && rdbScrollAtk.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//Multiple CFG's Below");
            dlmList.addElement("exec ScrollAtk.cfg");
            dlmList.addElement("exec Legend.cfg");
            dlmList.addElement("exec Strafer.cfg");
            x = 4;
        } else if (rdbStrafe.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//Strafe Backwards your MAD");
            dlmList.addElement("bind_US_standard \"mwheeldown\" \"+backward; +jump\" 0");
            dlmList.addElement("bind_US_standard \"mwheelup\" \"+forward; +jump\" 0");
        } else if (rdbScrollAtk.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//GUN GO BRRRRTTT");
            dlmList.addElement("bind_US_standard \"mwheelup\" \"+attack\" 0");
            dlmList.addElement("bind_US_standard \"mwheeldown\" \"+attack\" 0");
        } else if (rdbScroll.isSelected()) {
            dlmList.addElement(" ");
            dlmList.addElement("//Become a Legend");
            dlmList.addElement("bind_US_standard \"mwheeldown\" \"+use; +use_long; +jump\" 0");
            dlmList.addElement("bind_US_standard \"mwheelup\" \"+forward; +jump\" 0");
        }
        JOptionPane.showMessageDialog(null, "X's value is : " + x);
        lstCFG.setModel(dlmList);
    }

    private void spCreateFile(String filename) {
        File Textfile = new File(installPath + "\\" + filename);
        try {
            Textfile.createNewFile();
            lblOutput.setText("File has been Created: " + filename);
        } catch (Exception Ex) {
            JOptionPane.showMessageDialog(null, Ex);
        }
    }

    private void spCreateAuto() {
        int intIndex = lstCFG.getModel().getSize();
        String[] arrSTR = new String[intIndex];
        for (int i = 0; i < intIndex; i++) {
            arrSTR[i] = lstCFG.getModel().getElementAt(i);

            try {
                String strfilename = installPath + "\\autoexec.cfg";
                FileWriter fw = new FileWriter(strfilename, true);
                fw.write(arrSTR[i] + "\r\n");
                fw.close();
                lblOutput.setText("Your autoexec has been written");
            } catch (Exception Ex) {
                JOptionPane.showMessageDialog(null, "AutoExec failed to generate\n" + Ex);
            }
        }
    }

    private void spScrollAtk() {
        String[] arrContents = new String[5];
        arrContents[0] = "//GUN GO BRRRRTTT";
        arrContents[1] = "bind_US_standard \"mwheelup\" \"+attack\" 0";
        arrContents[2] = "bind_US_standard \"mwheeldown\" \"+attack\" 0";
        switch (x) {
            case 1:
                arrContents[3] = "bind_US_standard \"/\" \"exec Strafer.cfg\" 0";
                arrContents[4] = " ";
                break;
            case 2:
                arrContents[3] = "bind_US_standard \",\" \"exec Legend.cfg\" 0";
                arrContents[4] = " ";
                break;
            case 4:
                arrContents[3] = "bind_US_standard \"/\" \"exec Strafer.cfg\" 0";
                arrContents[4] = "bind_US_standard \",\" \"exec Legend.cfg\" 0";
                break;
        }

        for (int i = 0; i < arrContents.length; i++) {
            dlmTEMP.addElement(String.valueOf(arrContents[x]));
            try {
                String strfilename = installPath + "\\ScrollAtk.cfg";
                FileWriter fw = new FileWriter(strfilename, true);
                fw.write(arrContents[i] + "\r\n");
                fw.close();
            } catch (Exception Ex) {
                JOptionPane.showMessageDialog(null, "ScrollAtk.cfg failed to generate\n" + Ex);
            }
        }
    }

    private void spStrafer() {
        String[] arrContents = new String[5];
        arrContents[0] = "//Strafe Backwards your MAD";
        arrContents[1] = "bind_US_standard \"mwheeldown\" \"+backward; +jump\" 0";
        arrContents[2] = "bind_US_standard \"mwheelup\" \"+forward; +jump\" 0";
        switch (x) {
            case 1:
                arrContents[3] = "bind_US_standard \",\" \"exec ScrollAtk.cfg\" 0";
                arrContents[4] = " ";
                break;
            case 3:
                arrContents[3] = "bind_US_standard \",\" \"exec Legend.cfg\" 0";
                arrContents[4] = " ";
                break;
            case 4:
                arrContents[3] = "bind_US_standard \"/\" \"exec Legend.cfg\" 0";
                arrContents[4] = "bind_US_standard \",\" \"exec ScrollAtk.cfg\" 0";
                break;
        }

        for (int i = 0; i < arrContents.length; i++) {
            dlmTEMP.addElement(String.valueOf(arrContents[i]));
            try {
                String strfilename = installPath + "\\Strafer.cfg";
                FileWriter fw = new FileWriter(strfilename, true);
                fw.write(arrContents[i] + "\r\n");
                fw.close();
            } catch (Exception Ex) {
                JOptionPane.showMessageDialog(null, "Strafer.cfg failed to generate\n" + Ex);
            }
        }
    }

    private void spLegend() {
        String[] arrContents = new String[5];
        arrContents[0] = "//Become a Legend";
        arrContents[1] = "bind_US_standard \"mwheeldown\" \"+use; +use_long; +jump\" 0";
        arrContents[2] = "bind_US_standard \"mwheelup\" \"+forward; +jump\" 0";
        switch (x) {
            case 2:
                arrContents[3] = "bind_US_standard \",\" \"exec ScrollAtk.cfg\" 0";
                arrContents[4] = " ";
                break;
            case 3:
                arrContents[3] = "bind_US_standard \"/\" \"exec Strafer.cfg\" 0";
                arrContents[4] = " ";
                break;
            case 4:
                arrContents[3] = "bind_US_standard \",\" \"exec ScrollAtk.cfg\" 0";
                arrContents[4] = "bind_US_standard \"/\" \"exec Strafer.cfg\" 0";
                break;
        }
        for (int i = 0; i < arrContents.length; i++) {
            dlmTEMP.addElement(String.valueOf(arrContents[x]));
            try {
                String strfilename = installPath + "\\Legend.cfg";
                FileWriter fw = new FileWriter(strfilename, true);
                fw.write(arrContents[i] + "\r\n");
                fw.close();
            } catch (Exception Ex) {
                JOptionPane.showMessageDialog(null, "Legend.cfg failed to generate\n" + Ex);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdbFOV = new javax.swing.JRadioButton();
        rdbLagFix = new javax.swing.JRadioButton();
        rdbPos = new javax.swing.JRadioButton();
        rdbSound = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstCFG = new javax.swing.JList<>();
        btnCreate = new javax.swing.JButton();
        rdbScrollAtk = new javax.swing.JRadioButton();
        rdbScroll = new javax.swing.JRadioButton();
        rdbStrafe = new javax.swing.JRadioButton();
        lblOutput = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Apex Create My (.CFG)");

        rdbFOV.setText("FOV 120");

        rdbLagFix.setText("Lag Fix");

        rdbPos.setText("cl_Pos Show");

        rdbSound.setText("Sound Settings");

        jScrollPane1.setFont(new java.awt.Font("Arial Black", 1, 11)); // NOI18N

        lstCFG.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lstCFG.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Please create file first, select options on the left which you would like to have in your .cfg", "There might be multiple that are created just cut from your desktop to the directory", " ", "If you are using Origin go to where you installed the game:", "~\\Origin\\Apex\\cfg", " ", "If you are using Steam go to where you intalled the game:", "~\\Steam\\steamapps\\common\\Apex Legends\\cfg", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstCFG.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(lstCFG);

        btnCreate.setText("Create File / (Files)");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        rdbScrollAtk.setText("Add Scroll attack .cfg");

        rdbScroll.setText("Scroll jump(&interact) ,Tap");

        rdbStrafe.setText("Tap FWD & BACK");

        jLabel1.setText("CREATED BY ACIONANON");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rdbSound, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdbPos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdbLagFix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdbFOV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdbScrollAtk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdbScroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdbStrafe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(126, 126, 126))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdbFOV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdbLagFix)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdbPos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdbSound)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdbScrollAtk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdbScroll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbStrafe)
                        .addGap(36, 36, 36)
                        .addComponent(btnCreate)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        chooseInstallPath();
        spBuildCFG();
        spCreateFile("autoexec.cfg");
        spCreateAuto();
        switch (x) {
            case 1:
                spCreateFile("ScrollAtk.cfg");
                spCreateFile("Strafer.cfg");
                spScrollAtk();
                spStrafer();
                break;
            case 2:
                spCreateFile("ScrollAtk.cfg");
                spCreateFile("Legend.cfg");
                spScrollAtk();
                spLegend();
                break;
            case 3:
                spCreateFile("Legend.cfg");
                spCreateFile("Strafer.cfg");
                spLegend();
                spStrafer();
                break;
            case 4:
                spCreateFile("ScrollAtk.cfg");
                spCreateFile("Legend.cfg");
                spCreateFile("Strafer.cfg");
                spScrollAtk();
                spLegend();
                spStrafer();
                break;
        }
    }//GEN-LAST:event_btnCreateActionPerformed
    private void chooseInstallPath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            installPath = selectedFile.getAbsolutePath();
            lblOutput.setText("Selected path: " + installPath);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOutput;
    private javax.swing.JList<String> lstCFG;
    private javax.swing.JRadioButton rdbFOV;
    private javax.swing.JRadioButton rdbLagFix;
    private javax.swing.JRadioButton rdbPos;
    private javax.swing.JRadioButton rdbScroll;
    private javax.swing.JRadioButton rdbScrollAtk;
    private javax.swing.JRadioButton rdbSound;
    private javax.swing.JRadioButton rdbStrafe;
    // End of variables declaration//GEN-END:variables

}
