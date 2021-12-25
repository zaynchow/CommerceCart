package ui.components;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;


//citation(source link): https://stackoverflow.com/questions/27194858/jbutton-default-cursor


public class MyCustomJButton extends JButton implements MouseListener {

    private Cursor defaultCursor;
    private Cursor handCursor;

    public MyCustomJButton() {
        super();

        init();
    }

    public MyCustomJButton(Action a) {
        super(a);

        init();
    }

    public MyCustomJButton(Icon icon) {
        super(icon);

        init();
    }

    public MyCustomJButton(String text, Icon icon) {
        super(text, icon);

        init();
    }

    public MyCustomJButton(String text) {
        super(text);

        init();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setCursor(handCursor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setCursor(defaultCursor);
    }

    private void init() {
        defaultCursor = this.getCursor();
        handCursor = new Cursor(Cursor.HAND_CURSOR);

        addMouseListener(this);
    }


}
