// http://biercoff.com/nice-and-simple-converter-of-java-resultset-into-jsonarray-or-xml/
package models;

import play.libs.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 * Utility for converting ResultSets into some Output formats
 * @author marlonlom
 */
public class Convertor {
    /**
     * Convert a result set into a JSON Array
     *
     * @param resultSet
     * @return a JSONArray
     * @throws Exception
     */
    public static JsonNode convertToJson(ResultSet resultSet) throws Exception {
        ArrayList<ObjectNode> arr = new ArrayList<ObjectNode>();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            ObjectNode obj = Json.newObject();
            for (int i = 0; i < total_rows; i++) {
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), Json.toJson(resultSet.getObject(i + 1)));
            }
            arr.add(obj);
        }
        return Json.toJson(arr);
    }
}