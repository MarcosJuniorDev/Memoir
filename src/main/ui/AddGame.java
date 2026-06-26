package main.ui;

import main.ui.components.RoundTextField;
import main.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;
import main.ui.components.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddGame extends JDialog {
    public AddGame(JFrame parent){
        super(parent, "Add new Game", true);
        setUndecorated(true);

        fixedModal(parent);

        //BTN cyan para adicionar
        RoundButton btnAddGame = new RoundButton("ADD GAME", AppTheme.PRIMARY, AppTheme.TEXT_BLACK, 15);

        //BTN Cinza para SAIR
        RoundButton btnCancel = new RoundButton("CANCEL", AppTheme.SECONDARY, AppTheme.TEXT_WHITE, 15);


        setSize(1000, 740);
        setLocationRelativeTo(parent);
        JPanel panel = new JPanel(new MigLayout("wrap 1, fill, insets 20"));
        panel.setBackground(AppTheme.BG_MODAL.getColor());

        //COLOCAR ESSAS PORRA DE BOTAO NO CANTO INFERIOR DIREITO
        JPanel footerPanel = new JPanel(new MigLayout("insets 0"));
        footerPanel.setOpaque(false);

        footerPanel.add(btnAddGame, "pushx, align right, width 180!, height 40!, gapbottom 30, gapright 10");
        footerPanel.add(btnCancel, "width 120!, height 40!, gapright 20");

        btnCancel.addActionListener(e -> dispose());
        panel.add(footerPanel, "dock south");
        add(panel);

        //TODO REFATORAR ESSA PORRA PRA NAO FICAR ESSA MERDA EXTENSA E REPETITIVA
        //CAMPO NOME
        //Label
        JLabel lblGameTitle = new JLabel("Game Title");
        lblGameTitle.setForeground(AppTheme.PRIMARY.getColor());
        lblGameTitle.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        // CAMPO DO TEXTO
        RoundTextField txtGameTitle = new RoundTextField(15, 16);
        panel.add(lblGameTitle,"align right, gaptop 15, gapbottom 5,width 60%!, wrap, north, gapright 10 ");
        panel.add(txtGameTitle, "align right,gaptop 10, width 60%!, height 40!, gapbottom 20, north, gapright 10");


        //CAMPO CAMINHO EXECUTAVEL
        //Label
        JLabel lblGameExec = new JLabel("Select Executable");
        lblGameExec.setForeground(AppTheme.PRIMARY.getColor());
        lblGameExec.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        panel.add(lblGameExec, "align right, gaptop 15, gapbottom 5,width 60%!, wrap, north, gapright 10 ");

        RoundTextField txtGameExec = new RoundTextField(15, 16);
        panel.add(txtGameExec, "align right,gaptop 10, width 60%!, height 40!, gapbottom 20, north, gapright 10");

        //CAMPO CAMINHO DO SAVE FILE
        JLabel lblSaveFolder = new JLabel("Save Folder");
        lblSaveFolder.setForeground((AppTheme.PRIMARY.getColor()));
        lblSaveFolder.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        panel.add(lblSaveFolder, "align right, gaptop 15, gapbottom 5,width 60%!, wrap, north, gapright 10 ");

        RoundTextField txtSaveFolder = new RoundTextField(15, 16);
        panel.add(txtSaveFolder, "align right,gaptop 10, width 60%!, height 40!, gapbottom 20, north, gapright 10");

        //DIR ONDE O BACKUP VAI FICAR
        JLabel lblBackupDir = new JLabel("BACKUP LOCATION");
        lblBackupDir.setForeground(AppTheme.PRIMARY.getColor());
        lblBackupDir.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 26));
        panel.add(lblBackupDir, "align right, gaptop 15, gapbottom 5,width 60%!, wrap, north, gapright 10 ");

        RoundTextField txtBackupDir = new RoundTextField(15, 16);
        panel.add(txtBackupDir, "align right,gaptop 10, width 60%!, height 40!, gapbottom 20, north, gapright 10");

    }

    private void fixedModal(JFrame parent){
        //tentando deixar ela fixa relacionada a tela principal
        ComponentAdapter lockMovement = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e){
                setLocationRelativeTo(parent);
            }
        };
        parent.addComponentListener(lockMovement);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.removeComponentListener(lockMovement);
            }
        });
    }


}
