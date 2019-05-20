import Component from '@ember/component';
import { computed } from '@ember/object';
import { inject } from '@ember/service';

const USERNAME_PATTERN = new RegExp("[^A-Za-z0-9]+");
const PASSWORD_PATTERN = new RegExp("[^A-Za-z0-9]+");

export default class RegisterForm extends Component.extend({
  notify: inject("toast" as any) as any,
  router: inject("router"),
  userService: inject("user-service"),
  isLoading: false,
  // Form values
  name: "",
  surname: "",
  username: "",
  password: "",
  passwordRepeat: "",
  // is form input touched
  nameTouched: false,
  surnameTouched: false,
  usernameTouched: false,
  passwordTouched: false,
  passwordRepeatTouched: false,
  // is input valid
  isNameValid: computed("name", function() {
    let name = this.get("name");
    return name.length > 3 && name.length < 26;
  }),
  isSurnameValid: computed("surname", function() {
    let surname = this.get("surname");
    return surname.length > 3 && surname.length < 26
  }),
  isUsernameValid: computed("username", function() {
    let username = this.get("username");
    return username.length > 3 && username.length < 26 && !USERNAME_PATTERN.test(username);
  }),
  isPasswordValid: computed("password", function() {
    let password = this.get("password");
    return password.length > 5 && password.length < 25 && !PASSWORD_PATTERN.test(password);
  }),
  isRepeatValid: computed("passwordRepeat", "password", function() {
    return this.get("passwordRepeat") === this.get("password");
  }),
  // show input validation error
  showNameValidationError: computed("isNameValid", "nameTouched", function() {
    return !this.get("isNameValid") && this.get("nameTouched");
  }),
  showSurnameValidationError: computed("isSurnameValid", "surnameTouched", function() {
    return !this.get("isSurnameValid") && this.get("surnameTouched");
  }),
  showUsernameValidationError: computed("isUsernameValid", "usernameTouched", function() {
    return !this.get("isUsernameValid") && this.get("usernameTouched");
  }),
  showPasswordValidationError: computed("isPasswordValid", "passwordTouched", function() {
    return !this.get("isPasswordValid") && this.get("passwordTouched");
  }),
  showPasswordRepeatValidationError: computed("isRepeatValid", "passwordRepeatTouched", function() {
    return !this.get("isRepeatValid") && this.get("passwordRepeatTouched");
  })
}) {
  actions = {
    registerUser(this: RegisterForm) {
      if (
        this.get("isNameValid") &&
        this.get("isSurnameValid") && 
        this.get("isUsernameValid") && 
        this.get("isPasswordValid") && 
        this.get("isRepeatValid"))
      {
        this.set("isLoading", true);
        this.get("userService")
        .signin(this.get("name"), this.get("surname"), this.get("username"), this.get("password"))
        .then((data) => {
          this.set("isLoading", false);
          this.get("notify").success("You can now login into your account", "Registration successfull");
          this.get("router").transitionTo("login");
        })
        .catch((error) => {
          this.set("isLoading", false);
          this.notify.error(error.responseJSON.exceptionMessage, "Registration error");
        });
      }
    },
    nameInputTouched(this: RegisterForm) {
      this.set("nameTouched", true);
    },
    surnameInputTouched(this: RegisterForm) {
      this.set("surnameTouched", true);
    },
    usernameInputTouched(this: RegisterForm) {
      this.set("usernameTouched", true);
    },
    passwordInputTouched(this: RegisterForm) {
      this.set("passwordTouched", true);
    },
    repeatPasswordInputTouched(this: RegisterForm) {
      this.set("passwordRepeatTouched", true);
    },
  }
};
