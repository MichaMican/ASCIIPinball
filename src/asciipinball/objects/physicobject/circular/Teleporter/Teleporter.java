package asciipinball.objects.physicobject.circular.Teleporter;

import asciipinball.Coordinate;
import asciipinball.Settings;
import asciipinball.objects.ball.Ball;
import asciipinball.objects.physicobject.circular.CircleEntity;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.shapes.Circle;

/**
 * Teleporter, der den Ball an den verbundenen Teleporter teleportiert
 */
public class Teleporter extends CircleEntity {

    private float x;
    private float y;
    private Teleporter destinationTeleporter;
    private static long deadTimeStart = 0; //Has to be static so all instances share the same timer

    //package private so only TeleporterFactory can instantiate a new Teleporter

    Teleporter(PlayerManager playerManager, Coordinate coordinate) {
        super(playerManager);
        x = coordinate.getX();
        y = coordinate.getY();
        circles = new Circle[1];
        circles[0] = new Circle(coordinate, 2f);
    }

    /**
     * Gibt die x-Koordinate des Teleporters zurück
     *
     * @return x-Koordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Gibt die y-Koordinate des Teleporters zurück
     *
     * @return y-Koordinate
     */
    public float getY() {
        return y;
    }

    //Package private so only the TeleporterFactory can addLinks

    void addLink(Teleporter teleporter) {
        destinationTeleporter = teleporter;
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        if ((System.currentTimeMillis() - deadTimeStart) > Settings.TELEPORTER_COOL_DOWN) {
            deadTimeStart = System.currentTimeMillis();
            playerManager.getCurrentPlayer().addToScore(1);
            return new Ball(destinationTeleporter.getX(), destinationTeleporter.getY(), ball.getRadius(), ball.getDirection(), ball.getVelocity());
        }
        return null;
    }

    @Override
    protected int getCollisionSoundId() {
        return 6;
    }

    @Override
    public char getColor() {
        return 'C';
    }
}
