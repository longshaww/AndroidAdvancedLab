package vn.edu.huflit.ttl_19dh110248.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM Cart")
    List<vn.edu.huflit.ttl_19dh110248.models.Cart> getAll();

    @Insert
    void insertAll(vn.edu.huflit.ttl_19dh110248.models.Cart... carts);

    @Insert
    void insertCart(vn.edu.huflit.ttl_19dh110248.models.Cart cart);

    @Delete
    void deleteCart(vn.edu.huflit.ttl_19dh110248.models.Cart cart);

    @Update
    void updateCart(vn.edu.huflit.ttl_19dh110248.models.Cart cart);

    @Delete
    void deleteMultiCart(vn.edu.huflit.ttl_19dh110248.models.Cart... cart);

    @Query("DELETE FROM Cart")
    void delete();


}

