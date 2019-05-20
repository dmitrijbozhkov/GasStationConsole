import Component from '@ember/component';
import { computed } from '@ember/object';
import { inject } from '@ember/service';

const CHANGE_PASSWORD_URL = "/api/user/change-password";

export default class ChangePasswordForm extends Component.extend({
  notify: inject("toast" as any) as any,
  userService: inject("user-service"),
  router: inject("router"),
  isLoading: false,
  oldPassword: "",
  newPassword: "",
  repeatPassword: "",
  repeatPasswordErrorMessage: computed("oldPassword", "newPassword", "repeatPassword", function() {
    if (this.get("newPassword") != this.get("repeatPassword")) {
      return "New password and repeat password should be the same"
    }
    return "";
  }),
  newPasswordErrorMessage: computed("newPassword", function() {
    let checkError = this.checkPassword(this.get("newPassword"));
    if (!this.get("newPassword")) {
      return ""
    }
    if (checkError) {
      return checkError;
    }
    if (this.get("newPassword") === this.get("oldPassword")) {
      return "New password should be different from old password";
    }
    return "";
  }),
  isChangingNotPossible: computed("repeatPasswordErrorMessage", "newPasswordErrorMessage", "oldPassword", function() {
    return this.get("repeatPasswordErrorMessage") || this.get("newPasswordErrorMessage") || !this.get("oldPassword");
  })
}) {
  checkPassword(password: string) {
    if (password.length < 6) {
      return "Password length must be 6 or more";
    }
    if (password.length > 26) {
      return "Password length must be less, than 26";
    }
    if (!/[0-9]/g.test(password)) {
      return "Password should have at least one number";
    }
    if (!/[a-z]/.test(password)) {
      return "Password should have at least one latin letter";
    }
    if (!/[A-Z]/g.test(password)) {
      return "Password should have at least one capital letter";
    }
    return "";
  }
  actions = {
    changePassword(this: ChangePasswordForm, isDisabled: boolean) {
      if (!isDisabled) {
        this.set("isLoading", true);
        this.get("userService")
        .changePassword(this.get("newPassword"), this.get("oldPassword"))
        .then((data) => {
          this.set("isLoading", false);
          this.get("notify").success("Password successfully changed", "You may login with your new password now");
          this.get("router").transitionTo("login");
        })
        .catch((error) => {
          this.set("isLoading", false);
          this.get("notify").error(error.responseJSON.exceptionMessage, "An error occured");
        });
      }
    }
  }
};
