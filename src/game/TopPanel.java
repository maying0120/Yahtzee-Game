package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TopPanel extends JPanel{

    private YahtzeeFrame frame;
    private JTextField playerName;
    private JLabel hint;

    public TopPanel(YahtzeeFrame frame){
        this.setPreferredSize(new Dimension(500, 50));
        this.setLayout(null);
        JLabel jl = new JLabel("Player Name:");
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        jl.setBounds(10, 5, 200, 30);
        jl.setFont(YahtzeeFrame.font);
        playerName = new JTextField();
        playerName.setBounds(200, 5, 250, 30);
        hint = new JLabel("Hint:");
        hint.setForeground(Color.red);
        hint.setBounds(10, 35, 400, 15);
        this.add(jl);
        this.add(playerName);
        this.add(hint);
        setEdit(false);
    }

    public void setEdit(boolean edit){
        playerName.setEditable(edit);
    }

    public String getPlayerName() {
        return playerName.getText();
    }

    public void setPlayerName(String name) {
        this.playerName.setText(name);
    }

    public void setHint(String hint) {
        this.hint.setText("Hint:  " + hint);
    }
}
