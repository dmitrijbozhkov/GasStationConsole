import Component from '@ember/component';
import { inject as service } from '@ember/service';

export default class LoginForm extends Component.extend({
  username: "",
  password: ""
}) {
  session = (service("session" as any) as any)
  router = service("router")
  actions = {
    loginUser(this: LoginForm) {
      let credentials = { username: this.get("username"), password: this.get("password") };
      this
        .get("session")
        .authenticate('authenticator:token', credentials)
        .then((success: any) => {
          let transition = this.get("session.attemptedTransition" as any);
          if (transition) {
            transition.retry();
          } else {
            this.get("router").transitionTo("index");
          }
        })
        .catch((err: any) => {
          console.log(err);
        });
    }
  }
};
