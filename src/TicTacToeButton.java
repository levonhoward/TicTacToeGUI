import javax.swing.*;

public class TicTacToeButton extends JButton
{
    private String placement;

    public TicTacToeButton()
    {
        super();
        this.placement = " ";
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
}
