package game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CenterPanel extends JPanel{

    private int[] scores = new int[13];
    private int[] tmpScores = new int[13];

    private YahtzeeFrame frame;
    private JPanel up = new JPanel();
    private JPanel lp = new JPanel();
    private Border etchedBorder = BorderFactory.createEtchedBorder();
    private Border titleBorder1 = BorderFactory.createTitledBorder(etchedBorder, "Upper Section");
    private Border titleBorder2 = BorderFactory.createTitledBorder(etchedBorder, "Lower Section");
    private JTextField[] textFields = new JTextField[18];
    private JButton[] buttons = new JButton[13];
    private String[] buttonsNames = {
            "Aces", "Twos", "Threes", "Fours", "Fives", "Sixes",
            "3 of a kind", "4 of a kind", "Full House",
            "Small Straight", "Large Straight", "Yahtzee", "Chance"
    };
    private JLabel[] labels = new JLabel[5];
    private String[] labelsNames = {
            "Score Subtotal", "Bonus", "Total of upper section:",
            "Total of lower section:", "Grand Total:"
    };


    public CenterPanel(YahtzeeFrame frame){
        this.frame = frame;
        initialData();
        up.setPreferredSize(new Dimension(395, 400));
        up.setBounds(0,0,395,400);
        lp.setPreferredSize(new Dimension(395, 400));
        lp.setBounds(0,400,395,400);
        up.setLayout(null);
        lp.setLayout(null);
        up.setBorder(titleBorder1);
        lp.setBorder(titleBorder2);

        //upperPanel
        for (int i = 0; i < 6; i++) {
            buttons[i] = new JButton(buttonsNames[i]);
            buttons[i].setBounds(10, 30 + 40*i, 160, 40);
            buttons[i].setFont(YahtzeeFrame.font);
            up.add(buttons[i]);
        }
        for (int i = 0; i < 3; i++) {
            labels[i] = new JLabel(labelsNames[i]);
            labels[i].setBounds(10, 30 + 40*(i+6), 160, 40);
            labels[i].setFont(YahtzeeFrame.font);
            up.add(labels[i]);
        }
        for (int i = 0; i < 9; i++) {
            textFields[i] = new JTextField();
            textFields[i].setBounds(190, 30 + 40*i, 200, 40);
            textFields[i].setFont(YahtzeeFrame.font);
            up.add(textFields[i]);
        }

        //lowerPanel
        for (int i = 0; i < 7; i++) {
            buttons[i+6] = new JButton(buttonsNames[i+6]);
            buttons[i+6].setBounds(15, 30 + 40*i, 160, 40);
            buttons[i+6].setFont(YahtzeeFrame.font);
            lp.add(buttons[i+6]);
        }
        for (int i = 0; i < 2; i++) {
            labels[i+3] = new JLabel(labelsNames[i+3]);
            labels[i+3].setBounds(15, 30 + 40*(i+7), 160, 40);
            labels[i+3].setFont(YahtzeeFrame.font);
            lp.add(labels[i+3]);
        }
        for (int i = 0; i < 3; i++) {
            labels[i] = new JLabel(labelsNames[i]);
            labels[i].setBounds(15, 30 + 40*i, 160, 40);
            labels[i].setFont(YahtzeeFrame.font);
            up.add(labels[i]);
        }
        for (int i = 0; i < 9; i++) {
            textFields[i+9] = new JTextField();
            textFields[i+9].setBounds(190, 30 + 40*i, 200, 40);
            textFields[i+9].setFont(YahtzeeFrame.font);
            lp.add(textFields[i+9]);
        }

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[index].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!YahtzeeFrame.CAN_SELECT){
                        return;
                    }
                    if(scores[index] == -1){
                        if(tmpScores[index] > 0){
                            scores[index] = tmpScores[index];
                            clearAndRefresh(index);
                        }else{
                            int n = JOptionPane.showConfirmDialog(null,
                                    "This move will give you 0 point. Are you sure you want to do this?", "Hint",JOptionPane.YES_NO_OPTION);
                            if(n == 0){
                                scores[index] = 0;
                                clearAndRefresh(index);
                            }
                        }
                    }
                }
            });
        }

        this.setPreferredSize(new Dimension(400, 950));
        this.setLayout(null);
        this.add(up);
        this.add(lp);
    }

    private void initialData() {
        for (int i = 0; i < scores.length; i++) {
            scores[i] = -1;
        }
    }

    public void clearAndRefresh(int index) {
        for (int i = 0; i < 6; i++) {
            tmpScores[i] = 0;
            if(scores[i] == -1){
                textFields[i].setText("");
            }
        }
        for (int i = 6; i < tmpScores.length; i++) {
            tmpScores[i] = 0;
            if(scores[i] == -1){
                textFields[i + 3].setText("");
            }
        }
        boolean upperDone = GameLogic.isUpperDone(scores);
        boolean lowerDone = GameLogic.isLowerDone(scores);
        if(index < 6){
            textFields[index].setText(String.valueOf(scores[index]));
            textFields[index].setHorizontalAlignment(SwingConstants.CENTER);
            textFields[index].setForeground(Color.black);
            if(textFields[8].getText().isEmpty() && upperDone){
                int upperScore = GameLogic.getUpperScore(scores);
                int upperBonus = GameLogic.getUpperBonus(scores);
                textFields[6].setText(String.valueOf(upperScore));
                textFields[6].setHorizontalAlignment(SwingConstants.CENTER);
                textFields[7].setText(String.valueOf(upperBonus));
                textFields[7].setHorizontalAlignment(SwingConstants.CENTER);
                textFields[8].setText(String.valueOf(upperScore + upperBonus));
                textFields[8].setHorizontalAlignment(SwingConstants.CENTER);
            }
        }else{
            textFields[index + 3].setText(String.valueOf(scores[index]));
            textFields[index + 3].setHorizontalAlignment(SwingConstants.CENTER);
            textFields[index + 3].setForeground(Color.black);
            if(textFields[16].getText().isEmpty() && lowerDone){
                int lowerScore = GameLogic.getLowerTotal(scores);
                textFields[16].setText(String.valueOf(lowerScore));
                textFields[16].setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        if(upperDone && lowerDone){
            int totalScore = GameLogic.getTotalScore(scores);
            textFields[17].setText(String.valueOf(totalScore));
            textFields[17].setHorizontalAlignment(SwingConstants.CENTER);
        }
        frame.afterSelect(buttonsNames[index]);
    }

    public void refreshScoreAfterRoll(int[] newScores){
        for (int i = 0; i < tmpScores.length; i++) {
            tmpScores[i] = newScores[i];
        }
        if(textFields[8].getText().isEmpty()){
            for (int i = 0; i < 6; i++) {
                if(scores[i] == -1){
                    if(newScores[i] != 0){
                        textFields[i].setText(String.valueOf(newScores[i]));
                        textFields[i].setForeground(Color.red);
                    }else{
                        textFields[i].setText("");
                    }
                }
            }
        }
        if(textFields[16].getText().isEmpty()){
            for (int i = 6; i < scores.length; i++) {
                if(scores[i] == -1){
                    if(newScores[i] != 0){
                        textFields[i+3].setText(String.valueOf(newScores[i]));
                        textFields[i+3].setForeground(Color.red);
                    }else{
                        textFields[i+3].setText("");
                    }
                }
            }
        }
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public void clearAll() {
        initialData();
        for (int i = 0; i < textFields.length; i++) {
            textFields[i].setFont(YahtzeeFrame.font);
            textFields[i].setText("");
            textFields[i].setForeground(Color.black);
            textFields[i].setHorizontalAlignment(SwingConstants.LEFT);
        }
    }
}
