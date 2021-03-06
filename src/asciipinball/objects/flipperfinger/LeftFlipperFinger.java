package asciipinball.objects.flipperfinger;

import asciipinball.playersandscore.PlayerManager;
import asciipinball.shapes.Line;

/**
 * Linker Flipperfinger
 */
public class LeftFlipperFinger extends FlipperFinger {

    /**
     * Erzeugt einen Flipperfinger auf der linken Seite
     *
     * @param playerManager PlayerManager des Spiels
     * @param x             x-Koordinate des linken Flipperfingers
     * @param y             y-Koordinate des linken Flipperfingers
     * @param length        Länge des linken Flipperfingers
     * @param minAngle      Legt die Minimalhöhe des linken Flipperfingers fest
     * @param maxAngle      Legt die Maximalhöhe des linken Flipperfingers fest
     */
    public LeftFlipperFinger(PlayerManager playerManager, float x, float y, float length, float minAngle, float maxAngle) {
        super(playerManager, x, y, length, minAngle, maxAngle);
    }


    @Override
    public void generateLine(float angle) {
        float adjacentSide = ((float) Math.cos(Math.toRadians(angle))) * length;
        float oppositeSide = ((float) Math.sin(Math.toRadians(angle))) * length;
        float xRes = oppositeSide + x;
        float yRes = y - adjacentSide;
        lines[0] = new Line(x, y, xRes, yRes);
    }

    @Override
    public char getColor() {
        return 'B';
    }
}
