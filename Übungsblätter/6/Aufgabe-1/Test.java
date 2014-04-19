import java.util.concurrent.CopyOnWriteArraySet;

class Test {
 public static Integer getClosestTileSize(int height, int width, int sizeWish) {
// first calculcate prime factors of the given tile size
CopyOnWriteArraySet<Integer> validSizes =
new CopyOnWriteArraySet<Integer>();
for (int i = 2; i <= height / i; i++) {
System.out.println("i = " + i + ", height = " + height + ", width = " + width);
while ((height % i == 0) && (width % i == 0)) {
// found common denominator
for (int k : validSizes) {
validSizes.add(k * i);
System.out.println("validSizes.add(" + k + " * " + i + " = " + (k*i) + ")");
}
validSizes.add(i);
System.out.println("validSizes.add(" + i + ")");
width /= i;
height /= i;
}
}
if (validSizes.size() == 0) {
return null;
}
int lowestError = Integer.MAX_VALUE;
int bestTileSize = Integer.MAX_VALUE;
for (int vts : validSizes) {
int error = Math.abs(vts - sizeWish);
System.out.println("size: " + vts + ", error: " + error);
if (error < lowestError) {
// new best candidate
lowestError = error;
bestTileSize = vts;
}
}
return bestTileSize;
}

public static void main(String[] argv) {
System.out.println(getClosestTileSize(Integer.valueOf(argv[0]), Integer.valueOf(argv[1]), Integer.valueOf(argv[2])));
}
}
