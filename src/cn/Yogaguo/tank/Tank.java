package cn.Yogaguo.tank;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@SuppressWarnings("all")
public class Tank {
    private int x, y;
    private Direct dir;
    public static final int SPEED = 5;
    private boolean bL, bR, bU, bD;
    private boolean moving = true;
    private boolean live = true;
    private Group group;
    private Bullet bullet;
    private int height;
    private int width;
    int oldX,oldY;
    private Random r = new Random();
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Tank(int x, int y, Direct dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.height = ResourceMgr.badTankD.getHeight();
        this.width = ResourceMgr.badTankD.getWidth();
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        if (!this.isLive()) {
            return;
        }
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.badTankL, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.badTankR, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.badTankU, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y, null);
                break;
            default:
        }

        move();

    }
    private void move() {
        if (!moving) {
            return;
        }
        oldX = x;
        oldY = y;
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
            default:
        }
        boundsCheck();
        randomDir();
        if(r.nextInt(100) > 90) {
            fire();
        }
    }
    private void boundsCheck() {
        if (x < 0 || y < 30 || x + width > TankFrame.GAME_WIDTH || y + height > TankFrame.GAME_HEIGHT) {
            this.back();
        }
    }
    private void fire() {
        int bx = x + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth();
        int by = y + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight();
        TankFrame.INSTANCE.add(new Bullet(bx + 7, by - 5, this.dir, this.group));
    }


    private void back() {
        this.x = oldX;
        this.y = oldY;
    }


    private void randomDir() {
        if(r.nextInt(100) > 90) {
            this.dir = Direct.randomDir();
        }
    }

    public void die() {
        this.setLive(false);
        TankFrame.INSTANCE.add(new Explode(x,y));
    }
}
