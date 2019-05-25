package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

import java.awt.event.KeyEvent;

public class FlipperFinger extends PolygonEntity {

    //flipperFingerDirection is true when moving up and false when moving down
    private boolean flipperFingerDirection;
    //import right class because java already has a library called Line
    private Line[] lines;
    //how fast the flipperfingers should move in ms
    private final int TIMEFORSTOPANGLE = 250;
    private float stopAngle;
    private float startAngle;
    private long startTime;
    private long stopTime;
    private long abortTimeStop;
    private long abortTimeStart;
    private float length;
    private float adjacentSide;
    private float oppositeSide;
    private float x;
    private float y;

    //startangle initializes the angle in wich the flipperfingers are created
    //stopangle where the flipperfingers should stop
    public FlipperFinger(float x, float y, float startAngle, float stopAngle, float length) {
        this.startAngle = startAngle;
        this.stopAngle = stopAngle;
        this.length = length;
        calculateLine(x, y, startAngle, length);
    }

    public Line calculateLine(float x, float y, float startAngle, float length) {
        adjacentSide = ((float) Math.cos(Math.toRadians(startAngle))) * length;
        oppositeSide = ((float) Math.sin(Math.toRadians(startAngle))) * length;
        this.x = adjacentSide - x;
        this.y = oppositeSide - y;
        return new Line(x, y, this.x, this.y);
    }

    //moves the line of the flipperfingers up
    public float calculateAngleUp(long timeSinceStart) {
        float result = (stopAngle - startAngle) / TIMEFORSTOPANGLE * timeSinceStart + startAngle;
        if (result > stopAngle) {
            result = stopAngle;
        }
        return result;
    }

    //moves the line of the flippersfingers down
    public float calculateAngleDown(long timeSinceStop) {
        float result = -(stopAngle - startAngle) / TIMEFORSTOPANGLE * timeSinceStop + stopAngle;
        if (result < startAngle) {
            result = startAngle;
            return result;
        }
        return result;
    }

    public void updateFlipperfinger(Ball ball, GameView gameView) {
        KeyEvent[] keyEvent;
        keyEvent = gameView.getKeyEvents();
        try {
            if (keyEvent.length != 0) {
                //moving the left flipperfinger with 'a' or 'left'
                if (((keyEvent[0].getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_A))
                        && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
                    flipperFingerDirection = true;
                    startTime = System.currentTimeMillis();
                    abortTimeStop = System.currentTimeMillis() - stopTime;

                } else if (((keyEvent[0].getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_A))
                        && (keyEvent[0].getID() == KeyEvent.KEY_RELEASED)) {
                    flipperFingerDirection = false;
                    stopTime = System.currentTimeMillis();
                    abortTimeStart = System.currentTimeMillis() - startTime;
                }
            }
            //moving the right flipperfinger with 'd' or 'right
            if (((keyEvent[0].getKeyCode() == KeyEvent.VK_RIGHT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_D))
                    && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
                flipperFingerDirection = true;
                startTime = System.currentTimeMillis();
                abortTimeStop = System.currentTimeMillis() - stopTime;

            } else if (((keyEvent[0].getKeyCode() == KeyEvent.VK_RIGHT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_D))
                    && (keyEvent[0].getID() == KeyEvent.KEY_RELEASED)) {
                flipperFingerDirection = false;
                stopTime = System.currentTimeMillis();
                abortTimeStart = System.currentTimeMillis() - startTime;
            }

            float angle;
            if (flipperFingerDirection) {
                angle = calculateAngleUp((TIMEFORSTOPANGLE - abortTimeStop + System.currentTimeMillis() - startTime));
            } else if (((keyEvent[0].getKeyCode() == KeyEvent.VK_RIGHT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_D))
                    && (keyEvent[0].getID() == KeyEvent.KEY_RELEASED)) {
                flipperFingerDirection = false;
                stopTime = System.currentTimeMillis();
                abortTimeStart = System.currentTimeMillis() - startTime;
            }
            angle = calculateAngleUp((TIMEFORSTOPANGLE - abortTimeStart + (System.currentTimeMillis() - stopTime)));
            //TODO boxen hinzufügen anstatt einzelne linien
        lines[0] = calculateLine(x,y,angle,length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
