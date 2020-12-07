package CSCI201_LunchWithFriends;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class userDBAccess {

	private static final String SQL_SERIALIZE_OBJECT = "INSERT INTO Users(object_name, serialized_object) VALUES (?, ?)";
	private static final String SQL_DESERIALIZE_OBJECT = "SELECT serialized_object FROM Users WHERE serialized_id = ?";
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/lunchwithfriends?cloudSqlInstance=csci201-lunchwithfriends:us-west2:lunchwithfriends&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=lunchwithfriendsTest&password=lunchwithfriends";


	public long serializeJavaObjectToDB(Connection connection,
			Object objectToSerialize) throws SQLException, ClassNotFoundException {
		
		//Class.forName("com.mysql.jdbc.Driver");
		//connection = DriverManager.getConnection(CREDENTIALS_STRING);
		

		PreparedStatement pstmt = connection
				.prepareStatement(SQL_SERIALIZE_OBJECT, Statement.RETURN_GENERATED_KEYS);

		// just setting the class name
		pstmt.setString(1, objectToSerialize.getClass().getName());
		pstmt.setObject(2, objectToSerialize);
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		int serialized_id = -1;
		if (rs.next()) {
			serialized_id = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		System.out.println("Java object serialized to database. Object: "
				+ objectToSerialize);
		return serialized_id;
	}

	/**
	 * To de-serialize a java object from database
	 *
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object deSerializeJavaObjectFromDB(Connection connection,
			long serialized_id) throws SQLException, IOException,
			ClassNotFoundException {
		//Class.forName("com.mysql.jdbc.Driver");

		PreparedStatement pstmt = connection
				.prepareStatement(SQL_DESERIALIZE_OBJECT);
		pstmt.setLong(1, serialized_id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();

		Object object = rs.getObject(1);

		byte[] buf = rs.getBytes(1);
		ObjectInputStream objectIn = null;
		if (buf != null)
			objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

		User deSerializedObject = (User)objectIn.readObject();

		rs.close();
		pstmt.close();

		System.out.println("Java object de-serialized from database. Object: "
				+ deSerializedObject + " Classname: "
				+ deSerializedObject.getClass().getName() + " name: "
						+ deSerializedObject.getName());
		return deSerializedObject;
	}

}






