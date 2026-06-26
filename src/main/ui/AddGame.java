package main.ui;

import main.ui.components.CoverPick;
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
        setSize(1000, 740);
        setLocationRelativeTo(parent);
        JPanel panel = new JPanel(new MigLayout("wrap 1, fill, insets 30"));
        panel.setBackground(AppTheme.BG_MODAL.getColor());



        //BTN cyan para adicionar
        RoundButton btnAddGame = new RoundButton("ADD GAME", AppTheme.PRIMARY, AppTheme.TEXT_BLACK, 15);

        //BTN Cinza para SAIR
        RoundButton btnCancel = new RoundButton("CANCEL", AppTheme.SECONDARY, AppTheme.TEXT_WHITE, 15);

        //AREA CENTRAL
        //PAINEL DIVISAO COM 2 COLUNAS
        JPanel contentPanel = new JPanel(new MigLayout("insets 0, fillx", "[][grow]"));
        contentPanel.setOpaque(false);
        //COLUNA A ESQUERDA
        CoverPick coverpic = new CoverPick(15, "+ ADD COVER");
        contentPanel.add(coverpic, "width 240!, height 340!, aligny top, gapright 40");

        // COLUNA DIREITA
        JPanel formPanel = new JPanel(new MigLayout("wrap 1, fillx, insets 0"));
        formPanel.setOpaque(false);

        RoundTextField txtGameTitle = createFormField(formPanel, "Game Title");
        RoundTextField txtGameExec = createFormField(formPanel, "Select Executable");
        RoundTextField txtSaveFolder = createFormField(formPanel, "Save Folder");
        RoundTextField txtBackupDir  = createFormField(formPanel, "BACKUP LOCATION");

        // Adiciona o formulário inteiro na segunda coluna do contentPanel
        contentPanel.add(formPanel, "growx, aligny top");

        // Joga a área central inteira para dentro do painel principal
        panel.add(contentPanel, "push, grow, aligny top");

        //COLOCAR ESSAS PORRA DE BOTAO NO CANTO INFERIOR DIREITO
        JPanel footerPanel = new JPanel(new MigLayout("insets 0"));
        footerPanel.setOpaque(false);

        footerPanel.add(btnAddGame, "pushx, align right, width 180!, height 40!, gapbottom 30, gapright 10");
        footerPanel.add(btnCancel, "width 120!, height 40!, gapright 20");

        btnCancel.addActionListener(e -> dispose());
        panel.add(footerPanel, "dock south");
        add(panel);
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

    private RoundTextField createFormField(JPanel parentPanel, String title) {
        // Cria e adiciona o Label
        JLabel lbl = new JLabel(title);
        lbl.setForeground(AppTheme.PRIMARY.getColor());
        lbl.setFont(FontUtils.importFont("/fonts/Orbitron-VariableFont_wght.ttf", 22)); // Reduzi levemente de 26 para 22 para caber perfeito
        parentPanel.add(lbl, "gaptop 10, gapbottom 5");

        // Cria e adiciona o TextField
        RoundTextField txt = new RoundTextField(15, 16);
        // "width 100%": preenche exatamente o limite do formPanel
        parentPanel.add(txt, "width 100%, height 40!");

        return txt; // Devolvemos o campo para você poder pegar o texto (.getText()) na hora de salvar o jogo
    }


}
