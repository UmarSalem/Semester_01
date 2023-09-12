package model;

import java.io.Serializable;

public class Room implements Serializable {
    private int roomNumber;
    private RoomType roomType;
    private final double pricePerNight;
    private boolean isCheckedIn;

    public Room(int roomNumber, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;

        if (roomType.equals(RoomType.FAMILY_ROOM)) pricePerNight = 400;
        else if (roomType.equals(RoomType.SINGLE_BEDROOM)) pricePerNight = 150;
        else pricePerNight = 295;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    public boolean getIsCheckedIn() {
        return this.isCheckedIn;

    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                '}';
    }

    public enum RoomType {
        SINGLE_BEDROOM, DOUBLE_BEDROOM, FAMILY_ROOM
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Room)) return false;

        Room room = (Room) object;
        return room.roomNumber == roomNumber && room.roomType.equals(roomType);
    }
}

