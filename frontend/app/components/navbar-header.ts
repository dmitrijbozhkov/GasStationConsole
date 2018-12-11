import Component from '@ember/component';
import { inject } from "@ember/service";
import { computed } from '@ember/object';

export default class NavbarHeader extends Component.extend({
  tagName: "nav",
  classNames: ["navbar", "is-white"],
  isBurgerOpen: false,
  globalUnToggleEvent: null,
  session: inject("session" as any),
  router: inject("router"),
  isLoggedIn: computed("session.isAuthenticated", function() {
    return this.get("session.isAuthenticated");
  }),
  didInsertElement() {
    this._super(...arguments);
    this.set("globalUnToggleEvent", this.$(document).click((e) => {
      if (!$(e.target).hasClass("navbar-burger")) {
        this.set("isBurgerOpen", false);
      }
    }));
  },
  didDestroyElement() {
    this._super(...arguments);
    (this.get("globalUnToggleEvent") as any).unbind();
  }
}) {
  actions = {
    toggleBurger(this: NavbarHeader) {
      this.toggleProperty("isBurgerOpen");
    },
    logOut(this: NavbarHeader) {
      this.get("session").invalidate();
      this.get("router").transitionTo("index");
    }
}
};
