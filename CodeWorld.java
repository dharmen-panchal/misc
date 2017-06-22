
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Dharmen
 *
 */
public class CodeWorld {

  public static void main(String[] args) {

    // Let's say Role-A has permission value 144 for model BookmarksFolder.
    List<String> actionsForRole = findPermittedActions(Constants.MDL_BOOKMARKS_FOLDER, 144);

    System.out.println(actionsForRole);

  }

  /**
   * Finds the list of actions of model, based on composite permission value.
   * 
   * @param modelName name of the model
   * @param permissions composite binary value that represents the sum of allowed actions' binary
   *        values.
   * @return list of String
   */
  private static List<String> findPermittedActions(String modelName, int permissions) {

    /*
     * 
     * - The resourceActions' binary values are stored in 2^n format.
     *   Thus the binary representation will have only one 1 and rest all zeros.
     * - Here, we will do binary AND operation between composite binary value and 
     *   resourceAction's binary value. If it equals to resourceAction's binary value,
     *   then that resourceAction is permitted.
     * 
     */
    List<String> permittedActions =
        ResrouceActionTable.getByModelName(modelName)
        .filter(resourceAction -> {
          return (permissions & resourceAction.getBinValue()) == resourceAction.getBinValue();
        })
        .map(ResourceAction::getAction)
        .collect(Collectors.toList());

    return permittedActions;
  }
}


/**
 * Model for ResourceAction entity.
 * 
 */
class ResourceAction {

  private long resourceActionId;
  private String modelName;
  private String action;
  private int binValue;

  public ResourceAction(long resourceActionId, String modelName, String action, int binValue) {
    super();
    this.resourceActionId = resourceActionId;
    this.modelName = modelName;
    this.action = action;
    this.binValue = binValue;
  }

  public long getResourceActionId() {
    return resourceActionId;
  }

  public void setResourceActionId(long resourceActionId) {
    this.resourceActionId = resourceActionId;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public int getBinValue() {
    return binValue;
  }

  public void setBinValue(int binValue) {
    this.binValue = binValue;
  }

}


/**
 * Holds the master records for ResourceAction entity. This will mimic the DB table in LR.
 * 
 * Thing to note here is that the allowed binary values are 2^n.
 * 
 */
class ResrouceActionTable {

  private static final Stream<ResourceAction> table = Stream.of(

      new ResourceAction(404, Constants.MDL_BOOKMARKS, Constants.PRM_VIEW, 1),
      new ResourceAction(401, Constants.MDL_BOOKMARKS, Constants.PRM_ADD_ENTRY, 2),
      new ResourceAction(402, Constants.MDL_BOOKMARKS, Constants.PRM_PERMISSIONS, 4),
      new ResourceAction(403, Constants.MDL_BOOKMARKS, Constants.PRM_SUBSCRIBE, 8),
      new ResourceAction(405, Constants.MDL_BOOKMARKS, Constants.PRM_ADD_FOLDER, 16),

      new ResourceAction(410, Constants.MDL_BOOKMARKS_ENTRY, Constants.PRM_VIEW, 1),
      new ResourceAction(406, Constants.MDL_BOOKMARKS_ENTRY, Constants.PRM_DELETE, 2),
      new ResourceAction(407, Constants.MDL_BOOKMARKS_ENTRY, Constants.PRM_PERMISSIONS, 4),
      new ResourceAction(408, Constants.MDL_BOOKMARKS_ENTRY, Constants.PRM_UPDATE, 8),
      new ResourceAction(409, Constants.MDL_BOOKMARKS_ENTRY, Constants.PRM_SUBSCRIBE, 16),

      new ResourceAction(417, Constants.MDL_BOOKMARKS_FOLDER, Constants.PRM_VIEW, 1),
      new ResourceAction(411, Constants.MDL_BOOKMARKS_FOLDER, Constants.PRM_ADD_ENTRY, 2),
      new ResourceAction(412, Constants.MDL_BOOKMARKS_FOLDER, Constants.PRM_DELETE, 4),
      new ResourceAction(413, Constants.MDL_BOOKMARKS_FOLDER, Constants.PRM_PERMISSIONS, 8),
      new ResourceAction(414, Constants.MDL_BOOKMARKS_FOLDER, Constants.PRM_ADD_SUBFOLDER, 16),
      new ResourceAction(415, Constants.MDL_BOOKMARKS_FOLDER, Constants.PRM_UPDATE, 32),
      new ResourceAction(416, Constants.MDL_BOOKMARKS_FOLDER, Constants.PRM_SUBSCRIBE, 64),
      new ResourceAction(418, Constants.MDL_BOOKMARKS_FOLDER, Constants.PRM_ACCESS, 128));

  public static Stream<ResourceAction> getByModelName(String modelName) {

    return table.filter(resourceAction -> {
      return resourceAction.getModelName().equals(modelName);
    });
  }

}


/**
 * Contant Strings for Model names and Action names.
 * 
 */
class Constants {

  public static final String MDL_BOOKMARKS = "com.liferay.bookmarks";
  public static final String MDL_BOOKMARKS_ENTRY = "com.liferay.bookmarks.model.BookmarksEntry";
  public static final String MDL_BOOKMARKS_FOLDER = "com.liferay.bookmarks.model.BookmarksFolder";

  public static final String PRM_ADD_ENTRY = "ADD_ENTRY";
  public static final String PRM_PERMISSIONS = "PERMISSIONS";
  public static final String PRM_SUBSCRIBE = "SUBSCRIBE";
  public static final String PRM_VIEW = "VIEW";
  public static final String PRM_ADD_FOLDER = "ADD_FOLDER";
  public static final String PRM_DELETE = "DELETE";
  public static final String PRM_UPDATE = "UPDATE";
  public static final String PRM_ADD_SUBFOLDER = "ADD_SUBFOLDER";
  public static final String PRM_ACCESS = "ACCESS";

}
