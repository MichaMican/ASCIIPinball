package asciipinball.levels;

import asciipinball.corelogic.players.PlayerManager;
import asciipinball.Settings;
import asciipinball.interfaces.Coverable;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.circular.Bumper;
import asciipinball.objects.physicobject.circular.JointCover;
import asciipinball.objects.physicobject.polygonial.LineWall;
import asciipinball.objects.physicobject.polygonial.RotatingCross;
import asciipinball.shapes.Line;

import java.util.ArrayList;
import java.util.List;

public class Levels {

    private PlayerManager playerManager;

    public Levels(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public PhysicEntity[] getLevel1(){
        return addJointCovers(new PhysicEntity[]{
                new LineWall(playerManager, 20,60,40,40),
                new LineWall(playerManager, 80,60,60,40),
                //new TriangleWall(0,25, (((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)), 15,0,15),
                //new TriangleWall(100,25, (((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)), 15,100,15),
                new LineWall(playerManager, 0,25,(((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)), 15),
                new LineWall(playerManager, 100,25,(((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)), 15),
                //new JointCover(playerManager, (((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)), 15),
                //new JointCover(playerManager, (((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)), 15),
                //new LineWall((((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)),15,(((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)),0),
                //new LineWall((((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)),15,(((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)),0),
                new Bumper(playerManager,Settings.WIDTH/2,Settings.HEIGHT/2, 4),
                new RotatingCross(playerManager,Settings.WIDTH/2,Settings.HEIGHT/2 + 30, 8, 0.0008f, false),
                //new Bumper((((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)), 15,2),
                //new Bumper((((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)), 15,2)
        });
    }

    public PhysicEntity[] addJointCovers(PhysicEntity[] physicEntities){
        ArrayList<PhysicEntity> arrayList = new ArrayList<>();

        for(PhysicEntity entity : physicEntities){
            if(entity != null){
                arrayList.add(entity);
                if(entity instanceof Coverable){

                    Line[] linesToCover = ((Coverable) entity).getAllLines();

                    for(Line line : linesToCover){
                        arrayList.add(new JointCover(playerManager, line.getX1(), line.getY1()));
                        arrayList.add(new JointCover(playerManager, line.getX2(), line.getY2()));
                    }


                }
            }
        }

        PhysicEntity[] returnArray = new PhysicEntity[arrayList.size()];

        for(int i = 0; i < arrayList.size(); i++){
            returnArray[i] = arrayList.get(i);
        }

        return returnArray;

    }

}
