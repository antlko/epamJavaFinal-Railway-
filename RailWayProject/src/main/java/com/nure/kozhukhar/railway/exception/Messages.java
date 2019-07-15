package com.nure.kozhukhar.railway.exception;

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
	public static final String ERR_COUNTRIES_WAS_NOT_FOUND = "error.countries_wa_not_found";
	public static final String ERR_COUNTRY_WAS_NOT_FOUND = "error.country_was_not_found";


	private Messages() {

	}

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";

	public static final String ERR_USER_LOGIN_OR_PASSWORD = "error.incorrect_login_or_password";

}