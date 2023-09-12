package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomList implements Serializable {

    private final List<Room> rooms;

    public RoomList() {
        rooms = new ArrayList<>();

    }

    private void addRoom(Room room) {
        this.rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * This function creates a list of rooms with 30 rooms, 10 of each type
     */
    public void createList() {


        for (int i = 0; i < 42; i++) {

            if (i % 2 == 0) {
                Room room = new Room(i, Room.RoomType.FAMILY_ROOM);
                rooms.add(room);
            } else if (i % 3 == 0) {
                Room room = new Room(i, Room.RoomType.DOUBLE_BEDROOM);
                rooms.add(room);
            } else {
                Room room = new Room(i, Room.RoomType.SINGLE_BEDROOM);
                rooms.add(room);
            }

        }

    }


}
