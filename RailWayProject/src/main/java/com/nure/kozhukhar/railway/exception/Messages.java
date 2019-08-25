package com.nure.kozhukhar.railway.exception;

/**
 * Messages
 * <p>
 *     This class provides messages from server to clients.
 *     Messages could be contain into 'messages.properties' and
 *     could be translated into different languages.
 * </p>
 *
 * @author Anatol Kozhukhar
 */
public class Messages {

	/**
	 * Error message Login Validation
	 */
	public static final String ERR_REGISTER_DATA_LOGIN = "error.min_size_login";
	public static final String ERR_REGISTER_DATA_PASSWORD = "error.min_size_pass";
	public static final String ERR_REGISTER_DATA_EMAIL = "error.email_valid";
	public static final String ERR_ADD_NEW_USER = "error.add_new_user";

	/**
	 * Error message User by DBException
	 */
	public static final String ERR_GET_USER_BY_LOGIN = "error.user_not_found";
	public static final String ERR_GET_USER_ROLE_BY_LOGIN = "error.user_role_not_found";
	public static final String ERR_GET_DB_USER_FULL_NAME = "error.full_user_name_not_found";
	public static final String ERR_SAVE_USER_ROLE = "error.role_exist";
	public static final String ERR_UPDATE_USER_DATA = "error.user_has_not_been_updated";
	public static final String ERR_DELETE_USER = "error.user_has_not_been_deleted";

	/**
	 * Error message City/Country by DBException
	 */
	public static final String ERR_DELETE_CITY = "error.delete_city_error";
	public static final String ERR_CITY_SAVE = "error.cannot_save_new_city";
	public static final String ERR_GET_CITY_LIST = "error.cannot_get_all_city";
	public static final String ERR_GET_CITY_BY_ID = "error.city_was_not_found";
	public static final String ERR_COUNTRY_DELETE = "error.country_delete";
	public static final String ERR_COUNTRY_SAVE = "error.country_save";
	public static final String ERR_COUNTRIES_WAS_NOT_FOUND = "error.countries_was_not_found";
	public static final String ERR_COUNTRY_WAS_NOT_FOUND = "error.country_was_not_found";

	/**
	 * Error message Train/Carriage/Type by DBException
	 */
	public static final String ERR_DELETE_TYPE_OF_CARRIAGE = "error.delete_carr_type";
	public static final String ERR_UPDATE_CARR_TYPE = "error.update_carr_type";
	public static final String ERR_SAVE_CARR_TYPE = "error.save_carr_type";
	public static final String ERR_GET_TYPES = "error.get_all_carr_type";
	public static final String ERR_GET_ID_TYPE = "error.type_was_not_found";
	public static final String ERR_DELETE_TRAIN = "error.delete_train";
	public static final String ERR_SAVE_NEW_TRAIN = "error.save_new_train";
	public static final String ERR_GET_ALL_TRAINS = "error.cannot_get_all_trains";
	public static final String ERR_GET_TRAIN = "error.train_was_not_found";
	public static final String ERR_DELETE_ALL_TRAINS_CONTENT = "error.delete_train";
	public static final String ERR_CANNOT_GET_INFO_TRAIN = "error.cannot_get_trains_info";
	public static final String ERR_CANNOT_SAVE_NEW_CARRIAGE_AND_SEAT = "error.cannot_save_new_carriage_or_seats";
	public static final String ERR_GET_MAX_SIZE = "error.cannot_get_max_carr_size";

	/**
	 * Error message Route/Station by DBException
	 */
	public static final String ERR_GET_STATION = "error.cannot_get_station";
	public static final String ERR_CANNOT_SAVE_STATION_IN_ROUTE = "error.cannot_save_station_in_route";
	public static final String ERR_CANNOT_GET_ROUTE = "error.cannot_get_route";
	public static final String ERR_GET_ROUTE_ON_DATE = "error.route_was_not_found";
	public static final String ERR_CANNOT_GET_STATION = "error.station_was_not_found";
	public static final String ERR_ROUTE_WAS_NOT_SAVED = "error.route_was_not_saved";
	public static final String ERR_CANNOT_DELETE_ROUTE = "error.cannot_delete_route";
	public static final String ERR_COUNT_ALL_FREE_SEAT = "error.cannot_count_all_free_seats";
	public static final String ERR_CANNOT_GET_ALL_SEAT = "error.cannot_get_all_seats";
	public static final String ERR_CANNOT_GET_STATIONS_IN_ROUTE = "error.cannot_get_stations_in_route";
	public static final String ERR_CANNOT_GET_STATIONS = "error.cannot_get_stations";
	public static final String ERR_CANNOT_SAVE_STATION = "error.cannot_save_the_new_station";
	public static final String ERR_CANNOT_DELETE_STATION = "error.cannot_delete_station";
	public static final String ERR_CANNOT_DELETE_USER_CHECK = "error.cannot_delete_check";

	/**
	 * Error message User Check by DBException
	 */
	public static final String ERR_CANNOT_GET_CARRIAGE_INFO = "error.cannot_get_carriage_info";
	public static final String ERR_CANNOT_GET_CHECK_BY_USER = "error.cannot_get_check_by_user";
	public static final String ERR_CANNOT_SAVE_USER_CHECK = "error.cannot_save_user_check";

	/**
	 * Logical Error message by APPException
	 */
	public static final String ERR_CANNOT_FIND_ANY_ROUTE = "error.cannot_find_any_route";

	public static final String ERR_TYPE_FIELD = "Error type filed. Check input data!";
	public static final String ERR_CANNOT_SAVE_NEW_ROUTE = "Error! Cannot save new route!";
	public static final String ERR_CANNOT_OPEN_ACCOUNT_PAGE = "Error! Cannot open account page";
	public static final String ERR_CANNOT_SHOW_DOCUMENT = "Cannot show document!";
	public static final String ERR_CANNOT_UPDATE_USER = "Cannot update user!";
	public static final String ERR_CANNOT_CHANGE_SEAT_DATA = "Seat data cannot be changed!";
	public static final String ERR_CANNOT_SAVE_CITY = "City was not saved!";
	public static final String ERR_CANNOT_SAVE_TRAIN = "Train was not saved!";
	public static final String ERR_CANNOT_SAVE_TYPE = "Type was not saved!";
	public static final String ERR_CANNOT_SAVE_USER_DATA = "User Data was not saved!";
	public static final String ERR_CANNOT_FIND_SEAT = "Cannot find any seats and carriages!";
	public static final String ERR_UNKNOWN_ERROR = "Unknown error!";
	public static final String ERR_USER_CREATE_UNKNOWN = "Unknown user create error!";
	public static final String ERR_PIN_CODE_WRONG = "Pin code is wrong!";
	public static final String ERR_CANNOT_SHOW_USER_INFO = "Cannot show user info";
	public static final String ERR_FIND_BY_EMAIL = "Cannot find user ny email";
	public static final String ERR_EMAIL_ALREADY_EXISTS = "This email is exist in system.";


	private Messages() {

	}

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";

	public static final String ERR_USER_LOGIN_OR_PASSWORD = "error.incorrect_login_or_password";

}