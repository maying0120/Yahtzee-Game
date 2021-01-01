package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * @Author 
 */
public class RightPanel extends JPanel{

    //components
    private YahtzeeFrame frame;
    private JLabel[] imageLabels;
    private Image[] diceImages;
    private ImageIcon[] imageIcons;
    private JCheckBox[] checkBoxes;

    //size
    private int[] numbers;
    private int width = 90;
    private int height = 50;
    private static int TOTAL_DICE = 5;

    public RightPanel(YahtzeeFrame frame){
        this.frame = frame;
        diceImages = new Image[6];
        for (int i = 0; i < diceImages.length; i++) {
            Image image = new ImageIcon("die"+(i+1) +".png").getImage();
            diceImages[i] = image.getScaledInstance(width,width,Image.SCALE_FAST);
        }
        imageLabels = new JLabel[TOTAL_DICE];
        imageIcons = new ImageIcon[TOTAL_DICE];
        checkBoxes = new JCheckBox[TOTAL_DICE];
        numbers = new int[TOTAL_DICE];
        for (int i = 0; i < TOTAL_DICE; i++) {
            imageLabels[i] = new JLabel();
            imageLabels[i].setBounds(5, 50 + i*(width+height),width,width);
            imageIcons[i] = new ImageIcon();
            checkBoxes[i] = new JCheckBox("Keep");
            checkBoxes[i].setBounds(5,50+i*(width+height)+width,width,height);
            checkBoxes[i].setHorizontalAlignment(SwingConstants.CENTER);
            checkBoxes[i].setFont(YahtzeeFrame.font);
        }

        this.setPreferredSize(new Dimension(width+10, 950));
        Button rollButton = new Button("Roll");
        rollButton.setFont(YahtzeeFrame.font);
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frame.getStatus() == YahtzeeFrame.GAME_START && YahtzeeFrame.CAN_ROLL){
                    for (int i = 0; i < TOTAL_DICE; i++) {
                        if(!checkBoxes[i].isSelected()){
                            numbers[i] = GameLogic.roll();//1-6
                        }
                    }
                    repaintDice();
                    frame.roll();
                }
            }
        });

        this.setLayout(null);
        for (int i = 0; i < TOTAL_DICE; i++) {
            imageLabels[i].setIcon(imageIcons[i]);
            this.add(imageLabels[i]);
            this.add(checkBoxes[i]);
        }
        rollButton.setBounds(5, 50 + (width+height)*TOTAL_DICE, width, height);
        this.add(rollButton);
        clearAll();
    }

    private void repaintDice(){
        for (int i = 0; i < TOTAL_DICE; i++) {
            imageIcons[i].setImage(diceImages[numbers[i]-1]);
            imageLabels[i].repaint();
        }
    }

    public int[] getNumbers(){
        return numbers;
    }

    public void setNumbers(int[] nums){
        for (int i = 0; i < TOTAL_DICE; i++) {
            this.numbers[i] = nums[i];
        }
        repaintDice();
    }

    public void clearAll(){
        for (int i = 0; i < TOTAL_DICE; i++) {
            numbers[i] = 1;
        }
        repaintDice();
        clearSelect();
    }

    public void clearSelect(){
        for (int i = 0; i < TOTAL_DICE; i++) {
            this.checkBoxes[i].setSelected(false);
        }
    }

}
