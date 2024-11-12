import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseCode {
    public boolean save(Customer c) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        PreparedStatement statement =
                DbConnection.getInstance().
                        getConnection().
                        prepareStatement(sql);
        statement.setInt(1, c.getId());
        statement.setString(2, c.getName());
        statement.setString(3, c.getAddress());
        statement.setDouble(4, c.getSalary());
        return statement.executeUpdate() > 0;
    }

    public Customer find(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE id=?";
        PreparedStatement stm = DbConnection.getInstance().
                getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet set = stm.executeQuery();
        if (set.next()) {
            return new Customer(set.getInt(1),
                    set.getString(2),
                    set.getString(3),
                    set.getDouble(4)
            );
        } else {return null;}
    }

    public boolean update(Customer c) {
    }

    public boolean delete(int id) {
    }

    public List<Customer> findAll() {
    }
}