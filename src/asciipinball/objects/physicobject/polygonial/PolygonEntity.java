package asciipinball.objects.physicobject.polygonial;

import asciipinball.CollisionData;
import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Line;

import java.util.ArrayList;

/**
 * Eine Abstrakte Oberklasse für Entities die aus Linien bestehen
 */
public abstract class PolygonEntity extends PhysicEntity implements Drawable {

    protected Line[] lines;

    public PolygonEntity(PlayerManager playerManager) {
        super(playerManager);
    }

    /**
     * Gibt alle Linien zurück aus der die Polygon Entity besteht
     * @return alle Linien
     */
    public Line[] getLines() {
        return lines;
    }

    /**
     * Stellt fest ob der Ball kollidiert und Speichert ggf. den Punkt der Collision sowie die konkrete Linie mit der Kollidiert wurde.
     * @param ball Ball für den die Kollisionsabfrage durchgefüht wird.
     * @return Boolscher ausdruck ob eine Collision gefunden wurde.
     */
    @Override
    protected boolean isCollided(Ball ball) {

        boolean collisionDetected = false;

        for (Line line : lines) {
            float distanceToBall;
            float distanceToA;
            float distanceToB;
            float x;
            float y;

            float mLine = line.getM();
            if (Float.isFinite(mLine)) { //If The line isn't 90° straight
                float bLine = line.getB();
                float mVertical = -1 / mLine;
                if (Float.isFinite(mVertical)) {
                    float bVertical = ball.getFuturePositionY() - mVertical * ball.getFuturePositionX();

                    x = (bLine - bVertical) / (mVertical - mLine);
                    y = mVertical * x + bVertical;
                    //calc distance Ball to perpendicular
                    distanceToBall = (float) Math.sqrt(Math.pow((x - ball.getFuturePositionX()), 2) + Math.pow((y - ball.getFuturePositionY()), 2));
                } else { //if Line is 0° horizontal
                    x = ball.getFuturePositionX();
                    y = line.getY1();

                    distanceToBall = Math.abs(y - ball.getFuturePositionY());
                }


            } else { // If the line is 90° straight (up)

                x = line.getX1();
                y = ball.getFuturePositionY();

                distanceToBall = Math.abs(ball.getFuturePositionX() - line.getX1());
            }

            distanceToA = (float) (Math.sqrt(Math.pow((x - line.getX1()), 2) +
                    Math.pow((y - line.getY1()), 2)));
            distanceToB = (float) (Math.sqrt(Math.pow((x - line.getX2()), 2) +
                    Math.pow((y - line.getY2()), 2)));


            if (distanceToBall < ball.getRadius()) {

                if (distanceToA > line.getLength() || distanceToB > line.getLength()) {

                    if (distanceToA < ball.getRadius()) {
                        distanceToBall = distanceToA;
                    } else if (distanceToB < ball.getRadius()) {
                        distanceToBall = distanceToB;
                    } else { //In this case the lot point isn't hitting the line Segment
                        continue;
                    }
                }
                collisionDetected = true;
                collisionList.add(new CollisionData(x, y, line, distanceToBall));
            }
        }

        cleanupCollisionList();

        return collisionDetected;
    }


    /**
     * Gibt ball nach Collision zurück
     * @param ball Ball der kollidiert
     * @return Ball nach kollision
     */
    @Override
    protected Ball interactWithBall(Ball ball) {

        ArrayList<Ball> ballList = new ArrayList<>();

        while (!collisionList.isEmpty()) {

            CollisionData collisionData = collisionList.get(0);
            Line collisionLine = (Line) collisionData.getCollisionShape();
            collisionList.remove(0);

            //Checks if the collisionPoint is on the line segment
            float collisionPointDistanceA = (float) (Math.sqrt(Math.pow((collisionData.getCollisionX() - collisionLine.getX1()), 2) +
                    Math.pow((collisionData.getCollisionY() - collisionLine.getY1()), 2)));
            float collisionPointDistanceB = (float) (Math.sqrt(Math.pow((collisionData.getCollisionX() - collisionLine.getX2()), 2) +
                    Math.pow((collisionData.getCollisionY() - collisionLine.getY2()), 2)));

            float finalAngle;

            //boolean bumpedIntoEdge = false;
            float collisionGradient = collisionLine.getM();

            if (collisionPointDistanceA >= collisionLine.getLength()) { //If this statement is true the ball is bumping into the side of the Line
                System.out.println("Bumped into Edge");
                //bumpedIntoEdge = true
                //finalAngle = calculateBallAngleFromGradient(ball, -1/collisionLine.getM());
                collisionGradient = -1 / ((ball.getPositionY() - collisionLine.getY2()) / (ball.getPositionX() - collisionLine.getX2())); //Has to be Y2 & X2 because y1 & x1 are FURTHER away than length
                ball.addVelocity(0.005f);
                //collisionGradient = -1/collisionLine.getM();
            } else if (collisionPointDistanceB >= collisionLine.getLength()) {
                collisionGradient = -1 / ((ball.getPositionY() - collisionLine.getY1()) / (ball.getPositionX() - collisionLine.getX1()));
                ball.addVelocity(0.005f);
            }

            finalAngle = calculateBallAngleFromGradient(ball, collisionGradient);

            System.out.println(ball.getDirection() + " -> " + finalAngle);

            Ball ballToAdd = new Ball(ball.getPositionX(), ball.getPositionY(), ball.getRadius(), finalAngle, ball.getVelocity());
            ballToAdd.jumpToFuture(2);
            ballList.add(ballToAdd);

        }

        return ball.joinBalls(ballList);

    }
}
