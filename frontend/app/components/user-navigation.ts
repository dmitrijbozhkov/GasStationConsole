import Component from '@ember/component';
import { inject as service } from '@ember/service';

enum SETTINGS_MENUS {
  MyOperations = "user.my-operations",
  ChangePassword = "user.change-password",
  ManageUserAuthorities = "user.manage-authorities",
  SearchUserOperations = "user.search-user-operations",
  ManageFuel = "user.manage-fuel"
} 

export default class UserNavigation extends Component.extend({
  isMyOperationsActive: false,
  isChangePasswordActive: false,
  isManageUserAuthoritiesActive: false,
  isSearchUserOperationsActive: false,
  isManageFuelTanksActive: false
}) {
  router = service("router")
  resetActiveAndTransition(target: string) {
    this.set("isMyOperationsActive", false);
    this.set("isChangePasswordActive", false);
    this.set("isManageUserAuthoritiesActive", false);
    this.set("isSearchUserOperationsActive", false);
    this.set("isManageFuelTanksActive", false);
    this.get("router").transitionTo(target);
  }
  didReceiveAttrs(this: UserNavigation) {
    this.set("isMyOperationsActive", true);
  }
  actions = {
    transitionToMenu(this: UserNavigation, target: string) {
      this.resetActiveAndTransition(target);
      switch(target) {
        case SETTINGS_MENUS.MyOperations:
        this.set("isMyOperationsActive", true);
        break;
        case SETTINGS_MENUS.ChangePassword:
        this.set("isChangePasswordActive", true);
        break;
        case SETTINGS_MENUS.ManageUserAuthorities:
        this.set("isManageUserAuthoritiesActive", true);
        break;
        case SETTINGS_MENUS.SearchUserOperations:
        this.set("isSearchUserOperationsActive", true);
        break;
        case SETTINGS_MENUS.ManageFuel:
        this.set("isManageFuelTanksActive", true);
        break;
      }
    }
  }
};
