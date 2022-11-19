package org.example.component;


import org.example.model.Avatar;
import org.example.record.AvatarRecord;

public class RecordMapper {

    public AvatarRecord toRecord(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatar.getId());
        avatarRecord.setMediaTipe(avatar.getMediaTipe());
        avatarRecord.setUrl("http://localhost:8080/avatars/"+ avatar.getId() +"/from-db");
        return avatarRecord;
    }
}
