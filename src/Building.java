import java.io.File;

public class Building {

  int[] mathCoordinate;
  int[] guiCorrdinate;
  int schoolNum;
  String nameOfBuilding;
  String detailOfBuilding;
  File imgOfBuilding;

  Building(String nameOfBuilding, String detailOfBuilding, File imgOfBuilding, int schoolNum,
      int[] guiCorrdinate, int[] mathCoordinate) {
    this.detailOfBuilding = detailOfBuilding;
    this.guiCorrdinate = guiCorrdinate.clone();
    this.imgOfBuilding = imgOfBuilding;
    this.mathCoordinate = mathCoordinate.clone();
    this.schoolNum = schoolNum;
    this.nameOfBuilding = nameOfBuilding;
  }

  public String getDetailOfBuilding() {
    return detailOfBuilding;
  }
}