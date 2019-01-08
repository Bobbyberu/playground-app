/**
 * Regex validator for email format
 */
class EmailValidator {

  static String _regEmail = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+\$)";

  static bool isEmail(String test) {
    RegExp regexp = new RegExp(_regEmail);
    return regexp.hasMatch(test);
  }

}