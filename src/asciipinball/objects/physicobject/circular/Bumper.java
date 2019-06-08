package asciipinball.objects.physicobject.circular;

import asciipinball.GameView;
import asciipinball.Settings;
import asciipinball.logic.Ball;
import asciipinball.shapes.Circle;

public class Bumper extends CircleEntity {

    public Bumper(float x, float y, float radius){
        score = 1;
        circles = new Circle[1];
        circles[0] = new Circle(x,y,radius);
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        Ball unmodifiedBall = super.interactWithBall(ball);
        unmodifiedBall.addVelocity(0.025f);
        return unmodifiedBall;
    }

    @Override
    public char getColor() {
        return 'R';
    }
}
