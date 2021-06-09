package com.example.roomdatabinding.roomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface MyDao {
    @Insert
    public void AddData(MyUser myUser);

    @Update
    public void UpdataData(MyUser myUser);

    @Delete
    public void DeleteUser(MyUser myUser);

    @Query("UPDATE user SET username =:username,useremail=:useremail,usercity=:usercity WHERE id=:id")
    public void UpdateUser(int id,String username,String useremail,String usercity);

    @Query("DELETE FROM user")
    public void DeleteAll();

    @Query("SELECT * FROM user ORDER BY id DESC")
    public List<MyUser>getMyData();
}
