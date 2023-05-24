package network.udp.client;

public class UpdatedState
{
    private int[] playerPos;
    private int[] bulletPos;
    private int[] usedItem;

    // @TODO: add more fields


    // Getters and setters
    public int[] getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(int[] playerPos) {
        this.playerPos = playerPos;
    }

    public int[] getBulletPos() {
        return bulletPos;
    }

    public void setBulletPos(int[] bulletPos) {
        this.bulletPos = bulletPos;
    }

    public int[] getUsedItem() {
        return usedItem;
    }

    public void setUsedItem(int[] usedItem) {
        this.usedItem = usedItem;
    }
}
