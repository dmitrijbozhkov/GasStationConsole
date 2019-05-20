import Component from '@ember/component';
import { inject } from '@ember/service';

export default class LoginForm extends Component.extend({
  userService: inject("user-service"),
  router: inject("router"),
  notify: inject("toast" as any) as any,
  isLoading: false,
  username: "",
  password: ""
}) {
  actions = {
    loginUser(this: LoginForm) {
      this.set("isLoading", true);
      this.get("userService")
      .login(this.get("username"), this.get("password"))
      .then((data: any) => {
        this.set("isLoading", false);
        let transition = this.get("session.attemptedTransition" as any);
        if (transition) {
          transition.retry();
        } else {
          this.get("router").transitionTo("index");
        }
      })
      .catch((error: any) => {
        this.set("isLoading", false);
        this.notify.error(error.responseJSON.exceptionMessage, "Login error");
      });
    }
  }
};
