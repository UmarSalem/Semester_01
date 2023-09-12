import model.ReservationList;
import model.RoomList;
import utils.ModelManager;



// Only use to create files if they are corrupted
public class RoomAndReservationCreater {
    public static void main(String[] args) {
                ModelManager modelManager = new ModelManager();
        RoomList roomList = new RoomList();
        roomList.createList();
        modelManager.saveRoomListToFile(roomList);

        modelManager.saveReservationListToFile(new ReservationList());
    }
}
